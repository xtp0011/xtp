package com.xtp.qqmusic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    /**
     * 转换时间
     * @param duration
     * @return
     */
    public static String formatTime(int duration) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return  simpleDateFormat.format(new Date(duration));
    }

    public static String formatSystemTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");//24小时
        return simpleDateFormat.format(new Date());
    }
}
