package com.example.administrator.entity;

/**
 * Created by User on 2016/10/13.
 */

/**
   * 购物车实体类
  * 测试
  */
 public class Product {
        //商品名称
                 private String name;
         // 商品数量
         private int num;
      // 该商品总价
           private int price;
    private int danjia;

          @Override
       public String toString() {
            return "Product{" +
                                "name='" + name + '\'' +
                                ", num=" + num +
                                ", price=" + price +
                                '}';
          }
                public void setName(String name) {
                 this.name = name;
            }

                 public void setNum(int num) {
                this.num = num;
        }

                public void setPrice(int price) {
               this.price = price;
          }

            public String getName() {
              return name;
          }

               public int getNum() {
                 return num;
           }

                public int getPrice() {
               return price;
            }

    public int getDanjia() {
        return danjia;
    }

    public void setDanjia(int danjia) {
        this.danjia = danjia;
    }
}

