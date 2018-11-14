package panse.team.grocerymanagement;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import panse.team.grocerymanagement.entities.Product;

public class EditProductActivity extends AppCompatActivity implements FrameActi {
    private EditText edtProName, edtProQty, edtProPrice, edtProInfo;
    private TextView tvProId;
    private ImageButton imgBtnSave, imgBtnBack;

    @Override
    public void init() {
        tvProId = findViewById(R.id.tvProId);
        edtProName = findViewById(R.id.edtProName);
        edtProQty = findViewById(R.id.edtProQty);
        edtProPrice = findViewById(R.id.edtProPrice);
        edtProInfo = findViewById(R.id.edtProInfo);
        imgBtnSave = findViewById(R.id.imgBtnSave);
        imgBtnBack = findViewById(R.id.imgBtnBack);
    }

    @Override
    public void registerEvent() {
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle bundle = intent.getBundleExtra("edit");
                Product product = (Product) bundle.getSerializable("product");
                if (edtProName.getText().toString().equals("")) {
                    Toast.makeText(EditProductActivity.this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                    hideKeyboard(EditProductActivity.this);
                } else if (edtProQty.getText().toString().equals("")) {
                    Toast.makeText(EditProductActivity.this, "Vui lòng nhập số lượng sản phẩm", Toast.LENGTH_SHORT).show();
                    hideKeyboard(EditProductActivity.this);
                } else if (edtProPrice.getText().toString().equals("")) {
                    Toast.makeText(EditProductActivity.this, "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
                    hideKeyboard(EditProductActivity.this);
                } else {
                    product.setProductId(tvProId.getText().toString());
                    product.setProductName(edtProName.getText().toString());
                    product.setProductQty(Integer.parseInt(edtProQty.getText().toString()));
                    product.setProductPrice(Double.parseDouble(edtProPrice.getText().toString()));
                    product.setInformation(edtProInfo.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void processData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("edit");
        Product product = (Product) bundle.getSerializable("product");
        tvProId.setText(product.getProductId());
        edtProName.setText(product.getProductName());
        edtProQty.setText(String.valueOf(product.getProductQty()));
        edtProPrice.setText(String.valueOf((int) product.getProductPrice()));
        edtProInfo.setText(product.getInformation());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        init();
        registerEvent();
        processData();
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
