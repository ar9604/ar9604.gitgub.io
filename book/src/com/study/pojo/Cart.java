package com.study.pojo;

import com.study.Dao.BookDao;
import com.study.Dao.impl.BookDaoImpl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
//    private Integer totalCount;
//    private BigDecimal totalPrice;
    private Map<Integer, CartItem> items = new LinkedHashMap<Integer, CartItem>();

    /**
     * 添加商品项
     * @param carItem
     */
    public void addItem(CartItem carItem){
        //先查看购物车中是否已经添加过此商品，
        // 如果已经添加：数量累加，总金额更新
        // 如果没有添加：直接放到集合中
        CartItem item = items.get(carItem.getId());
        if (item == null){
            //之前没添加过此商品
            items.put(carItem.getId(),carItem);
        }else {
            //已经添加过
            item.setCount(item.getCount() + 1);//数量累加
            //更新总价格
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }
    /**
     * 删除商品项
     * @param id
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }
    /**
     * 清空购物车
     */
    public void clear(){
        items.clear();
    }
    /**
     * 修改商品数量
     */
    public void updateCount(Integer id,Integer count){
        //先查看购物车中是否有此商品，如果有更改商品数量，修改总金额
        CartItem carItem = items.get(id);
        BookDao bookDao = new BookDaoImpl();
        Book book = bookDao.queryBookById(carItem.getId());
        Integer bookStock = book.getStock();
        if (carItem != null){
            //已经添加过
            if (count > bookStock){
                carItem.setCount(bookStock);
                carItem.setTotalPrice(carItem.getPrice().multiply(new BigDecimal(bookStock)));
            }else {
                carItem.setCount(count);//数量累加
                //更新总价格
                carItem.setTotalPrice(carItem.getPrice().multiply(new BigDecimal(count)));
            }
        }
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
//        Iterator<Map.Entry<Integer, CartItem>> iterator = items.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Integer, CartItem> next =  iterator.next();
//            totalCount += next.getValue().getCount();
//        }
        for (Map.Entry<Integer,CartItem> entry : items.entrySet()){
            totalCount += entry.getValue().getCount();
        }

        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer,CartItem> entry : items.entrySet()){
        totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Car{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
