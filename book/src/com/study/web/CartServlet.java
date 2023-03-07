package com.study.web;

import com.google.gson.Gson;
import com.study.pojo.Book;
import com.study.pojo.Cart;
import com.study.pojo.CartItem;
import com.study.servise.BookService;
import com.study.servise.impl.BookServiceImpl;
import com.study.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cartServlet")
public class CartServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();

    /**
     * 修改商品数量
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数，商品编号，商品数量
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        int count = WebUtils.parseInt(req.getParameter("count"), 1);
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null){
            //修改商品数量
            cart.updateCount(id,count);
            //重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));

        }

    }
        /**
         * 清空购物车
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null){
            cart.clear();
            //重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));

        }
    }
        /**
         * 删除商品项
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null){
            //删除购物车的商品项
            cart.deleteItem(id);
            //重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));

        }
    }
        /**
         * 加入购物车
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("加入购物车");
//        System.out.println("商品编号" + req.getParameter("id"));
        //获取请求的参数n 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //调用bookService.queryById 得到图书的信息
        Book book = bookService.queryBookById(id);
        Integer stock = book.getStock();
        //把图书信息转化成CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        cartItem.setBookStock(book.getStock());
        //调用Cart.addItem();添加商品项
        Cart cart = (Cart)req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
//        System.out.println(cart);
        //重定向为商品列表项(重定向不支持req域共享)
        resp.sendRedirect(req.getHeader("Referer"));
        //最后一个添加的商品名称
        req.getSession().setAttribute("lastName",cartItem.getName());
    }



    protected void AjaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("加入购物车");
//        System.out.println("商品编号" + req.getParameter("id"));
        //获取请求的参数n 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //调用bookService.queryById 得到图书的信息
        Book book = bookService.queryBookById(id);
        Integer stock = book.getStock();
        //把图书信息转化成CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        cartItem.setBookStock(book.getStock());
        //调用Cart.addItem();添加商品项
        Cart cart = (Cart)req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
//        System.out.println(cart);

        //最后一个添加的商品名称
        req.getSession().setAttribute("lastName",cartItem.getName());

        req.getSession().getAttribute("lastName");

        Map<String,Object> resultMap = new HashMap<>();
        //返回购物车总的商品数量和最后一个添加的商品名称
        resultMap.put("lastName",cartItem.getName());
        resultMap.put("totalCount",cart.getTotalCount());

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);

        resp.getWriter().write(json);



    }
}
