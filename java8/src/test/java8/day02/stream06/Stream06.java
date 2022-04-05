package java8.day02.stream06;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.List;

/**
 * projectName:  java_learing
 * packageName: java8.day02.stream06
 * date: 2022-04-03 22:46
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Stream06 {
    List<Transaction> transactions = null;

    @BeforeAll

    public void beforeAll() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    //1. 找出2011年发生得所有交易，并按交易额排序（从低到高）
    @Test
    public void test01() {
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted((t1, t2) -> t1.getCost().compareTo(t2.getCost()))
                .forEach(System.out::println);
    }

    //2. 交易员都在哪些不同公司工作过
    @Test
    public void test02() {
        transactions.stream()
                .map(transaction -> transaction.getTrader().getCompany())
                .distinct()
                .forEach(System.out::println);
    }

    //3. 查找所有来自Cambridge的交易员，并按姓名排序
    @Test
    public void test03() {
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCompany().equals("Cambridge"))
                .sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
                .distinct()
                .forEach(System.out::println);
    }

    //4. 返回所有交易员的姓名字符串，并按照字母顺序排序
    @Test
    public void test04() {
        transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .sorted()
                .forEach(System.out::println);

        System.out.println("--------------------");

        String str = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .reduce("", String::concat);
        System.out.println(str);
    }

    //5. 有没有交易员在米兰工作
    @Test
    public void test05() {
        System.out.println(transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCompany().equals("Milan")));
    }

    //6. 打印生活在剑桥的交易员的所有交易额
    @Test
    public void test06() {
        System.out.println(transactions.stream()
                .filter(transaction -> transaction.getTrader().getCompany().equals("Cambridge"))
                .map(Transaction::getCost)
                .reduce(0, Integer::sum));
    }

    //7. 所有交易中最高的交易额
    @Test
    public void test07() {
        System.out.println(transactions.stream()
                .map(Transaction::getCost).max(Integer::compareTo)
                .get());
    }

    //8. 所有交易额最小的交易
    @Test
    public void test08() {
        System.out.println(transactions.stream()
                .max((t1, t2) -> Integer.compare(t1.getCost(), t2.getCost()))
                .get());
    }
}
