package panse.team.grocerymanagement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import panse.team.grocerymanagement.customadapteractivity.DetailOrderListAdapter;
import panse.team.grocerymanagement.customadapteractivity.EditOrderAdapter;
import panse.team.grocerymanagement.dao.OrderDetailManager;
import panse.team.grocerymanagement.dao.ProductManager;
import panse.team.grocerymanagement.entities.Order;
import panse.team.grocerymanagement.entities.OrderDetails;
import panse.team.grocerymanagement.entities.Product;


public class EditOrdersActivity extends AppCompatActivity implements FrameActi {
    private EditText edtCustomerName, edtOrderDate;
    private TextView tvOrderId;
    public static TextView tvTotalOrderPrice;
    private ImageButton imgBtnBack, imgBtnSave;
    public static ArrayList<Product> deleteDetail = new ArrayList<>();
    public static ArrayList<Product> editDetail = new ArrayList<>();
    private ArrayList<Product> products;
    private ArrayList<OrderDetails> orderDetails;
    private ProductManager productManager;
    private OrderDetailManager orderDetailManager;
    private ListView lvOrderDetails;
    private EditOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_orders);
        init();
        registerEvent();
        processData();
        adapter = new EditOrderAdapter(this, R.layout.custom_detail_order_item_orderdetail, products);
        lvOrderDetails.setAdapter(adapter);
    }

    @Override
    public void init() {
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtOrderDate = findViewById(R.id.edtOrderDate);
        tvOrderId = findViewById(R.id.tvOrderId);
        tvTotalOrderPrice = findViewById(R.id.tvTotalOrderPrice);
        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnSave = findViewById(R.id.imgBtnSave);
        lvOrderDetails = findViewById(R.id.lvListOrderDetail);
        productManager = new ProductManager(this);
        orderDetailManager = new OrderDetailManager(this);
    }

    @Override
    public void registerEvent() {
        final Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("edit");
        final Order order = (Order) bundle.getSerializable("order");
        tvOrderId.setText(order.getOrderId());
        tvTotalOrderPrice.setText((int) order.getTotalOrderPrice() + "");
        edtCustomerName.setText(order.getCustomerName());
        edtOrderDate.setText(order.getOrderDate());

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                edtOrderDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCustomerName.getText().toString().equals("")) {
                    Toast.makeText(EditOrdersActivity.this, "Vui lòng nhập tên khách hàng", Toast.LENGTH_SHORT).show();
                    hideKeyboard(EditOrdersActivity.this);
                } else if (edtOrderDate.getText().toString().equals("")) {
                    Toast.makeText(EditOrdersActivity.this, "Vui lòng nhập ngày mua hàng", Toast.LENGTH_SHORT).show();
                    hideKeyboard(EditOrdersActivity.this);
                } else {
                    order.setCustomerName(edtCustomerName.getText().toString());
                    order.setOrderDate(dateToString(myCalendar.getTime()));
                    order.setTotalOrderPrice(Double.parseDouble(tvTotalOrderPrice.getText().toString()));
                    Intent intent1 = new Intent();
                    intent1.putExtra("edit", bundle);
                    setResult(RESULT_OK, intent1);
                    finish();
                }
            }
        });

        edtOrderDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditOrdersActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void processData() {
        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("edit");
        final Order order = (Order) bundle.getSerializable("order");

        products = new ArrayList<>();
        orderDetails = new ArrayList<>();
        orderDetails = orderDetailManager.getAllOrderDetailByOrdID(order.getOrderId());
        for (OrderDetails odt : orderDetails) {
            Product product = productManager.getProductByID(odt.getProductId());
            product.setProductQty(odt.getOrderDetailQty());
            products.add(product);
        }
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


    public String dateToString(Date date) {
        //Muốn xuất Ngày/Tháng/Năm , ví dụ 12/04/2013
        String strDateFormat = "dd/MM/yyyy";
        //tạo đối tượng SimpleDateFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        //gọi hàm format để lấy chuỗi ngày tháng năm đúng theo yêu cầu
        //System.out.println("Ngày hôm nay : " + sdf.format(date));
        return sdf.format(date);
    }
}

