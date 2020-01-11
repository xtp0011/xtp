package com.xtp.diveinspringboot.web.servlet;
/*

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 * 异步实现
 *//*

@WebServlet(urlPatterns = "/my/servlet",asyncSupported = true)
public class MyServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext context = req.startAsync();
        context.start(()->{
            try {
                resp.getWriter().println("hello word");
                //触发完成
                context.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
*/
