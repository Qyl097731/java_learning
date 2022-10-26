package java8.chapter05.demo01;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @description 付诸实践 自测
 * @date:2022/10/26 22:21
 * @author: qyl
 */
public class Practice {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    //(1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
    @Test
    public void test1() {
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue)).forEach(System.out::println);
    }

    //(2) 交易员都在哪些不同的城市工作过？
    @Test
    public void test2() {
        transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
    }

    //(3) 查找所有来自于剑桥的交易员，并按姓名排序。
    @Test
    public void test3() {
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(System.out::println);
    }

    //(4) 返回所有交易员的姓名字符串，按字母顺序排序。
    @Test
    public void test4() {
        transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    //(5) 有没有交易员是在米兰工作的？
    @Test
    public void test5() {
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> "Milan".equals(trader.getCity()))
                .findAny()
                .ifPresent(System.out::println);
    }

    //(6) 打印生活在剑桥的交易员的所有交易额。
    @Test
    public void test6() {
        System.out.println(transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .reduce(0, Integer::sum)
                .intValue());

    }

    //(7) 所有交易中，最高的交易额是多少？
    @Test
    public void test7() {
        transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .ifPresent(System.out::println);
    }

}
