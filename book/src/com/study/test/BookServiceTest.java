package com.study.test;

import com.study.pojo.Book;
import com.study.servise.BookService;
import com.study.servise.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BookServiceTest {

    private BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(1,"哈哈","我",new BigDecimal(12.1213),1233,0,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(22);
    }
    @Test
    public void updateBook() {
        bookService.updateBook(new Book(1,"社会我国哥，人狠话不多！", "1125", new BigDecimal(999999), 10, 111110, null));
    }
    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(22));
    }
    @Test
    public void queryBooks() {
        for (Book queryBook : bookService.queryBooks()) {
            System.out.println(queryBook);
        }
    }
    @Test
    public void page(){
        System.out.println(bookService.page(0,4));
    }
    @Test
    public void pageByPrice(){
        System.out.println(bookService.pageByPrice(0,4,10,245));
    }
}