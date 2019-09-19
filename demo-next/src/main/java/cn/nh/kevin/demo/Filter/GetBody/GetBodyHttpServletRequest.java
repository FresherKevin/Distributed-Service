package cn.nh.kevin.demo.Filter.GetBody;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 标题: 获取httpServletRequest中body
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-03 11:01
 */
@Data
public class GetBodyHttpServletRequest {
    private static GetBodyHttpServletRequest getBodyHttpServletRequest;
    private HttpServletRequest request;

    private GetBodyHttpServletRequest() {
    }

    /**
     * @param request
     * @return cn.nh.kevin.demo.Filter.GetBody.GetBodyHttpServletRequest
     * @Description 懒汉双重判断单例模式
     * @Author xck
     * @Date 2019/9/3 18:24
     **/
    public static GetBodyHttpServletRequest getInstance(HttpServletRequest request) {
        if (getBodyHttpServletRequest == null) {
            synchronized (GetBodyHttpServletRequest.class) {
                if (getBodyHttpServletRequest == null) {
                    getBodyHttpServletRequest = new GetBodyHttpServletRequest();
                    getBodyHttpServletRequest.setRequest(request);
                }
            }
        }
        return getBodyHttpServletRequest;
    }

    /**
     * @param clazz
     * @return T
     * @Description 根据Request获取请求参数Body，并转换成响应的DTO
     * @Author xck
     * @Date 2019/9/3 18:23
     **/
    public <T> T doChange(Class<T> clazz) throws IOException {
        BufferedReader br = request.getReader();
        String str;
        StringBuilder wholeStr = new StringBuilder();
        while ((str = br.readLine()) != null) {
            wholeStr.append(str);
        }
        JSONObject.parseObject(wholeStr.toString(), clazz);
        return (T) JSONObject.parseObject(wholeStr.toString(), clazz);
    }
}
