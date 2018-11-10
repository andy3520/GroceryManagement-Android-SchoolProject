package panse.team.grocerymanagement.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Order implements Serializable {
    private String orderId;
    private double totalOrderPrice;
    private String customerName;
    private Date orderDate;

    public Order(String orderId, String customerName, Date orderDate, double totalOrderPrice) {
        this.orderId = orderId;
        this.totalOrderPrice = totalOrderPrice;
        this.customerName = customerName;
        this.orderDate = orderDate;
    }

    public Order() {

    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getOrderDate() {
        return DateFormat.getDateInstance().format(orderDate);
    }

    public Date getOrderDateFull() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    //get customer firstnam
    public static String getOrderCusFirstName(String customerName) {
        String[] words = customerName.split(" ");
        return words[words.length - 1];
    }
    //sort
    public static Comparator<Order> ASC_orderIdComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            String orderId1 = o1.getOrderId();
            String orderId2 = o2.getOrderId();
            return orderId1.compareTo(orderId2);
        }
    };

    public static Comparator<Order> DES_orderIdComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            String orderId1 = o1.getOrderId();
            String orderId2 = o2.getOrderId();
            return orderId2.compareTo(orderId1);
        }
    };

    public static Comparator<Order> ASC_orderCusFirstNameComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            String firstName1 = Normalizer.normalize(Order.getOrderCusFirstName(o1.getCustomerName().toUpperCase()),Normalizer.Form.NFD);
            String firstName2 = Normalizer.normalize(Order.getOrderCusFirstName(o2.getCustomerName().toUpperCase()),Normalizer.Form.NFD);
            return firstName1.compareToIgnoreCase(firstName2);
        }
    };

    public static Comparator<Order> DES_orderCusFirstNameComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            String firstName1 = Normalizer.normalize(Order.getOrderCusFirstName(o1.getCustomerName().toUpperCase()),Normalizer.Form.NFD);
            String firstName2 = Normalizer.normalize(Order.getOrderCusFirstName(o2.getCustomerName().toUpperCase()),Normalizer.Form.NFD);
            return firstName2.compareToIgnoreCase(firstName1);
        }
    };


    public static Comparator<Order> ASC_orderDateComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            Date date1 = o1.getOrderDateFull();
            Date date2 = o2.getOrderDateFull();
            return date1.compareTo(date2);
        }
    };

    public static Comparator<Order> DES_orderDateComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            Date date1 = o1.getOrderDateFull();
            Date date2 = o2.getOrderDateFull();
            return date2.compareTo(date1);
        }
    };

    public static Comparator<Order> ASC_orderTotalPriceComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            double price1 = o1.getTotalOrderPrice();
            double price2 = o2.getTotalOrderPrice();
            return (int) (price1 - price2);
        }
    };

    public static Comparator<Order> DES_orderTotalPriceComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            double price1 = o1.getTotalOrderPrice();
            double price2 = o2.getTotalOrderPrice();
            return (int) (price2 - price1);
        }
    };


}
