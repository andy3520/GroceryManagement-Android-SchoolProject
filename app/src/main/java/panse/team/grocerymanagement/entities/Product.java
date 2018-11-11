package panse.team.grocerymanagement.entities;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.Objects;

public class Product implements Serializable {
    private String productId;
    private String productName;
    private int productQty;
    private double productPrice;
    private String information;

    public Product(String productId, String productName, int productQty, double productPrice, String information) {
        this.productId = productId;
        this.productName = productName;
        this.productQty = productQty;
        this.productPrice = productPrice;
        this.information = information;
    }

    public Product() {

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
    public static String getProductFirstName(String proName) {
        String[] words = proName.split(" ");
        return words[words.length - 1];
    }

    public static  Comparator<Product> ASC_productId= new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            String proId1 = p1.getProductId();
            String proId2 = p2.getProductId();
            return proId1.compareTo(proId2);
        }
    };
    public static  Comparator<Product> DES_productId= new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            String proId1 = p1.getProductId();
            String proId2 = p2.getProductId();
            return proId2.compareTo(proId1);
        }
    };
    public static  Comparator<Product> ASC_productName = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            String proName1 = Normalizer.normalize(Product.getProductFirstName(p1.getProductName().toUpperCase()),Normalizer.Form.NFD);
            String proName2 =Normalizer.normalize(Product.getProductFirstName(p2.getProductName().toUpperCase()),Normalizer.Form.NFD);
            return proName1.compareTo(proName2);
        }
    };
    public static  Comparator<Product> DES_productName = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            String proName1 = Normalizer.normalize(Product.getProductFirstName(p1.getProductName().toUpperCase()),Normalizer.Form.NFD);
            String proName2 =Normalizer.normalize(Product.getProductFirstName(p2.getProductName().toUpperCase()),Normalizer.Form.NFD);
            return proName2.compareTo(proName1);
        }
    };
    public static  Comparator<Product> ASC_productQty= new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            Integer qty1= p1.getProductQty();
            Integer qty2= p2.getProductQty();
            return qty1.compareTo(qty2);
        }
    };
    public static  Comparator<Product> DES_productQty= new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            Integer qty1= p1.getProductQty();
            Integer qty2= p2.getProductQty();
            return qty2.compareTo(qty1);
        }
    };
    public static Comparator<Product> ASC_productPrice = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            double price1 =p1.productPrice;
            double price2 = p2.productPrice;
            return (int)(price1-price2);
        }
    };

    public static Comparator<Product> DES_productPrice = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            double price1 =p1.productPrice;
            double price2 = p2.productPrice;
            return (int)(price2-price1);
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId);
    }
}
