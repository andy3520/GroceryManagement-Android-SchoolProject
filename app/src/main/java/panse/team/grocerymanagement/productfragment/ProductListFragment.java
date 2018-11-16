package panse.team.grocerymanagement.productfragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import panse.team.grocerymanagement.AddProductActivity;
import panse.team.grocerymanagement.DetailProductActivity;
import panse.team.grocerymanagement.EditProductActivity;
import panse.team.grocerymanagement.FrameFuction;
import panse.team.grocerymanagement.R;

import panse.team.grocerymanagement.dao.OrderDetailManager;
import panse.team.grocerymanagement.dao.ProductManager;
import panse.team.grocerymanagement.entities.Product;


public class ProductListFragment extends ListFragment implements View.OnClickListener, FrameFuction {
    private ArrayList<Product> products;
    private SearchView svProduct;
    private ListView list;
    private ImageButton imgBtnAdd;
    private ProductListAdapter adapter;
    private TextView tvProductIdHeader, tvProductNameHeader, tvProductQtyHeader, tvProductPriceHeader, tvProductInfoHeader;
    private static final int EDIT = 99;
    private static final int ADD = 88;
    private ProductManager productManager;
    private OrderDetailManager orderDetailManager;

    @Override
    public void init(View view) {
        list = view.findViewById(android.R.id.list);
        tvProductIdHeader = view.findViewById(R.id.tvProductIdHeader);
        tvProductNameHeader = view.findViewById(R.id.tvProductNameHeader);
        tvProductQtyHeader = view.findViewById(R.id.tvProductQtyHeader);
        tvProductPriceHeader = view.findViewById(R.id.tvProductPriceHeader);
        tvProductInfoHeader = view.findViewById(R.id.tvProductInfoHeader);
        svProduct = view.findViewById(R.id.svProductList);
        imgBtnAdd = view.findViewById(R.id.imgBtnAdd);
    }

    @Override
    public void registerEvent() {
        tvProductIdHeader.setOnClickListener(this);
        tvProductNameHeader.setOnClickListener(this);
        tvProductQtyHeader.setOnClickListener(this);
        tvProductPriceHeader.setOnClickListener(this);
        tvProductInfoHeader.setOnClickListener(this);
        imgBtnAdd.setOnClickListener(this);
        svProduct.setQueryHint("Nhập tên sản phẩm");
        svProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Product> queryOrder = productManager.getProductByName(query);
                ProductListAdapter newAdapter = new ProductListAdapter(getActivity(), R.layout.custom_product_listview, queryOrder);
                setListAdapter(newAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Product> queryOrder = productManager.getProductByName(newText);
                ProductListAdapter newAdapter = new ProductListAdapter(getActivity(), R.layout.custom_product_listview, queryOrder);
                setListAdapter(newAdapter);
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        // Khởi tạo view và sự kiện
        init(view);
        registerEvent();

        // Đăng kí contextmenu cho list
        registerForContextMenu(list);

        // SQLite Product manager
        productManager = new ProductManager(getActivity());
        orderDetailManager = new OrderDetailManager(getActivity());
        // Lấy danh sách sản phẩm trong database
        products = productManager.getAllProduct();

        // Mặc định sort bằng proId ASC
        Collections.sort(products, Product.ASC_productId);
        tvProductIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);

        // Array Adapter Customize của ListView và set ListAdapter cho fragment
        adapter = new ProductListAdapter(Objects.requireNonNull(getActivity()), R.layout.custom_product_listview, products);
        setListAdapter(adapter);

        return view;
    }

    // Đĩnh nghĩa context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == android.R.id.list) {
            MenuInflater inflater = Objects.requireNonNull(getActivity()).getMenuInflater();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Product product = products.get(info.position);

