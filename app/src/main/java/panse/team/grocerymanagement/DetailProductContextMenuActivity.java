package panse.team.grocerymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import panse.team.grocerymanagement.entities.Product;

public class DetailProductContextMenuActivity extends AppCompatActivity {
    private TextView tvMaSP, tvTenSP, tvSLSP, tvGiaSP, tvThongTinSP;
    private ImageButton btnTroVe;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_context_menu);
        tvMaSP = findViewById(R.id.tvMaSP);
        tvTenSP = findViewById(R.id.tvTenSanPham);
        tvSLSP = findViewById(R.id.tvSoLuong);
        tvGiaSP = findViewById(R.id.tvGia);
        tvThongTinSP = findViewById(R.id.tvThongTin);
        tvTitle= findViewById(R.id.tvTiTle);
        btnTroVe = findViewById(R.id.imgTroVe);
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("detail");
        Product product = (Product) bundle.getSerializable("product");
        tvTitle.setText("Thông tin"+" "+product.getProductName());
        tvMaSP.setText(product.getProductId());
        tvTenSP.setText(product.getProductName());
        tvSLSP.setText(product.getProductQty() + "");
        tvGiaSP.setText(product.getProductPrice() + "");
        tvThongTinSP.setText(product.getInformation());
    }
}
