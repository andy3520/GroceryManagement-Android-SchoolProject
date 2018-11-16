package panse.team.grocerymanagement.statfragment;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import panse.team.grocerymanagement.FrameFuction;
import panse.team.grocerymanagement.R;
import panse.team.grocerymanagement.dao.OrderManager;
import panse.team.grocerymanagement.dao.ProductManager;
import panse.team.grocerymanagement.entities.Product;

public class StatsFragment extends ListFragment implements FrameFuction {
    private TextView tvRevenue;
    private EditText edtStartDate,edtEndDate;
    private ImageButton imtBtnFind;
    private OrderManager orderManager;
    private ProductManager productManager;
    private ArrayList<Product> products;
    private StatsProductAdapter adapter;

    @Override
    public void init(View view) {
        tvRevenue = view.findViewById(R.id.tvRevenue);
        edtEndDate = view.findViewById(R.id.edtEndDate);
        edtStartDate = view.findViewById(R.id.edtStartDate);
        imtBtnFind = view.findViewById(R.id.imgBtnFind);
        orderManager = new OrderManager(getActivity());
        productManager = new ProductManager(getActivity());
    }

    @Override
    public void registerEvent() {
        final Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.MONTH,-1);
        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, month);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                edtStartDate.setText(sdf.format(startCalendar.getTime()));
            }
        };

        final Calendar endCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, month);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                edtEndDate.setText(sdf.format(endCalendar.getTime()));
            }
        };

        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(),startDate,startCalendar.get(Calendar.YEAR),startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(),endDate,endCalendar.get(Calendar.YEAR),endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imtBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = (int)orderManager.totalSaleByTime(edtStartDate.getText().toString(), edtEndDate.getText().toString());
                tvRevenue.setText(String.valueOf(total));
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        init(view);
        registerEvent();
        defaultStats();
        return view;
    }

    public void defaultStats() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String endDate = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH,-1);
        String startDate = simpleDateFormat.format(calendar.getTime());
        edtStartDate.setText(startDate);
        edtEndDate.setText(endDate);
        int total = (int)orderManager.totalSaleByTime(startDate, endDate);
        tvRevenue.setText(String.valueOf(total));
        products = productManager.sortProductBySale(startDate, endDate);
        adapter = new StatsProductAdapter(getActivity(), R.layout.custom_stats_product_list, products);
        setListAdapter(adapter);
    }
}
