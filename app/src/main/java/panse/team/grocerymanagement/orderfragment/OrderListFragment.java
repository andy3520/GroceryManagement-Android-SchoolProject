package panse.team.grocerymanagement.orderfragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import panse.team.grocerymanagement.DetailOrdersActivity;
import panse.team.grocerymanagement.EditOrdersActivity;
import panse.team.grocerymanagement.FrameFuction;
import panse.team.grocerymanagement.R;
import panse.team.grocerymanagement.customadapteractivity.EditOrderAdapter;
import panse.team.grocerymanagement.dao.OrderDetailManager;
import panse.team.grocerymanagement.dao.OrderManager;
import panse.team.grocerymanagement.dao.ProductManager;
import panse.team.grocerymanagement.entities.Order;
import panse.team.grocerymanagement.entities.OrderDetails;
import panse.team.grocerymanagement.entities.Product;
import panse.team.grocerymanagement.salefragment.SaleFragment;

public class OrderListFragment extends ListFragment implements View.OnClickListener, FrameFuction {
    private SearchView svOrderList;
    private ImageButton imgBtnAdd;
    private ArrayList<Order> orders;
    private TextView tvOrderIdHeader, tvOrderCusNameHeader, tvOrderDateHeader, tvOrderPriceHeader;
    private ListView list;
    private OrderListAdapter adapter;
    private static final int EDIT = 2;
    private OrderManager orderManager;
    private OrderDetailManager orderDetailManager;

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

    // Đăng kí sự kiện
    @Override
    public void registerEvent() {
        tvOrderIdHeader.setOnClickListener(this);
        tvOrderDateHeader.setOnClickListener(this);
        tvOrderPriceHeader.setOnClickListener(this);
        tvOrderCusNameHeader.setOnClickListener(this);
        imgBtnAdd.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View layout của fragment, khởi tạo và đk sự kiện
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        init(view);
        registerEvent();

        // Đăng kí context menu
        registerForContextMenu(list);

        // Sqlite manager
        orderManager = new OrderManager(getActivity());
        orderDetailManager = new OrderDetailManager(getActivity());

        // Lấy danh sách order từ CSDL
        orders = orderManager.getAllOrder();

        // Fake data để test
//        orders = new ArrayList<>();
//        orders.add(new Order("A1EASD", "Nguyễn Thị Nghiêng", new GregorianCalendar(2017, Calendar.JANUARY, 3).getTime(), 10000));
//        orders.add(new Order("A6", "Nguyễn Hiếu Đức Ân", new GregorianCalendar(2017, Calendar.APRIL, 1).getTime(), 150000));
//        orders.add(new Order("C", "Trần Trung Nam", new GregorianCalendar(2016, Calendar.JULY, 15).getTime(), 5000));
//        orders.add(new Order("PP", "Phan Minh Phụng", new GregorianCalendar(2018, Calendar.AUGUST, 3).getTime(), 1000000));
//        orders.add(new Order("C1", "Nguyễn Hữu Sơn", new GregorianCalendar(2013, Calendar.JANUARY, 3).getTime(), 6000000));
//        orders.add(new Order("B6", "Huỳnh Tấn Phát", new GregorianCalendar(2017, Calendar.SEPTEMBER, 11).getTime(), 300000));
//        orders.add(new Order("HP", "Nguyễn Thị Ai", new GregorianCalendar(2017, Calendar.JANUARY, 3).getTime(), 900000));
//        orders.add(new Order("A1", "Nguyễn Thị Nghiêng", new GregorianCalendar(2012, Calendar.NOVEMBER, 29).getTime(), 150000));
//        orders.add(new Order("A6", "Nguyễn Văn Bành", new GregorianCalendar(2017, Calendar.DECEMBER, 30).getTime(), 2000));
//        orders.add(new Order("C", "Trần Trang", new GregorianCalendar(2013, Calendar.JANUARY, 20).getTime(), 300));
//        orders.add(new Order("PP", "Phan Minh Hồ", new GregorianCalendar(2017, Calendar.OCTOBER, 14).getTime(), 8000));
//        orders.add(new Order("C1", "Nguyễn Lâm Nguy", new GregorianCalendar(2017, Calendar.JANUARY, 30).getTime(), 56000));
//        orders.add(new Order("B6", "Huỳnh Văn Đạt", new GregorianCalendar(2011, Calendar.JANUARY, 3).getTime(), 800500));
//        orders.add(new Order("HP", "Nguyễn Thị Tẹt", new Date(), 60000));

        // Mặc định sort bằng orderId ASC
        Collections.sort(orders, Order.ASC_orderIdComparator);
        tvOrderIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);

