package cn.nh.kevin.demo.Exception.ExceptionDeal;

import cn.nh.kevin.demo.Enum.ResultEnum;
import cn.nh.kevin.demo.Rest.JsonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 标题:
 * 描述: 过滤器出现自定义异常处理
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-02 10:34
 */
@Component
@Slf4j
public class FilterErrorReturn {
    /**
     * @param e
     * @return cn.nh.kevin.demo.DTO.ResultDTO
     * @Description 将自定义异常作为json返回
     * @Author xck
     * @Date 2019/9/2 11:00
     **/
    public void dealFilterError(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        log.info("过滤器异常处理");
        /**
         * 组装response
         */
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Accept-Encoding", "");

        String Json = convertObjectToJson(JsonResponse.build(ResultEnum.FAIL, e.getMessage()));
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(Json.getBytes("UTF-8"));
        outputStream.flush();
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
