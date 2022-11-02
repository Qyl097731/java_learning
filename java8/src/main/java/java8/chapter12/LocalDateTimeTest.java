package java8.chapter12;

import java8.chapter05.demo01.Transaction;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * @date:2022/11/1 15:29
 * @author: qyl
 */
public class LocalDateTimeTest {
    LocalDate date = LocalDate.of(2014, 3, 18);
    LocalTime time = LocalTime.of(13, 45, 20);

    /**
     * 创建一个LocalDate对象并读取其值
     */
    @Test
    public void test1() {
        int year = date.getYear();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dow = date.getDayOfWeek();
        int len = date.lengthOfMonth();
        boolean leap = date.isLeapYear();
        System.out.println(date);
        System.out.println("今年是 " + year);
        System.out.println("这月是 " + month);
        System.out.println("今天是该月的第 " + day + "天");
        System.out.println("今天是 " + dow);
        System.out.println("这个月有" + len + "天");
        System.out.println("是不是闰年 " + leap);
    }

    /**
     * 使用TemporalField读取LocalDate的值
     */
    @Test
    public void test2() {
        int year = date.get(ChronoField.YEAR);
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        int day = date.get(ChronoField.DAY_OF_MONTH);
    }

    /**
     * 创建LocalTime并读取其值
     */
    @Test
    public void test3() {
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
    }

    /**
     * parse创建日期和时间
     */
    @Test
    public void test4(){
        LocalDate date = LocalDate.parse("2014-03-18");
        LocalTime time = LocalTime.parse("13:45:20");
    }

    /**
     * 直接创建LocalDateTime对象，或者通过合并日期和时间的方式创建
     */
    @Test
    public void test5(){
        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);

        // 转化
        LocalDate date1 = dt1.toLocalDate();
        LocalTime time1 = dt1.toLocalTime();
    }

    /**
     * Instant时间差
     */
    @Test
    public void test6(){
        Instant.ofEpochSecond(3);
        Instant.ofEpochSecond(3, 0);
        Instant.ofEpochSecond(2, 1_000_000_000);
        Instant.ofEpochSecond(4, -1_000_000_000);
    }
}
