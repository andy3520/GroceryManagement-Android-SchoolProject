package panse.team.grocerymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import panse.team.grocerymanagement.dao.ProductManager;
import panse.team.grocerymanagement.entities.Product;
import panse.team.grocerymanagement.orderfragment.OrderListFragment;
import panse.team.grocerymanagement.productfragment.ProductListFragment;
import panse.team.grocerymanagement.salefragment.SaleFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView navigation;
    private ProductManager productManager;

    // Sự kiện thanh điều hướng bên dưới
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_donhang:
                    addOrderList();
                    return true;
                case R.id.nav_banhang:
                    addOrderDetail();
                    return true;
                case R.id.nav_sanpham:
                    addProductList();
                    return true;
                case R.id.nav_phantich:

                    return true;
            }
            return false;
        }
    };

    // ^^^

    // Khởi tạo các view
    private void init() {
        navigation = findViewById(R.id.navigation);
        productManager = new ProductManager(this);
//        productManager.createProduct(new Product("18091000", "Mì hảo hảo", 50, 5000, "Mì hảo hảo tôm chua cay"));
//        productManager.createProduct(new Product("18091001", "Bút bi Thiên Long", 600, 5000, "Bút bi Thiên Long BTL1"));
//        productManager.createProduct(new Product("18091002", "Nước mắn Nam Ngư", 20, 29000, "Thơm ngon"));
//        productManager.createProduct(new Product("18091003", "Coca cola 500ml", 50, 8000, "Nước ngọt coca cola 500ml"));
//        productManager.createProduct(new Product("18091004", "Mì Ly Omachi Xúc Xích", 50, 17000, "Mì Omachi có xúc xích 200g "));
//        productManager.createProduct(new Product("18091005", "Trứng gà", 30, 5000, "Trứng gà công nghiệp"));
//        productManager.createProduct(new Product("18091006", "Sữa đặc Phương Nam", 10, 30000, "Mì hảo hảo tôm chua cay"));
//        productManager.createProduct(new Product("18091007", "Bánh bông lan cuộn", 4, 23000, "Bánh bông lan cuộn 200g"));
//        productManager.createProduct(new Product("18091008", "Kim chi gói 200g", 10, 30000, "Kim chi đóng gói 200g"));
//        productManager.createProduct(new Product("18091009", "Bánh bao trứng cút", 5, 15000, "Bánh bao thịt nhân trứng cút"));
    }

    // ^^^

    // Đăng kí sự kiện
    private void registerEvent() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    // ^^^


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Xin chào", Toast.LENGTH_SHORT).show();
        init();
        registerEvent();

        // Màn hình mặc định sẽ là danh sách hóa đơn
        addOrderList();
        // ^^^
    }

    // Add OrderListFragment vào Main Container
    private void addOrderList() {
        fragmentManager = getSupportFragmentManager();
        OrderListFragment orderListFragment = new OrderListFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMainContainer, orderListFragment);
        fragmentTransaction.commit();
    }

    private void addProductList() {
        fragmentManager = getSupportFragmentManager();
        ProductListFragment productListFragment = new ProductListFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMainContainer, productListFragment);
        fragmentTransaction.commit();
    }

    private void addOrderDetail() {
        fragmentManager = getSupportFragmentManager();
        SaleFragment orderListFragment = new SaleFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMainContainer, orderListFragment);
        fragmentTransaction.commit();
    }
}
