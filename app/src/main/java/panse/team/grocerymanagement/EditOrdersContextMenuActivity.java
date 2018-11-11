package panse.team.grocerymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import java.sql.Date;

import panse.team.grocerymanagement.entities.Order;


public class EditOrdersContextMenuActivity extends AppCompatActivity {
private EditText edtMaHD,edtTenKH,edtNgay,edtPrice;
private TextView tvTitle;
private ImageButton imgSave,imgTroVe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_orders_context_menu);
        edtMaHD = findViewById(R.id.edtMaHD);
        edtTenKH = findViewById(R.id.edtTenKH);
        edtNgay = findViewById(R.id.edtDate);
        edtPrice = findViewById(R.id.edtPrice);
        tvTitle = findViewById(R.id.tvTiTle);
        imgSave = findViewById(R.id.imgSave);
        imgTroVe = findViewById(R.id.imgTroVe);
        imgTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("edit");
        final Order order = (Order) bundle.getSerializable("order1");
        tvTitle.setText(order.getCustomerName());
        edtMaHD.setText(order.getOrderId());
        edtTenKH.setText(order.getCustomerName());
        edtNgay.setText(order.getOrderDate()+"");
        edtPrice.setText(order.getTotalOrderPrice()+"");

       imgSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               order.setCustomerName(edtTenKH.getText().toString());

               order.setTotalOrderPrice(Double.parseDouble(edtPrice.getText().toString()));
               Intent intent1 = new Intent();
               intent1.putExtra("edit",bundle);
               setResult(RESULT_OK,intent1);
               finish();
           }
       });
    }
}
