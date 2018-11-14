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

import panse.team.grocerymanagement.orderfragment.OrderListFragment;
import panse.team.grocerymanagement.productfragment.ProductListFragment;
import panse.team.grocerymanagement.salefragment.SaleFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView navigation;


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
