## SpringBoot(updateing)
*****
* @RestController和@Controller  
  1. @RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用  
  2. 如果只是使用@RestController注解Controller，则Controller中的方法无法返回jsp页面，配置的视图解析器InternalResourceViewResolver则不起作用，返回的内容就是Return 里的内容（String/JSON）。
     例如：本来应该到success.jsp页面的，则其显示success.
     ``` java
     @RequestMapping(value = "/test")
     public String test(HttpServletRequest request, HttpServletResponse response){
          return "success";
     }
     ```
  3. 如果使用@RestController注解Controller，需要返回到指定页面，则需要配置视图解析器InternalResourceViewResolver，可以利用ModelAndView返回视图。
     ``` java
      @RequestMapping(value = "/test")
      public String test(HttpServletRequest request, HttpServletResponse response){
           return newModelAndView("success");
      }
      ```
  4. 如果使用@Controller注解Controller，如果需要返回JSON，XML或自定义mediaType内容到页面，则需要在对应的方法上加上@ResponseBody注解。
     ``` java
     @ResponseBody
     @RequestMapping(value = "/test")
     public String test(HttpServletRequest request, HttpServletResponse response){
          return "success";
     }
     ```
*****
