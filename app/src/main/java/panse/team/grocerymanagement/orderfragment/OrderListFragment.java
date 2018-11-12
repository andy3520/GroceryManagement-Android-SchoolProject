package panse.team.grocerymanagement.orderfragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import panse.team.grocerymanagement.FrameFuction;
import panse.team.grocerymanagement.R;
import panse.team.grocerymanagement.entities.Order;
import panse.team.grocerymanagement.salefragment.SaleFragment;

public class OrderListFragment extends ListFragment implements View.OnClickListener, FrameFuction {
    private SearchView svOrderList;
    private ImageButton imgBtnAdd;
    private ArrayList<Order> orders;
    private TextView tvOrderIdHeader, tvOrderCusNameHeader, tvOrderDateHeader, tvOrderPriceHeader;
    private ListView list;
    private OrderListAdapter adapter;

    // Khởi tạo các đối tượng theo id
    @Override
    public void init(View view) {
        list = view.findViewById(android.R.id.list);
        tvOrderIdHeader = view.findViewById(R.id.tvOrderIdHeader);
        tvOrderCusNameHeader = view.findViewById(R.id.tvCustomerNameHeader);
        tvOrderDateHeader = view.findViewById(R.id.tvOrderDateHeader);
        tvOrderPriceHeader = view.findViewById(R.id.tvTotalOrderPriceHeader);
        imgBtnAdd = view.findViewById(R.id.imgBtnAdd);
    }
    // ^^^

    // Đăng kí sự kiện
    @Override
    public void registerEvent() {
        tvOrderIdHeader.setOnClickListener(this);
        tvOrderDateHeader.setOnClickListener(this);
        tvOrderPriceHeader.setOnClickListener(this);
        tvOrderCusNameHeader.setOnClickListener(this);
        imgBtnAdd.setOnClickListener(this);
    }
    // ^^^

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View layout của fragment, khởi tạo và đk sự kiện
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        init(view);
        registerEvent();
        // ^^^

        // Đăng kí context menu
        registerForContextMenu(list);
        // ^^^


        // Fake data để test
        orders = new ArrayList<>();
        orders.add(new Order("A1EASD", "Nguyễn Thị Nghiêng", new GregorianCalendar(2017, Calendar.JANUARY, 3).getTime(), 10000));
        orders.add(new Order("A6", "Nguyễn Hiếu Đức Ân", new GregorianCalendar(2017, Calendar.APRIL, 1).getTime(), 150000));
        orders.add(new Order("C", "Trần Trung Nam", new GregorianCalendar(2016, Calendar.JULY, 15).getTime(), 5000));
        orders.add(new Order("PP", "Phan Minh Phụng", new GregorianCalendar(2018, Calendar.AUGUST, 3).getTime(), 1000000));
        orders.add(new Order("C1", "Nguyễn Hữu Sơn", new GregorianCalendar(2013, Calendar.JANUARY, 3).getTime(), 6000000));
        orders.add(new Order("B6", "Huỳnh Tấn Phát", new GregorianCalendar(2017, Calendar.SEPTEMBER, 11).getTime(), 300000));
        orders.add(new Order("HP", "Nguyễn Thị Ai", new GregorianCalendar(2017, Calendar.JANUARY, 3).getTime(), 900000));
        orders.add(new Order("A1", "Nguyễn Thị Nghiêng", new GregorianCalendar(2012, Calendar.NOVEMBER, 29).getTime(), 150000));
        orders.add(new Order("A6", "Nguyễn Văn Bành", new GregorianCalendar(2017, Calendar.DECEMBER, 30).getTime(), 2000));
        orders.add(new Order("C", "Trần Trang", new GregorianCalendar(2013, Calendar.JANUARY, 20).getTime(), 300));
        orders.add(new Order("PP", "Phan Minh Hồ", new GregorianCalendar(2017, Calendar.OCTOBER, 14).getTime(), 8000));
        orders.add(new Order("C1", "Nguyễn Lâm Nguy", new GregorianCalendar(2017, Calendar.JANUARY, 30).getTime(), 56000));
        orders.add(new Order("B6", "Huỳnh Văn Đạt", new GregorianCalendar(2011, Calendar.JANUARY, 3).getTime(), 800500));
        orders.add(new Order("HP", "Nguyễn Thị Tẹt", new Date(), 60000));
        // ^^^

