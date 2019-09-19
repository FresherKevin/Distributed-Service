package cn.nh.kevin.demo.Rest;

import cn.nh.kevin.demo.Enum.MessageEnum;
import cn.nh.kevin.demo.Enum.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 标题:http json返回对象
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-02 11:07
 */
@Data
public class JsonResponse<T extends Object> implements Serializable {
    private static final long serialVersionUID = 1L;
    private T data;
    private ResultEnum resultEnum;
    private String code;
    private String Message;

    public JsonResponse(T data) {
        this.data = data;
        this.resultEnum = ResultEnum.SUCCESS;
        code = ResultEnum.SUCCESS.getCode();
    }

    public JsonResponse(ResultEnum resultEnum,MessageEnum messageEnum) {
        this.Message = messageEnum.getText();
        this.code = messageEnum.getCode();
        this.resultEnum = resultEnum;
    }

    public JsonResponse(ResultEnum resultEnum, String message) {
        this.resultEnum = resultEnum;
        Message = message;
        code = resultEnum.getCode();
    }

    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<T>(data);
    }

    /**
     * @param resultEnum
     * @param messageEnum
     * @return cn.nh.kevin.demo.Rest.JsonResponse
     * @Description 错误jsonresponse
     * @Author xck
     * @Date 2019/9/6 14:19
     **/
    public static  JsonResponse build(ResultEnum resultEnum,MessageEnum messageEnum) {
        return new JsonResponse(resultEnum,messageEnum);
    }
    /**
     * @param resultEnum
     * @param message
     * @return cn.nh.kevin.demo.Rest.JsonResponse
     * @Description 成功jsonresponse
     * @Author xck
     * @Date 2019/9/6 14:20
     **/
    public static  JsonResponse build(ResultEnum resultEnum,String message) {
        return new JsonResponse(resultEnum,message);
    }
}
