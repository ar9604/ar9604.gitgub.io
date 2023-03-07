package com.study.web;

import com.study.pojo.User;
import com.study.servise.UserService;
import com.study.servise.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //2.检测验证码是否正确 ==>先写死
        if ("1".equalsIgnoreCase(code)){
            //正确
            //3.检测用户名是否可用(web层是不可以访问Dao层的，只能通过service层访问)
            if (userService.existsUsername(username)){
                //用户名不可用
                req.setAttribute("msg","用户名已存在");
                req.setAttribute("username",username);
                req.setAttribute("email",email);
                System.out.println("用户名" + username + "不可用");
                //跳回注册页面
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }else {
                // 可用：
                // 调用service保存到数据库
                userService.registerUser(new User(null,username,password,email));
                // 跳转到注册成功页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
            }
        }else {
            //把错误信息，和回显信息，保存到Request区域中
            req.setAttribute("msg","验证码错误");
            req.setAttribute("username",username);
            req.setAttribute("email",email);
            //跳回注册页面
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }

    }
}
