package com.study.web;

import com.google.gson.Gson;
import com.study.pojo.User;
import com.study.servise.UserService;
import com.study.servise.impl.UserServiceImpl;
import com.study.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_BORDER_THICKNESS;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@WebServlet("/userServlet")
@SuppressWarnings({"all"})
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数
        String username = req.getParameter("username");
        //调用userService.existsUsername()
        boolean existsUsername = userService.existsUsername(username);
        //把返回的结果封装成为map对象
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername",existsUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        System.out.println(json);

        resp.getWriter().write(json);

    }
        /**
         * 实现注销功能
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void loginOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.销毁session中用户登录的信息
        req.getSession().invalidate();
        //2.重定向到首页(或登录页面)
        resp.sendRedirect(req.getContextPath());


    }

        /**
         * 处理登录的功能
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 调用 userService.login()登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));
        // 如果等于 null,说明登录 失败!
        if (loginUser == null) {
            //把错误信息，和回显信息，保存到Request区域中
            req.setAttribute("msg", "用户或密码错误");
            req.setAttribute("username", username);
            // 跳回登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {
            // 登录 成功
            //保存用户登录之后的信息到session域中
            req.getSession().setAttribute("user",loginUser);
            //跳到成功页面 login_success.jsp
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }

    /**
     * 处理注册的功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取session中的验证码
        String token = (String)req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //删除session中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //直接把获取的参数封装到user里面
        User user = WebUtils.copyParaToBean(req.getParameterMap(),new User());

        //2.检测验证码是否正确 ==>先写死
        if (token != null && token.equalsIgnoreCase(code)){
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
