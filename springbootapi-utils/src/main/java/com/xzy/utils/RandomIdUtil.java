package com.xzy.utils;

import java.util.Random;

/**
 * @remark: RandomIdUtil
 * 随机组合16位ID,【并发小的时候使用】
 * @author: xiatl
 * @create: 2018-06-26 12:15
 **/
public class RandomIdUtil {

    /**
     * 生成随机数
     *
     * @return str
     */
    public static String get() {
        Long h = System.currentTimeMillis();//获得当前时间的毫秒数
        String str = h.toString();//转化为字符串
        int i = str.length();//总长度
        int j = i - 7;//用来取此字符串的末尾7位数，因为前面的数是年份什么的基本不变，我们只用后面的7位
        char[] charArray = str.substring(j, i).toCharArray();//将取到的7位数做成数组
        //将26位字母做成数组
        String[] arr = {"a", "b", "c", "d", "e", "f", "g",
                "h", "i", "g", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z"};
        //将字母数组随机取3个字母组成一个字符串，一共组成3个字符串放到目标数组target中
        StringBuffer uniqueId = new StringBuffer();//用于生成唯一ID
        Random random = new Random();//用于取随机数和布尔值
        boolean insertflag = true;//用来控制是插入数字还是字母
        int timecount = 0;//用来控制插入数字的长度，别超过7
        int zimucount = 0;//用来控制插入字母的总数，别超过9 7个数字加上9个字母组合
        boolean timeflag = true;//判断时间是否插入了7位，默认true为不满
        boolean zimuflag = true;//判断字幕是否插入了9位，默认true为不满
        while (zimucount < 9 || timecount < 7) {//开始组合
            if (insertflag) {//默认为ture，先加字母，你也可以先加数字
                if (zimucount < 9) {//如果uniqueId插入的字幕总数没超过9个
                    uniqueId.append(arr[random.nextInt(26)]);//则在字母数组中随机选一个插入
                    zimucount++;//对应加1
                    if (timeflag) {//如果时间没有插入满7位则重新抓阄看插入时间还是数字
                        insertflag = random.nextBoolean();//重置flag，随机产生false还是true
                    }//如果timeflag=false,时间数字已经插入满7位，则不抓阄了。保持insertflag=true
                } else {//如果已经加够了否则不操作，
                    zimuflag = false;//将zimuflag变为已加够，false
                    insertflag = false;//将插入权判断给时间数字
                }
            } else {
                if (timecount < 7) {//先加时间转化成的数组，你也可以先加字母
                    //此处取时间数字数组不能用random随机取。那样用时间来生成数组就没意义了
                    uniqueId.append(charArray[timecount]);//不可打乱顺序
                    timecount++;//对应加1
                    if (zimuflag) {//如果字母没有插入满9位则重新抓阄看插入时间还是数字
                        insertflag = random.nextBoolean();
                    }//如果zimuflag=false,字幕已经插入满9位，则不抓阄了。保持insertflag=false
                } else {
                    timeflag = false;//将timeflag变为已加够，false
                    insertflag = true;//将插入权判断给字母
                }
            }
        }
        return uniqueId.toString();
    }

}
