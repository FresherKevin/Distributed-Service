package cn.nh.kevin.demo.Exception.DefineException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * 标题: 渠道校验异常
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-08-30 15:13
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class ChannleException extends Exception{
    private String code;
    public ChannleException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getcode() {
        return code;
    }
}
