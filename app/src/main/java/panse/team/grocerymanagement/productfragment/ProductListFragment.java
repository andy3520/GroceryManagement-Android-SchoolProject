package panse.team.grocerymanagement.productfragment;


import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import panse.team.grocerymanagement.FrameFuction;
import panse.team.grocerymanagement.R;

import panse.team.grocerymanagement.entities.Product;

public class ProductListFragment extends ListFragment implements View.OnClickListener, FrameFuction {
    private ArrayList<Product> products;
    private ListView list;
    private ProductListAdapter adapter;
    private TextView tvProductIdHeader, tvProductNameHeader, tvProductQtyHeader, tvProductPriceHeader, tvProductInforHeader;


    public void init(View view) {
        list = view.findViewById(android.R.id.list);
        tvProductIdHeader = view.findViewById(R.id.tvProductIdHeader);
        tvProductNameHeader = view.findViewById(R.id.tvProductNameHeader);
        tvProductQtyHeader = view.findViewById(R.id.tvProductQtyHeader);
        tvProductPriceHeader = view.findViewById(R.id.tvProductPriceHeader);
        tvProductInforHeader = view.findViewById(R.id.tvProductInforHeader);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        init(view);
        registerEvent();
        registerForContextMenu(list);
        products = new ArrayList<Product>();
        products.add(new Product("12345678", "nuoc suoi", 8, 80000.5, "nuoc uong"));
        products.add(new Product("12345679", "coca", 5, 50000, "nuoc uong"));
        products.add(new Product("123456789", "pesi", 3, 30000, "nuoc uong"));
        products.add(new Product("123456", "o long", 10, 100000, "nuoc uong"));
        Collections.sort(products, Product.ASC_productId);
        tvProductIdHeader.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        adapter = new ProductListAdapter(getActivity(), R.layout.custom_product_listview, products);
        setListAdapter(adapter);
        return view;
    }

    public void registerEvent() {
        tvProductIdHeader.setOnClickListener(this);
        tvProductNameHeader.setOnClickListener(this);
        tvProductQtyHeader.setOnClickListener(this);
        tvProductPriceHeader.setOnClickListener(this);
        tvProductInforHeader.setOnClickListener(this);
    }


    //biến quản lý sort
    int click = 0;
    String term = "";

    // Các sự kiện OnClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Sự kiện sort
            case R.id.tvProductIdHeader:
                sortProductId();
                break;
            case R.id.tvProductNameHeader:
                sortProductName();
                break;
            case R.id.tvProductQtyHeader:
                sortProductQty();
                break;
            case R.id.tvProductPriceHeader:
                sortProductPrice();
                break;
            // ^^^

        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == android.R.id.list) {
            MenuInflater inflater = getActivity().getMenuInflater();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Product product = products.get(info.position);

            //setTitle cho menu
            menu.setHeaderTitle(product.getProductName());
            inflater.inflate(R.menu.product_list_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có muốn xóa sản phẩm ?");
                builder.setNegativeButton("Không", null);
                builder.setPositiveButton("Có", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        products.remove(menuInfo.position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                break;

        }

        return super.onContextItemSelected(item);
    }

    //sap sep theo ma san pham
    public void sortProductId() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "a";
            Collections.sort(products, Product.ASC_productId);
            adapter.notifyDataSetChanged();
            //resetArrow();
            tvProductIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("a")) {
            click = 0;
            term = "";
            Collections.sort(products, Product.DES_productId);
            adapter.notifyDataSetChanged();
            // resetArrow();
            tvProductIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        }
    }
    //sap sep theo ten san pham

    public void sortProductName() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "a";
            Collections.sort(products, Product.ASC_productName);
            adapter.notifyDataSetChanged();
            //resetArrow();
            tvProductNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("a")) {
            click = 0;
            term = "";
            Collections.sort(products, Product.DES_productName);
            adapter.notifyDataSetChanged();
            // resetArrow();
            tvProductNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        }
    }

    //sap sep theo so luong
    public void sortProductQty() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "a";
            Collections.sort(products, Product.ASC_productQty);
            adapter.notifyDataSetChanged();
            //resetArrow();
            tvProductQtyHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("a")) {
            click = 0;
            term = "";
            Collections.sort(products, Product.DES_productQty);
            adapter.notifyDataSetChanged();
            // resetArrow();
            tvProductQtyHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        }
    }

    //sap sep theo gia
    public void sortProductPrice() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "a";
            Collections.sort(products, Product.ASC_productPrice);
            adapter.notifyDataSetChanged();
            //resetArrow();
            tvProductPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("a")) {
            click = 0;
            term = "";
            Collections.sort(products, Product.DES_productPrice);
            adapter.notifyDataSetChanged();
            // resetArrow();
            tvProductPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        }
    }
}
