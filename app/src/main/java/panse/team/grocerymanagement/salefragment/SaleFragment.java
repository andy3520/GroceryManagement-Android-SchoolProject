package panse.team.grocerymanagement.salefragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.androidhive.barcode.BarcodeReader;
import panse.team.grocerymanagement.FrameFuction;
import panse.team.grocerymanagement.R;

import static android.support.constraint.Constraints.TAG;

public class SaleFragment extends ListFragment implements FrameFuction,BarcodeReader.BarcodeReaderListener {
    private BarcodeReader barcodeReader;
    private ArrayList<String> code;
    private ArrayAdapter adapter;
    @Override
    public void init(View view) {

    }

    @Override
    public void registerEvent() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
        code = new ArrayList<>();
        adapter = new ArrayAdapter(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, code);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        barcodeReader = (BarcodeReader) getChildFragmentManager().findFragmentById(R.id.fragmentReader);
        barcodeReader.setListener(this);
        setListAdapter(adapter);
        return view;
    }


    @Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        barcodeReader.playBeep();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                code.add(barcode.displayValue);
                adapter.notifyDataSetChanged();
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

    }

}
