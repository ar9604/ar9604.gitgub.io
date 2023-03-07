package com.study.servise.impl;

import com.study.Dao.BookDao;
import com.study.Dao.OrderDao;
import com.study.Dao.OrderItemDao;
import com.study.Dao.impl.BookDaoImpl;
import com.study.Dao.impl.OrderDaoImpl;
import com.study.Dao.impl.OrderItemDaoImpl;
import com.study.pojo.*;
import com.study.servise.OrderService;

import java.util.Date;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {

        //订单号===唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
        //创建一个订单对象
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
        //保存订单
        orderDao.saveOrder(order);
        int a = 2/0;
        //遍历购物车中每一个商品项转成订单项
        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer, CartItem> map :items.entrySet()) {
            //购物车中的每一个商品项
            CartItem value = map.getValue();
            //转化为订单项
            OrderItem orderItem = new OrderItem(null,value.getName(),value.getCount(),value.getPrice(),value.getTotalPrice(),orderId);
            //保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            //更新库存和销量
            Book book = bookDao.queryBookById(value.getId());
            book.setSales( book.getSales() + value.getCount());
            book.setStock( book.getStock() - value.getCount());
            bookDao.updateBook(book);

        }
        //结账后清空购物车
        cart.clear();

        return orderId;
    }
}
