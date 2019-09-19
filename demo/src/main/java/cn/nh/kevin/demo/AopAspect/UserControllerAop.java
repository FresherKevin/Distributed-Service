package cn.nh.kevin.demo.AopAspect;

import cn.nh.kevin.demo.DTO.UserDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 标题:
 * 描述: UserController  AOP
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-08-28 16:28
 */
@Aspect
@Component
public class UserControllerAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerAop.class);

    @Pointcut("execution(* cn.nh.kevin.demo.Controller..UserController.register(..))")
    //@Pointcut("within(cn.nh.kevin.demo.Controller.UserController)")
    public void pointCut() {
    }

    /**
     * @param
     * @return void
     * @Description Controller之前的增强
     * @Author xck
     * @Date 2019/8/28 16:52
     **/
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String value = String.valueOf(request.getAttribute("name"));
        UserDTO userDTO = (UserDTO) joinPoint.getArgs()[0];
        String nameValue = userDTO.getName();
        LOGGER.info("name传入AOP之前是{}", nameValue);
        value = value + "_passAOP";
        nameValue = nameValue + "_passAOP";
        request.setAttribute("name", nameValue);
        LOGGER.info("name经过AOP之后是{}", nameValue);
    }
}
