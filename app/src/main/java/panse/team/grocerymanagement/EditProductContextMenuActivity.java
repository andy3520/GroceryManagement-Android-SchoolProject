package panse.team.grocerymanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import panse.team.grocerymanagement.entities.Product;

public class EditProductContextMenuActivity extends AppCompatActivity {
    private EditText edtMaSP, edtTenSP, edtSLSP, edtGia, edtThongTin;
    ImageButton imgSave, imgTroVe;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_context_menu);
        edtMaSP = findViewById(R.id.edtMaSP);
        edtTenSP = findViewById(R.id.edtTenSanPham);
        edtSLSP = findViewById(R.id.edtSoLuong);
        edtGia = findViewById(R.id.edtGia);
        tvTitle = findViewById(R.id.tvTiTle);
        edtThongTin = findViewById(R.id.edtThongTin);
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
            final Product product = (Product) bundle.getSerializable("product1");
            tvTitle.setText("Sửa thông tin"+" "+product.getProductName());
            edtMaSP.setText(product.getProductId());
            edtTenSP.setText(product.getProductName());
            edtSLSP.setText(product.getProductQty() + "");
            edtGia.setText(product.getProductPrice() + "");
            edtThongTin.setText(product.getInformation());


            imgSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    product.setProductName(edtTenSP.getText().toString());
                    product.setProductQty(Integer.parseInt(edtSLSP.getText().toString()));
                    product.setProductPrice(Double.parseDouble(edtGia.getText().toString()));
                    product.setInformation(edtThongTin.getText().toString());
                    Intent intent1 = new Intent();
                    intent1.putExtra("edit", bundle);
                    setResult(RESULT_OK, intent1);
                    finish();
                }
            });

        }
}
