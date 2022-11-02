package java8.appendix.demo01;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @description
 * @date:2022/11/2 23:09
 * @author: qyl
 */
public class StreamForker<T> {

    private final Stream<T> stream;
    private final Map<Object, Function<Stream<T>, ?>> forks = new HashMap<>();

    public StreamForker(Stream<T> stream) {
        this.stream = stream;
    }

    /**
     * 这里的fork方法接受两个参数。
     * Function参数，它对流进行处理，将流转变为代表这些操作结果的任何类型。
     * key参数，通过它你可以取得操作的结果，并将这些键/函数对累积到一个内部的Map中。
     *
     * @param key
     * @param f
     * @return
     */
    public StreamForker<T> fork(Object key, Function<Stream<T>, ?> f) {
        forks.put(key, f);
        return this; //返回this从而保证多次流畅地调用fork方法
    }

    public Results getResults() {

        ForkingStreamConsumer<T> consumer = build();

        try {
            stream.sequential().forEach(consumer);
        } finally {
            consumer.finish();
        }

        return consumer;
    }


    private ForkingStreamConsumer<T> build() {
        //创建由队列组成的列表，每一个队列对应一个操作
        List<BlockingQueue<T>> queues = new ArrayList<>();

        //建立用于标识操作的键与包含操作结果的Future之间的映射关系
        HashMap<Object, Future<?>> actions = forks.entrySet().stream().reduce(
                new HashMap<>(),
                (map, e) -> {
                    map.put(e.getKey(), getOperationResult(queues, e.getValue()));
                    return map;
                },
                (m1, m2) -> {
                    m1.putAll(m2);
                    return m1;
                }
        );
        return new ForkingStreamConsumer<>(queues, actions);
    }

    private Future<?> getOperationResult(List<BlockingQueue<T>> queues, Function<Stream<T>, ?> f) {
        BlockingQueue<T> queue = new LinkedBlockingDeque<>();
        queues.add(queue);
        //创建一个队列并将其添加到队列的列表中
        Spliterator<T> spliterator = new BlockingQueueSpliterator<>(queue);
        //创建一个流，将Spliterator作为数据源
        Stream<T> source = StreamSupport.stream(spliterator, false);
        //创建一个Future对象，以异步方式计算在流上执行特定函数的结果
        return CompletableFuture.supplyAsync(() -> f.apply(source));
    }


    static class ForkingStreamConsumer<T> implements Consumer<T>, Results {

        static final Object END_OF_STREAM = new Object();

        private final List<BlockingQueue<T>> queues;
        private final Map<Object, Future<?>> actions;

        ForkingStreamConsumer(List<BlockingQueue<T>> queues, Map<Object, Future<?>> actions) {
            this.queues = queues;
            this.actions = actions;
        }

        @Override
        public void accept(T t) {
            queues.forEach(q -> q.add(t));//将流中遍历的元素添加到所有的队列中
        }

        /**
         * 将最后一个元素添加到队列中，
         * 表明该流已经结束
         */
        void finish() {
            accept((T) END_OF_STREAM);
        }

        /**
         * 等待future完成相关的计算，返回由特定键标识的处理结果
         *
         * @param key
         * @param <R>
         * @return
         */
        @Override
        public <R> R get(Object key) {
            try {
                return ((Future<R>) actions.get(key)).get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 一个遍历BlockingQueue并读取其中元素的Spliterator
     * @param <T>
     */
    class BlockingQueueSpliterator<T> implements Spliterator<T> {

        private final BlockingQueue<T> q;

        BlockingQueueSpliterator(BlockingQueue<T> q) {
            this.q = q;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            T t;
            while (true) {
                try {
                    t = q.take();
                    break;
                } catch (InterruptedException e) {
                }
            }
            if (t != ForkingStreamConsumer.END_OF_STREAM) {
                action.accept(t);
                return true;
            }

            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return 0;
        }
    }

    interface Results {

        <R> R get(Object key);
    }


    public static void main(String[] args) {
        //测试
        List<Integer> menu = Arrays.asList(1, 2, 3, 4, 5, 6, 20, 40, 60);

        Results results = new StreamForker<Integer>(menu.stream())
                .fork("max", s -> s.max(Integer::compareTo))
                .fork("sum", s -> s.collect(Collectors.summarizingInt(Integer::intValue)))
                .getResults();
        System.out.println("max:"+results.get("max"));
        System.out.println("sum:"+results.get("sum"));
    }
}

