package panse.team.grocerymanagement.salefragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.androidhive.barcode.BarcodeReader;
import panse.team.grocerymanagement.FrameFuction;
import panse.team.grocerymanagement.R;
import panse.team.grocerymanagement.entities.Product;

import static android.support.constraint.Constraints.TAG;

public class SaleFragment extends ListFragment implements FrameFuction,BarcodeReader.BarcodeReaderListener {
    private BarcodeReader barcodeReader;
    private ArrayList<Product> products;
    private ArrayList<Product> hardDataProduct;
    private SaleListAdapter adapter;
    private EditText edtCusName;
    public static TextView tvTotalPrice;
    @Override
    public void init(View view) {
        barcodeReader = (BarcodeReader) getChildFragmentManager().findFragmentById(R.id.fragmentReader);
        edtCusName = view.findViewById(R.id.edtCusName);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
//        edtCusName.requestFocus();
    }

    @Override
    public void registerEvent() {
        barcodeReader.setListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
        hardDataProduct = new ArrayList<>();
        hardDataProduct.add(new Product("83156986", "Redbull", 1, 15000, "Nước tăng lực"));
        hardDataProduct.add(new Product("83156989", "Trứng gà", 1, 5000, "Trứng gà công nghiệp"));
        hardDataProduct.add(new Product("8315698745", "Mì tôm", 1, 7000, "Mì tôm chua cay"));
        hardDataProduct.add(new Product("8315698787", "Khăn", 1, 20000, "Khăn mặt"));
        products = new ArrayList<>();
        products.add(new Product("83156986", "Redbull", 2, 15000, "Nước tăng lực"));
        products.add(new Product("83156989", "Trứng gà", 1, 5000, "Trứng gà công nghiệp"));
        products.add(new Product("8315698745", "Mì tôm", 1, 7000, "Mì tôm chua cay"));
        adapter = new SaleListAdapter(Objects.requireNonNull(getActivity()), R.layout.custom_product_orderdetail_listview, products);
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(edtCusName, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        init(view);
        registerEvent();
        calculateTotalPrice();
        setListAdapter(adapter);
        return view;
    }


    @Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        barcodeReader.playBeep();
        barcodeReader.pauseScanning();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Product product = getProductById(barcode.displayValue);
                double totalPrice = Double.parseDouble(tvTotalPrice.getText().toString());
                if (product == null) {
                    Toast.makeText(getActivity(), "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    if (isInProductArray(product)) {
                        product = getProductInArray(barcode.displayValue);
                        product.setProductQty(product.getProductQty()+1);
                        adapter.notifyDataSetChanged();
                        totalPrice+= product.getProductPrice();
                        tvTotalPrice.setText(String.valueOf((int) totalPrice));
                        Toast.makeText(getActivity(), "Cập nhật số lượng sản phẩm", Toast.LENGTH_SHORT).show();
                    } else {
                        products.add(product);
                        adapter.notifyDataSetChanged();
                        totalPrice+= product.getProductPrice();
                        tvTotalPrice.setText(String.valueOf((int) totalPrice));
                        Toast.makeText(getActivity(), "Đã thêm sản phầm", Toast.LENGTH_SHORT).show();
                    }
                }
                barcodeReader.resumeScanning();
                getListView().post(new Runnable() {
                    @Override
                    public void run() {
                        getListView().setSelection(adapter.getCount()-1);
                    }
                });
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Vui lòng cấp quyền Camera", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
    }

    public boolean isInProductArray(Product pro) {
        if (products.contains(pro)){
            return true;
        }
        return false;
    }

    public Product getProductById(String id) {
        for (Product p : hardDataProduct) {
            if (p.getProductId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public Product getProductInArray(String id) {
        for (Product p: products){
            if (p.getProductId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void calculateTotalPrice() {
        double total = Double.parseDouble(tvTotalPrice.getText().toString());
        for (Product p : products) {
            total += p.getProductQty()*p.getProductPrice();
        }
        tvTotalPrice.setText(String.valueOf((int)total));
    }
}
