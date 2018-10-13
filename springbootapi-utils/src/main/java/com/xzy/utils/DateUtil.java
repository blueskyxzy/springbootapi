package com.xzy.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by laborc on 2018/4/26.
 */
public class DateUtil {
    public DateUtil() {
    }

    public static Date stringToDate(String dateStr, String formatStr) {
        if(dateStr != null && !"".equals(dateStr)) {
            DateFormat format = new SimpleDateFormat(formatStr);
            Date date = null;

            try {
                date = format.parse(dateStr);
            } catch (ParseException var5) {
                var5.printStackTrace();
            }

            return date;
        } else {
            return null;
        }
    }

    public static String dateToString(Date date, String formatStr) {
        if(date == null) {
            return null;
        } else {
            try {
                DateFormat format = new SimpleDateFormat(formatStr);
                return format.format(date);
            } catch (Exception var3) {
                var3.getMessage();
                return null;
            }
        }
    }

    public static String getDateUUid() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            return format.format(new Date());
        } catch (Exception var2) {
            var2.getMessage();
            return null;
        }
    }

    public static String dateAdd(String baseDate, int addNum) throws ParseException {
        if(baseDate != null && !"".equals(baseDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = sdf.parse(baseDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            cal.add(5, 1);
            return sdf.format(cal.getTime());
        } else {
            return null;
        }
    }
    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