        // Mặc định sort bằng orderId ASC
        Collections.sort(orders, Order.ASC_orderIdComparator);
        tvOrderIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        // ^^^

        // Array Adapter Customize của ListView và set ListAdapter cho fragment
        adapter = new OrderListAdapter(getActivity(), R.layout.custom_order_listview, orders);
        setListAdapter(adapter);
        // ^^^

        return view;
    }

    // Định nghĩa ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == android.R.id.list) {
            MenuInflater inflater = getActivity().getMenuInflater();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Order order = orders.get(info.position);
            //setTitle cho menu
            menu.setHeaderTitle(order.getOrderId() + " " + order.getCustomerName());
            inflater.inflate(R.menu.order_list_context_menu, menu);
        }
    }
    // ^^^


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                break;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa hóa đơn");
                builder.setMessage("Thao tác xóa sẽ không thể hoàn tác\nBạn chắn chắn muốn xóa hóa đơn?");
                builder.setPositiveButton("Xóa", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.show();
                break;
            case R.id.add:
                callSaleFragment();
                BottomNavigationView navigation = getActivity().findViewById(R.id.navigation);
                navigation.setSelectedItemId(R.id.nav_banhang);
                break;
            case R.id.detail:
                break;
        }
        return super.onContextItemSelected(item);
    }

    //biến quản lý sort
    int click = 0;
    String term = "";

    // Các sự kiện OnClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Sự kiện sort
            case R.id.tvOrderIdHeader:
                sortOrderId();
                break;
            case R.id.tvCustomerNameHeader:
                sortCusName();
                break;
            case R.id.tvOrderDateHeader:
                sortOrderDate();
                break;
            case R.id.tvTotalOrderPriceHeader:
                sortTotalPrice();
                break;
            case R.id.imgBtnAdd:
                callSaleFragment();
                BottomNavigationView navigation = getActivity().findViewById(R.id.navigation);
                navigation.setSelectedItemId(R.id.nav_banhang);
                break;
            // ^^^

        }
    }

    // Các hàm sắp xếp
    public void sortOrderId() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "a";
            Collections.sort(orders, Order.ASC_orderIdComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("a")) {
            click = 0;
            term = "";
            Collections.sort(orders, Order.DES_orderIdComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        } else {
            click = 1;
            term = "a";
            Collections.sort(orders, Order.ASC_orderIdComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        }
    }

    public void sortCusName() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "b";
            Collections.sort(orders, Order.ASC_orderCusFirstNameComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderCusNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("b")) {
            click = 0;
            term = "";
            Collections.sort(orders, Order.DES_orderCusFirstNameComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderCusNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        } else {
            click = 1;
            term = "b";
            Collections.sort(orders, Order.ASC_orderCusFirstNameComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderCusNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        }
    }

    public void sortOrderDate() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "c";
            Collections.sort(orders, Order.ASC_orderDateComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderDateHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("c")) {
            click = 0;
            term = "";
            Collections.sort(orders, Order.DES_orderDateComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderDateHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        } else {
            click = 1;
            term = "c";
            Collections.sort(orders, Order.ASC_orderDateComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderDateHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        }
    }

    public void sortTotalPrice() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "d";
            Collections.sort(orders, Order.ASC_orderTotalPriceComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("d")) {
            click = 0;
            term = "";
            Collections.sort(orders, Order.DES_orderTotalPriceComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        } else {
            click = 1;
            term = "d";
            Collections.sort(orders, Order.ASC_orderTotalPriceComparator);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvOrderPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        }
    }

    // Xóa mũi tên sort
    public void resetArrow() {
        tvOrderIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvOrderPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvOrderDateHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvOrderCusNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }
    // ^^^

    public void callSaleFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        SaleFragment orderListFragment = new SaleFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMainContainer, orderListFragment);
        fragmentTransaction.commit();
    }

}
