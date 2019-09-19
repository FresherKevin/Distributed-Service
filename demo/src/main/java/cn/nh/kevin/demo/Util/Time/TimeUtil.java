package cn.nh.kevin.demo.Util.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 标题: 时间工具类
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-06 15:04
 */
public class TimeUtil {

    public static Date string2Date(String time) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date=null;
        try {
            date = sdf.parse(time);
        }catch (ParseException e){
            date=null;
        }finally {
            return date;
        }
    }

    public static String date2String(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
    /**
     * @param time1
     * @param time2
     * @return boolean
     * @Description true   time1早于time2
     *              false  time1晚于time2
     * @Author xck
     * @Date 2019/9/6 15:14
     **/
    public static boolean timeCompare(String time1,String time2){
        Date var1 = TimeUtil.string2Date(time1);
        Date var2 = TimeUtil.string2Date(time2);
        return var1.before(var2)?true:false;
    }
}
