package com.example.administrator.entity;

/**
 * Created by User on 2016/10/13.
 */

import java.io.Serializable;

/**
   * 购物车实体类
  * 测试
  */
 public class Product implements Serializable {
        //商品名称
                 private String name;
         // 商品数量
         private int num;
      // 该商品总价
           private double price;
    private double danjia;
private String ID;
          @Override
       public String toString() {
            return "Product{" +
                                "name='" + name + '\'' +
                                ", num=" + num +
                                ", price=" + price +
                                '}';
          }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDanjia() {
        return danjia;
    }

    public void setDanjia(double danjia) {
        this.danjia = danjia;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