            //setTitle cho menu
            menu.setHeaderTitle(product.getProductName());
            inflater.inflate(R.menu.product_list_context_menu, menu);
        }
    }


    // Sự kiện của item trong context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            // Cảnh báo xóa trong dialog
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Thao tác xóa sẽ không thể hoàn tác\nBạn có muốn xóa sản phẩm?");
                builder.setNegativeButton("Hủy", null);
                builder.setPositiveButton("Có", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (orderDetailManager.getAllOrderDetailByproID(products.get(menuInfo.position).getProductId()).size() <= 0) {
                            if (productManager.deleteProductById(products.get(menuInfo.position).getProductId()) > 0) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        products.remove(menuInfo.position);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                            } 
                        }else{
                            Toast.makeText(getActivity(), "Sản phẩm tồn tại trong hóa đơn không thể xóa. Vui lòng xóa hóa đơn trước", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.show();
                return true;

            // Xem chi tiết
            case R.id.detail:
                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                Bundle bundle = new Bundle();
                Product product = products.get(menuInfo.position);
                bundle.putSerializable("product", product);
                intent.putExtra("detail", bundle);
                startActivity(intent);
                return true;

            // Cập nhật sản phẩm
            case R.id.edit:
                Intent intent1 = new Intent(getActivity(), EditProductActivity.class);
                Bundle bundle1 = new Bundle();
                Product product1 = products.get(menuInfo.position);
                bundle1.putInt("pos", menuInfo.position);
                bundle1.putSerializable("product", product1);
                intent1.putExtra("edit", bundle1);
                startActivityForResult(intent1, EDIT);
                return true;

            // Tạo sản phẩm
            case R.id.add:
                callCreateActi();
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
                Product product = (Product) bundle.getSerializable("product");
                if (productManager.updateProduct(product.getProductId(), product) > 0) {
                    Product productUI = products.get(bundle.getInt("pos"));
                    productUI.setProductName(product.getProductName());
                    productUI.setProductPrice(product.getProductPrice());
                    productUI.setProductQty(product.getProductQty());
                    productUI.setInformation(product.getInformation());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Hủy chỉnh sửa sản phẩm", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ADD) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getBundleExtra("add");
                Product product = (Product) bundle.getSerializable("product");
                if (productManager.createProduct(product) > 0) {
                    products.add(product);
                    adapter.notifyDataSetChanged();
                    getListView().post(new Runnable() {
                        @Override
                        public void run() {
                            getListView().setSelection(adapter.getCount() - 1);
                        }
                    });
                    Toast.makeText(getActivity(), "Tạo thành công", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Hủy tạo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Click item list để xem chi tiết
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        Bundle bundle = new Bundle();
        Product product = products.get(position);
        bundle.putSerializable("product", product);
        intent.putExtra("detail", bundle);
        Objects.requireNonNull(getActivity()).startActivity(intent);
    }

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

            // Tạo product
            case R.id.imgBtnAdd:
                callCreateActi();
                break;
        }
    }

    // Tạo sản phẩm
    private void callCreateActi() {
        Intent intent = new Intent(getActivity(), AddProductActivity.class);
        startActivityForResult(intent, ADD);
    }

    // biến quản lý sort
    int click = 0;
    String term = "";

    // Sắp xếp theo mã sp
    public void sortProductId() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "a";
            Collections.sort(products, Product.ASC_productId);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("a")) {
            click = 0;
            term = "";
            Collections.sort(products, Product.DES_productId);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        } else {
            click = 1;
            term = "a";
            Collections.sort(products, Product.ASC_productId);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        }
    }

    // Sắp xếp theo tên sp
    public void sortProductName() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "b";
            Collections.sort(products, Product.ASC_productName);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("b")) {
            click = 0;
            term = "";
            Collections.sort(products, Product.DES_productName);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        } else {
            click = 1;
            term = "b";
            Collections.sort(products, Product.ASC_productName);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        }
    }

    // Sắp xếp sp theo số lượng tồn
    public void sortProductQty() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "c";
            Collections.sort(products, Product.ASC_productQty);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductQtyHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("c")) {
            click = 0;
            term = "";
            Collections.sort(products, Product.DES_productQty);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductQtyHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        } else {
            click = 1;
            term = "c";
            Collections.sort(products, Product.ASC_productQty);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductQtyHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        }
    }

    //sap sep theo gia
    public void sortProductPrice() {
        if (click == 0 && term.equals("")) {
            click = 1;
            term = "d";
            Collections.sort(products, Product.ASC_productPrice);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        } else if (click == 1 && term.equals("d")) {
            click = 0;
            term = "";
            Collections.sort(products, Product.DES_productPrice);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_up_24, 0);
        } else {
            click = 1;
            term = "d";
            Collections.sort(products, Product.ASC_productPrice);
            adapter.notifyDataSetChanged();
            resetArrow();
            tvProductPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sharp_arrow_drop_down_24, 0);
        }
    }

    // Xóa mũi tên sort
    public void resetArrow() {
        tvProductIdHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvProductNameHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvProductQtyHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvProductPriceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tvProductInfoHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }
}
