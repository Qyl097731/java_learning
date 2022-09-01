package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author hujr
 * @date 2022/6/6 15:10
 * @description
 */
public class TimeUtil {

    private static Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    /**
     * 获取指定日期所在周的第一天和最后一天
     * @param dataStr
     * @return
     */
    public static Map<String,Object> getFirstAndLastOfWeek(String dataStr)  {
        try{
            Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dataStr));
            int d = 0;
            if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                d = -6;
            } else {
                d = 2 - cal.get(Calendar.DAY_OF_WEEK);
            }
            cal.add(Calendar.DAY_OF_WEEK, d);

            Map<String,Object> result = new HashMap<>();
            // 所在周开始日期
            String data1 = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            result.put("weekBegin",data1);
            cal.add(Calendar.DAY_OF_WEEK, 6);
            // 所在周结束日期
            String data2 = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            result.put("weekEnd",data2);
            return result;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }


    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }


    /**
     * 指定年月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {

        // 获取Calendar类的实例
        Calendar c = Calendar.getInstance();
        // 设置年份
        c.set(Calendar.YEAR, year);
        // 设置月份，因为月份从0开始，所以用month - 1
        c.set(Calendar.MONTH, month - 1);
        // 设置日期
        c.set(Calendar.DAY_OF_MONTH, 1);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 指定年月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {

        // 获取Calendar类的实例
        Calendar c = Calendar.getInstance();
        // 设置年份
        c.set(Calendar.YEAR, year);
        // 设置月份，因为月份从0开始，所以用month - 1
        c.set(Calendar.MONTH, month - 1);
        // 获取当前时间下，该月的最大日期的数字
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 将获取的最大日期数设置为Calendar实例的日期数
        c.set(Calendar.DAY_OF_MONTH, lastDay);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }


    /**
     * 获取上个月的日期
     * @param month yyyy-MM
     * @return String
     */
    public static String getLastMonth(String month) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Date date = simpleDateFormat.parse(month);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH,-1);
            return simpleDateFormat.format(calendar.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取过去7天内的日期数组
     * @return  日期数组
     */
    public static List<String> pastDay(String time){
        List<String> pastDaysList = new ArrayList<>();
        try {
            //我这里传来的时间是个string类型的，所以要先转为date类型的。
            SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
            Date date = sdf.parse(time);
            for (int i = 6; i >= 0; i--) {
                pastDaysList.add(getPastDate(i,date));
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past,Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String result = sdf.format(today);
        return result;
    }



    /**
     * 获取两个时间段的时间段值
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 时间类型
     * @return
     */
    public static List<String> getTimePeriodFromTwoTime(Long startTime, Long endTime, String type) {
        LocalDate start = Instant.ofEpochMilli(startTime).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        LocalDate end = Instant.ofEpochMilli(endTime).atZone(ZoneOffset.ofHours(8)).toLocalDate();

        List<String> result = new ArrayList<>();

        // 年
        if ("year".equals(type)) {
            Year startyear = Year.from(start);
            Year endYear = Year.from(end);
            // 包含最后一个时间
            for (long i = 0; i <= ChronoUnit.YEARS.between(startyear, endYear); i++) {
                result.add(startyear.plusYears(i).toString());
            }
        }
        // 月
        else if ("month".equals(type)) {
            YearMonth from = YearMonth.from(start);
            YearMonth to = YearMonth.from(end);
            for (long i = 0; i <= ChronoUnit.MONTHS.between(from, to); i++) {
                result.add(from.plusMonths(i).toString());
            }
        }
        // 日
        else {
            for (long i = 0; i <= ChronoUnit.DAYS.between(start, end); i++) {
                result.add(start.plus(i, ChronoUnit.DAYS).toString());
            }
        }
        return result;
    }








    public static String getDate(int past,Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);

        Date today = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String result = sdf.format(today);

        return result;
    }



}
