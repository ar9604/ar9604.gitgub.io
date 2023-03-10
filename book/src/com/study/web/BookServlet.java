package com.study.web;

import com.study.pojo.Book;
import com.study.pojo.Page;
import com.study.servise.BookService;
import com.study.servise.impl.BookServiceImpl;
import com.study.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@SuppressWarnings({"all"})
@WebServlet("/manager/bookServlet")
public class BookServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 0);
        pageNo += pageNo;
        //1.获取请求参数==封装成book对象
        Book book = WebUtils.copyParaToBean(req.getParameterMap(),new Book());
        //2.调用bookService.addBook()保存图书
        bookService.addBook(book);
        //3.跳到图书列表页面
        //(请求转发的第一个斜杠是到工程名(web目录))
//        req.getRequestDispatcher("/pages/manager/book_manager.jsp?action=list").forward(req,resp);
        //请求转发按F5会重复添加,所以用请求重定向
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);

    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数id，图书编程
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //2.调用bookServlet.deleteBookById()，删除图书
        bookService.deleteBookById(id);
        //2.重定向回到图书列表管理页面 /book/manager/bookServlet?action=list
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.通过BookService查询全部图书
        List<Book> books = bookService.queryBooks();
        //2.把全部图书保存到request域中
        req.setAttribute("books",books);
        //3.请求转发到pages/manager/book_manager.jsp页面
        //(请求转发的第一个斜杠是到工程名(web目录))
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);

    }
    protected void getBook(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        //1.获取请求的参数 图书编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //2.调用bookService.queryBookById查询图书
        Book book = bookService.queryBookById(id);
        //3.保存图书到request域中
        req.setAttribute("book",book);
        //4.请求转发到pages/manager/book_edit.jsp页面
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);

    }

    /**
     * 保存修改图书的操作
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        //1.获取请求的参数==封装成为book对象
        Book book = WebUtils.copyParaToBean(req.getParameterMap(), new Book());
        //2.调用bookService.updateBook(book);修改图书
        bookService.updateBook(book);
        //3.重定向回图书列表管理页面
        // 地址：工程名/manager/servletBook?action=list
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    /**
     * 处理分页功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        //1.获取请求的参数 pageNo 和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2.调用bookService.page(pageNo,pageSize)方法;返回page对象
        Page<Book> page = bookService.page(pageNo,pageSize);

        page.setUrl("manager/bookServlet?action=page");

        //3.保存page对象到request域中
        req.setAttribute("page",page);
        //4.请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);

    }
}
