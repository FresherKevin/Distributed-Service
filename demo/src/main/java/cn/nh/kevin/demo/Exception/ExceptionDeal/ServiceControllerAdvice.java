package cn.nh.kevin.demo.Exception.ExceptionDeal;

import cn.nh.kevin.demo.DTO.ResultDTO;
import cn.nh.kevin.demo.Enum.MessageEnum;
import cn.nh.kevin.demo.Enum.ResultEnum;
import cn.nh.kevin.demo.Exception.DefineException.ChannleException;
import cn.nh.kevin.demo.Exception.DefineException.IdConflctException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 标题:
 * 描述: 统一异常处理类
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-08-30 17:39
 */
@ControllerAdvice
public class ServiceControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceControllerAdvice.class);

    @ResponseBody
    @ExceptionHandler({ChannleException.class})
    public ResultDTO channelExceptionHandle(ChannleException e) {
        LOGGER.info("Channel异常处理");
        return new ResultDTO(ResultEnum.FAIL, MessageEnum.getMessageEnumByCode(e.getcode()));
    }

    @ResponseBody
    @ExceptionHandler({IdConflctException.class})
    public ResultDTO idConflictExceptionHandle(IdConflctException e) {
        LOGGER.info("主键冲突异常处理");
        return new ResultDTO(ResultEnum.FAIL, MessageEnum.getMessageEnumByCode(e.getErrorCode()));
    }
}
