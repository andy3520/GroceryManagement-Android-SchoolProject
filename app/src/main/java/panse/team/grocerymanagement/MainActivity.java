package panse.team.grocerymanagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import panse.team.grocerymanagement.orderfragment.OrderListFragment;
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
                    fragmentManager = getSupportFragmentManager();
                    SaleFragment orderListFragment = new SaleFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentMainContainer, orderListFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.nav_sanpham:

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

}
