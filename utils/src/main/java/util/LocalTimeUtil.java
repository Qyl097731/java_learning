package util;

/**
 * @author qyl
 * @program LocalTimeUtil.java
 * @Description 时间工具类
 * @createTime 2022-08-25 17:25
 */
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

public class LocalTimeUtil {
    public static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    public static DateTimeFormatter FORMAT_YMD = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static DateTimeFormatter FORMAT_YMD_F = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static DateTimeFormatter FORMAT_YMDH = DateTimeFormatter.ofPattern("yyyyMMddHH");
    public static DateTimeFormatter FORMAT_YM = DateTimeFormatter.ofPattern("yyyyMM");
    public static DateTimeFormatter FORMAT_YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter FORMAT_YMDHMS_F = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public LocalTimeUtil() {
    }

    public static LocalDateTime parse(String dateTimeStr) {
        DateTimeFormatter formatter = (new DateTimeFormatterBuilder()).appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    public static LocalDateTime parseYMDHMSF(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, FORMAT_YMDHMS_F);
    }

    public static LocalDateTime parseYMD(String dateTimeStr) {
        return LocalDate.parse(dateTimeStr, FORMAT_YMD_F).atStartOfDay();
    }

    public static LocalDateTime parseYMDH(String dateTimeStr) {
        return LocalDate.parse(dateTimeStr, FORMAT_YMDH).atTime(Integer.parseInt(dateTimeStr.substring(8, 10)), 0);
    }

    public static LocalDateTime parseYMDHMS(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, FORMAT_YMDHMS);
    }

    public static LocalDate parseYMDToDate(String dateTimeStr) {
        return LocalDate.parse(dateTimeStr, FORMAT_YMD_F);
    }

    public static String format(LocalDateTime localDateTime) {
        return FORMAT.format(localDateTime);
    }

    public static String formatYMDHMS(LocalDateTime localDateTime) {
        return FORMAT_YMDHMS.format(localDateTime);
    }

    public static String formatYMD(LocalDateTime localDateTime) {
        return FORMAT_YMD.format(localDateTime);
    }

    public static String formatYMDF(LocalDateTime localDateTime) {
        return FORMAT_YMD_F.format(localDateTime);
    }

    public static String formatYMDFFromDate(LocalDate localDate) {
        return FORMAT_YMD_F.format(localDate);
    }

    public static String formatYMDH(LocalDateTime localDateTime) {
        return FORMAT_YMDH.format(localDateTime);
    }

    public static String formatYM(LocalDateTime localDateTime) {
        return FORMAT_YM.format(localDateTime);
    }

    public static String formatYMFromDate(LocalDate localDate) {
        return FORMAT_YM.format(localDate);
    }

    public static String getDay(LocalDateTime localDateTime) {
        return leftAppend(localDateTime.getDayOfMonth());
    }

    public static String leftAppend(int num) {
        String result;
        if (num < 10) {
            result = "0".concat(String.valueOf(num));
        } else {
            result = String.valueOf(num);
        }

        return result;
    }

    public static boolean checkDownTime(LocalDateTime localDateTime) {
        LocalDateTime cur = LocalDateTime.now();
        LocalDateTime before = cur.minusDays(2L).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return cur.isAfter(localDateTime) && localDateTime.isAfter(before);
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
