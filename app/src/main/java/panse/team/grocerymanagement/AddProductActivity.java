package panse.team.grocerymanagement;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import panse.team.grocerymanagement.entities.Product;

public class AddProductActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener, FrameActi {
    private EditText edtProId, edtProName, edtProQty, edtProPrice, edtProInfo;
    private ImageButton imgBtnAdd, imgBtnBack;
    private BarcodeReader barcodeReader;

    @Override
    public void init() {
        edtProId = findViewById(R.id.edtProdId);
        edtProName = findViewById(R.id.edtProName);
        edtProQty = findViewById(R.id.edtProQty);
        edtProPrice = findViewById(R.id.edtProPrice);
        edtProInfo = findViewById(R.id.edtProInfo);
        imgBtnAdd = findViewById(R.id.imgBtnAdd);
        imgBtnBack = findViewById(R.id.imgBtnBack);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.fragmentScan);
        barcodeReader.setBeepSoundFile("quack.mp3");
    }

    @Override
    public void registerEvent() {
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtProId.getText().toString().equals("")) {
                    Toast.makeText(AddProductActivity.this, "Vui lòng nhập mã sp hoặc scan barcode", Toast.LENGTH_SHORT).show();
                    hideKeyboard(AddProductActivity.this);
                } else if (edtProName.getText().toString().equals("")) {
                    Toast.makeText(AddProductActivity.this, "Vui lòng tên sản phẩm", Toast.LENGTH_SHORT).show();
                    hideKeyboard(AddProductActivity.this);
                } else if (edtProQty.getText().toString().equals("")) {
                    Toast.makeText(AddProductActivity.this, "Vui lòng số lượng sản phẩm", Toast.LENGTH_SHORT).show();
                    hideKeyboard(AddProductActivity.this);
                } else if (edtProPrice.getText().toString().equals("")) {
                    Toast.makeText(AddProductActivity.this, "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
                    hideKeyboard(AddProductActivity.this);
                } else {
                    Product product = new Product();
                    product.setProductId(edtProId.getText().toString());
                    product.setProductName(edtProName.getText().toString());
                    product.setProductQty(Integer.parseInt(edtProQty.getText().toString()));
                    product.setProductPrice(Double.parseDouble(edtProPrice.getText().toString()));
                    product.setInformation(edtProInfo.getText().toString());
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product", product);
                    intent.putExtra("add", bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
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

    @Override
    public void processData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        init();
        registerEvent();
    }

    @Override
    public void onScanned(final Barcode barcode) {
        barcodeReader.playBeep();
        barcodeReader.pauseScanning();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                edtProId.setText(barcode.displayValue);
                barcodeReader.resumeScanning();
            }
        });
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(this, "Vui lòng cấp quyền Camera", Toast.LENGTH_SHORT).show();
        finish();
    }
}
