package com.study.web;

import com.study.pojo.Cart;
import com.study.pojo.User;
import com.study.servise.OrderService;
import com.study.servise.impl.OrderServiceImpl;
import com.study.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orderServlet")
public class OrderServlet extends BaseServlet{

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //先获取cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //获取userId
        User loginUser = (User) req.getSession().getAttribute("user");
        if (loginUser == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }
        Integer userId = loginUser.getId();
        //调用orderService.createOrder(cart,userId) 生成订单
        String orderId = orderService.createOrder(cart, userId);


        //req.setAttribute("orderId",orderId);
        // 请求转发到/pages/cart/checkout.jsp
        //req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req, resp);
        //防止用户重复提交，使用重定向
        req.getSession().setAttribute("orderId",orderId);
        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }
}