        // Array Adapter Customize của ListView và set ListAdapter cho fragment
        adapter = new OrderListAdapter(getActivity(), R.layout.custom_order_listview, orders);
        setListAdapter(adapter);

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
            menu.setHeaderTitle(order.getOrderId() + "\n" + order.getCustomerName());
            inflater.inflate(R.menu.order_list_context_menu, menu);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailOrdersActivity.class);
        Bundle bundle = new Bundle();
        Order order = orders.get(position);
        bundle.putSerializable("order", order);
        intent.putExtra("detail", bundle);
        getActivity().startActivity(intent);
    }

    // Sự kiện cho các item trong context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            // Xóa sẽ hiện dialog xác nhận
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa hóa đơn");
                builder.setMessage("Thao tác xóa sẽ không thể hoàn tác\nBạn chắn chắn muốn xóa hóa đơn?");
                builder.setNegativeButton("Hủy", null);
                builder.setPositiveButton("Xóa", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Order order = orders.get(menuInfo.position);
                        if (orderManager.deleteOrderById(order.getOrderId()) > 0) {
//                                if (orderDetailManager.deletManyOrderDetailByOrdId(order.getOrderId()) > 0) {
                            Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            orders.remove(menuInfo.position);
                            adapter.notifyDataSetChanged();
//                                } else {
//                                    Toast.makeText(getActivity(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
//                                }
                        } else {
                            Toast.makeText(getActivity(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
                return true;

            // Gọi acti chi tiết
            case R.id.detail:
                Intent intent = new Intent(getActivity(), DetailOrdersActivity.class);
                Bundle bundle = new Bundle();
                Order order = orders.get(menuInfo.position);
                bundle.putSerializable("order", order);
                intent.putExtra("detail", bundle);
                getActivity().startActivity(intent);
                return true;

            // Gọi acti chỉnh sửa
            case R.id.edit:
                Intent intent1 = new Intent(getActivity(), EditOrdersActivity.class);
                Bundle bundle1 = new Bundle();
                Order order1 = orders.get(menuInfo.position);
                bundle1.putSerializable("pos",menuInfo.position);
                bundle1.putSerializable("order", order1);
                intent1.putExtra("edit", bundle1);
                startActivityForResult(intent1, EDIT);
                return true;

            // Chuyển sang tab bán hàng để tạo hóa đơn
            case R.id.add:
                callSaleFragment();
                BottomNavigationView navigation = getActivity().findViewById(R.id.navigation);
                navigation.setSelectedItemId(R.id.nav_banhang);
                return true;
        }
        return false;
    }

    // Đợi kết quả edit
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getBundleExtra("edit");
                Order order = (Order) bundle.getSerializable("order");
                int pos = bundle.getInt("pos");
                Order orderNeedEdit = orders.get(pos);
                orderNeedEdit.setCustomerName(order.getCustomerName());
                orderNeedEdit.setOrderDate(order.getOrderDate());
                orderNeedEdit.setTotalOrderPrice(order.getTotalOrderPrice());
                ArrayList<Product> editList = EditOrdersActivity.editDetail;
                ArrayList<Product> deleteList = EditOrdersActivity.deleteDetail;
                if (deleteList.size() > 0) {
                    for (OrderDetails odt : orderDetailManager.getAllOrderDetailByOrdID(order.getOrderId())) {
                        for (Product p : deleteList) {
                            if (odt.getProductId().equals(p.getProductId())) {
                                orderDetailManager.deletOneOrderDetail(odt.getOrderDetailId());
                            }
                        }
                    }
                    deleteList.clear();
                }
                if (editList.size() > 0) {
                    for (OrderDetails odt : orderDetailManager.getAllOrderDetailByOrdID(order.getOrderId())) {
                        for (Product p : editList) {
                            if (odt.getProductId().equals(p.getProductId())) {
                                odt.setOrderDetailQty(p.getProductQty());
                                if (orderDetailManager.updateOrderDetail(odt.getOrderDetailId(), odt)>0) {
                                }
                            }
                        }
                    }
                    editList.clear();
                }
                Toast.makeText(getActivity(), "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                orderManager.updateOrder(order.getOrderId(), order);
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(getActivity(), "Hủy chỉnh sửa", Toast.LENGTH_SHORT).show();
            EditOrdersActivity.editDetail.clear();
            EditOrdersActivity.deleteDetail.clear();
        }
    }


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
            // Sự kiện nút add
            case R.id.imgBtnAdd:
                callSaleFragment();
                BottomNavigationView navigation = getActivity().findViewById(R.id.navigation);
                navigation.setSelectedItemId(R.id.nav_banhang);
                break;

        }
    }

    // biến quản lý sort
    int click = 0;
    String term = "";

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

    // Gọi fragment bán hàng khi bấm nút add
    public void callSaleFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        SaleFragment orderListFragment = new SaleFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMainContainer, orderListFragment);
        fragmentTransaction.commit();
    }

}
