package cn.nh.kevin.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 标题:
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-08-27 17:13
 */
@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("index")
    public String get() {
        return "indexTTTTT";
    }
}
