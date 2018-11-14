package panse.team.grocerymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import panse.team.grocerymanagement.entities.Product;

public class DetailProductActivity extends AppCompatActivity implements FrameActi{
    private TextView tvProId, tvProName, tvQty, tvPrice, tvInfo;
    private ImageButton imgBtnBack;

    @Override
    public void init() {
        tvProId = findViewById(R.id.tvProductId);
        tvProName = findViewById(R.id.tvProductName);
        tvQty = findViewById(R.id.tvProductQty);
        tvPrice = findViewById(R.id.tvProductPrice);
        tvInfo = findViewById(R.id.tvProductInfo);
        imgBtnBack= findViewById(R.id.imgBtnBack);
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
        Product product = (Product) bundle.getSerializable("product");
        tvProName.setText(product.getProductName());
        tvProId.setText(product.getProductId());
        tvPrice.setText(String.valueOf((int) product.getProductPrice()));
        tvQty.setText(String.valueOf(product.getProductQty()));
        tvInfo.setText(product.getInformation());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        init();
        registerEvent();
        processData();
    }
}
