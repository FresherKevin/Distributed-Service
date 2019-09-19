package cn.nh.kevin.demo.Filter.GetBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 标题:获取HttpServletResponse中body
 * 描述:毫无作用
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-03 18:26
 */
public class GetBodyHttpServletResponse {
    private static GetBodyHttpServletResponse getBodyHttpServletResponse;
    private HttpServletResponse response;

    private GetBodyHttpServletResponse() {
    }

    public GetBodyHttpServletResponse getInstance(HttpServletResponse response) {
        if (getBodyHttpServletResponse == null)
            synchronized (GetBodyHttpServletResponse.class) {
                if (getBodyHttpServletResponse == null) {
                    getBodyHttpServletResponse = new GetBodyHttpServletResponse();
                }
            }
        return getBodyHttpServletResponse;
    }
}
