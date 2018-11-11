package panse.team.grocerymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import panse.team.grocerymanagement.entities.Order;


public class DetailOrdersContextMenuActivity extends AppCompatActivity {

private TextView tvTitle,tvMaHD,tvTenKH,tvNgay,tvPrice;
private ImageButton imgTroVe,imgSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_orders_context_menu);
   tvMaHD = findViewById(R.id.tvMaHD);
   tvTenKH = findViewById(R.id.tvTenKH);
   tvNgay = findViewById(R.id.tvDate);
   tvPrice = findViewById(R.id.tvPrice);
        imgSave = findViewById(R.id.imgSave);
        imgTroVe=findViewById(R.id.imgTroVe);
        tvTitle = findViewById(R.id.tvTiTle);
        imgTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("detail");
        Order order = (Order) bundle.getSerializable("order");
        tvTitle.setText(order.getCustomerName());
        tvMaHD.setText(order.getOrderId());
        tvTenKH.setText(order.getCustomerName());
        tvNgay.setText(order.getOrderDate() );
        tvPrice.setText(order.getTotalOrderPrice() + "");

    }
}
