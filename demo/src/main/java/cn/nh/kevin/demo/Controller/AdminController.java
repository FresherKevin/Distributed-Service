package cn.nh.kevin.demo.Controller;

import cn.nh.kevin.demo.DTO.ResultDTO;
import cn.nh.kevin.demo.DTO.UserDTO;
import cn.nh.kevin.demo.Enum.MessageEnum;
import cn.nh.kevin.demo.Enum.ResultEnum;
import cn.nh.kevin.demo.Rest.JsonResponse;
import cn.nh.kevin.demo.Service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 标题: 用户登录 纯后端
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-06 10:24
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private LoginService loginService;

    @RequestMapping("/index")
    public JsonResponse<ResultDTO> getInfo() {
        return JsonResponse.success(new ResultDTO(ResultEnum.SUCCESS, MessageEnum.indexMessage));
    }

    //@ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse login(@RequestBody UserDTO user, HttpServletRequest request) {
        LOGGER.info("session is {}", request.getSession().getId());
        String id = user.getId();
        String password = user.getPassword();
        ResultDTO resultDTO = loginService.check(id,password);
        if (resultDTO.getResult().equals(ResultEnum.SUCCESS)) {
            HttpSession session = request.getSession();
            LOGGER.info("session是否缓存{}", session.getAttribute("user") == null);
            session.setAttribute("user", user);
            LOGGER.info("id:{}", user.getId());
            return JsonResponse.build(ResultEnum.SUCCESS,resultDTO.getMessage().getText());
        } else return JsonResponse.build(ResultEnum.FAIL,resultDTO.getMessage());
    }

    @RequestMapping(value = "/main")
    public ModelAndView toMain() {
        return new ModelAndView("main");
    }

    @RequestMapping(value = "/tologin")
    public ModelAndView toLogin() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResponse<ResultDTO> register(@RequestBody UserDTO userDTO) {
        if (userDTO != null) {
            LOGGER.info("name 是{}", userDTO.getName());
            try {
                loginService.register(userDTO);
            } catch (DuplicateKeyException e) {
                return JsonResponse.build(ResultEnum.FAIL,MessageEnum.idConflictMessage);
            } catch (Exception e) {
                return JsonResponse.build(ResultEnum.FAIL, MessageEnum.registerFailMessage);
            }
            return JsonResponse.build(ResultEnum.SUCCESS,MessageEnum.registerSuccessMessage);
        } else return JsonResponse.build(ResultEnum.FAIL, MessageEnum.inputEmptyMessage);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public JsonResponse<UserDTO> queryById(String id) {
        UserDTO userDTO = loginService.queryById(id);
        if (userDTO!=null)
        return JsonResponse.success(userDTO);
        else return JsonResponse.build(ResultEnum.FAIL,MessageEnum.idNotFindMessage);
    }
}
