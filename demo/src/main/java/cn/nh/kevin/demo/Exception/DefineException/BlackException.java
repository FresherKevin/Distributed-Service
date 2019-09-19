package cn.nh.kevin.demo.Exception.DefineException;

/**
 * 标题:
 * 描述:为空自定义异常
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-08-28 16:25
 */
public class BlackException extends Exception {
    public BlackException(String message, Throwable cause) {
        super(message, cause);
    }
}
