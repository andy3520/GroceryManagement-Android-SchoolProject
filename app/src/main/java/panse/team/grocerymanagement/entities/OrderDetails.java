package panse.team.grocerymanagement.entities;

import java.io.Serializable;
import java.util.Date;

public class OrderDetails implements Serializable {
    private String orderId;
    private long productId;
    private int orderDetailQty;
    private double orderDetailPrice;
    private Date orderDetailDate;

    public OrderDetails(String orderId, long productId, int orderDetailQty, double orderDetailPrice, Date orderDetailDate) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderDetailQty = orderDetailQty;
        this.orderDetailPrice = orderDetailPrice;
        this.orderDetailDate = orderDetailDate;
    }

    public OrderDetails() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getOrderDetailQty() {
        return orderDetailQty;
    }

    public void setOrderDetailQty(int orderDetailQty) {
        this.orderDetailQty = orderDetailQty;
    }

    public double getOrderDetailPrice() {
        return orderDetailPrice;
    }

    public void setOrderDetailPrice(double orderDetailPrice) {
        this.orderDetailPrice = orderDetailPrice;
    }

    public Date getOrderDetailDate() {
        return orderDetailDate;
    }

    public void setOrderDetailDate(Date orderDetailDate) {
        this.orderDetailDate = orderDetailDate;
    }
}
