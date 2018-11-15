package panse.team.grocerymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import panse.team.grocerymanagement.customadapteractivity.DetailOrderListAdapter;
import panse.team.grocerymanagement.entities.Order;
import panse.team.grocerymanagement.entities.Product;


public class DetailOrdersContextMenuActivity extends AppCompatActivity implements FrameActi {

    private TextView tvOrderId, tvCusName, tvOrderDate, tvTotalPrice;
    private ImageButton imgBtnBack;
    private ArrayList<Product> products;
    private ListView lvOrderDetails;
    private DetailOrderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_orders_context_menu);
        init();
        registerEvent();
        processData();
        applydDataListView();
    }

    @Override
    public void init() {
        tvOrderId = findViewById(R.id.tvOrderId);
        tvCusName = findViewById(R.id.tvCustomerName);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvTotalPrice = findViewById(R.id.tvTotalOrderPrice);
        imgBtnBack = findViewById(R.id.imgBtnBack);
        lvOrderDetails = findViewById(R.id.lvListOrderDetail);
    }

    @Override
    public void registerEvent() {
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void processData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("detail");
        Order order = (Order) bundle.getSerializable("order");
        tvOrderId.setText(order.getOrderId());
        tvCusName.setText(order.getCustomerName());
        tvOrderDate.setText(order.getOrderDate());
        tvTotalPrice.setText((int) order.getTotalOrderPrice() + "");
    }

    // Dữ liệu giả
    public void applydDataListView() {
        products = new ArrayList<>();
        products.add(new Product("83156986", "Redbull", 5, 15000, "Nước tăng lực"));
        products.add(new Product("83156989", "Trứng gà", 1, 5000, "Trứng gà công nghiệp"));
        products.add(new Product("8315698745", "Mì tôm", 3, 7000, "Mì tôm chua cay"));
        products.add(new Product("8315698787", "Khăn", 1, 20000, "Khăn mặt"));
        adapter = new DetailOrderListAdapter(this, R.layout.custom_detail_order_item_orderdetail, products);
        lvOrderDetails.setAdapter(adapter);
    }
}
