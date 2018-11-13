package panse.team.grocerymanagement.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class OrderDetails implements Serializable {
    public String orderDetailId;
    private String orderId;
    private String productId;
    private int orderDetailQty;
    private double orderDetailPrice;
    private Date orderDetailDate;

    // constructor
    public OrderDetails(String orderDetailId, String orderId, String productId, int orderDetailQty, double orderDetailPrice, Date orderDetailDate) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.orderDetailQty = orderDetailQty;
        this.orderDetailPrice = orderDetailPrice;
        this.orderDetailDate = orderDetailDate;
    }

    public OrderDetails() {
    }

    // getter & setter
    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
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

    public String getOrderDetailDateString() {
        return DateFormat.getDateInstance().format(orderDetailDate);
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

    // sort
    public static Comparator<OrderDetails> ASC_orderDetailIdComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            String orderId1 = o1.getOrderDetailId();
            String orderId2 = o2.getOrderDetailId();
            return orderId1.compareTo(orderId2);
        }
    };

    public static Comparator<OrderDetails> DES_orderDetailIdComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            String orderId1 = o1.getOrderDetailId();
            String orderId2 = o2.getOrderDetailId();
            return orderId2.compareTo(orderId1);
        }
    };

    public static Comparator<OrderDetails> ASC_orderDetailOrdIdComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            String orderId1 = o1.getOrderId();
            String orderId2 = o2.getOrderId();
            return orderId1.compareTo(orderId2);
        }
    };

    public static Comparator<OrderDetails> DES_orderDetailOrdIdComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            String orderId1 = o1.getOrderId();
            String orderId2 = o2.getOrderId();
            return orderId2.compareTo(orderId1);
        }
    };

    public static Comparator<OrderDetails> ASC_orderDetailProIdComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            String orderId1 = o1.getProductId();
            String orderId2 = o2.getProductId();
            return orderId1.compareTo(orderId2);
        }
    };

    public static Comparator<OrderDetails> DES_orderDetailProIdComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            String orderId1 = o1.getProductId();
            String orderId2 = o2.getProductId();
            return orderId2.compareTo(orderId1);
        }
    };

    public static Comparator<OrderDetails> ASC_orderDetailDateComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            Date date1 = o1.getOrderDetailDate();
            Date date2 = o2.getOrderDetailDate();
            return date1.compareTo(date2);
        }
    };

    public static Comparator<OrderDetails> DES_orderDetailDateComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            Date date1 = o1.getOrderDetailDate();
            Date date2 = o2.getOrderDetailDate();
            return date2.compareTo(date1);
        }
    };

    public static Comparator<OrderDetails> ASC_orderDetailPriceComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            double price1 = o1.getOrderDetailPrice();
            double price2 = o2.getOrderDetailPrice();
            return (int) (price1 - price2);
        }
    };

    public static Comparator<OrderDetails> DES_orderDetailPriceComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            double price1 = o1.getOrderDetailPrice();
            double price2 = o2.getOrderDetailPrice();
            return (int) (price2 - price1);
        }
    };

    public static Comparator<OrderDetails> ASC_orderDetailQtyComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            int qty1 = o1.getOrderDetailQty();
            int qty2 = o2.getOrderDetailQty();
            return qty1 - qty2;
        }
    };

    public static Comparator<OrderDetails> DES_orderDetailQtyComparator = new Comparator<OrderDetails>() {
        @Override
        public int compare(OrderDetails o1, OrderDetails o2) {
            int qty1 = o1.getOrderDetailQty();
            int qty2 = o2.getOrderDetailQty();
            return qty2 - qty1;
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return Objects.equals(orderDetailId, that.orderDetailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDetailId);
    }
}
