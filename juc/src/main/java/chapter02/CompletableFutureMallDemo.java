package chapter02;

import com.sun.org.apache.xpath.internal.objects.XString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sun.nio.ch.Net;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * projectName:  juc
 * packageName: chapter02
 * date: 2022-07-25 23:27
 * author 邱依良
 */
public class CompletableFutureMallDemo {
    static List<NetMall> list = Arrays.asList(
            new NetMall("gd"),
            new NetMall("dangdang"),
            new NetMall("taobao")
    );

    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list
                .stream()
                .map(netMall ->
                        String.format(productName + "in %s price is %.2f",
                                netMall.getNetMallName(),
                                netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream().map(netMall -> CompletableFuture.supplyAsync(()->
            String.format(productName + "in %s price is %.2f",
                    netMall.getNetMallName(),
                    netMall.calcPrice(productName))))
                .collect(Collectors.toList()).stream()
                .map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> list1 = getPrice(list,"mysql");
        for (String element: list1){
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("---costTime" + (endTime - startTime) + " ms");

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(list,"mysql");
        for (String element: list2){
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("---costTime" + (endTime2 - startTime2) + " ms");
    }

}

class NetMall {
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public BigDecimal calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0));
    }
}
