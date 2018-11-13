package panse.team.grocerymanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import panse.team.grocerymanagement.entities.Order;


public class EditOrdersContextMenuActivity extends AppCompatActivity implements FrameActi{
private EditText edtCustomerName,edtOrderDate;
private TextView tvOrderId, tvTotalOrderPrice;
private ImageButton imgBtnBack, imgBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_orders_context_menu);
        init();
        registerEvent();
    }

    @Override
    public void init() {
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtOrderDate = findViewById(R.id.edtOrderDate);
        tvOrderId = findViewById(R.id.tvOrderId);
        tvTotalOrderPrice = findViewById(R.id.tvTotalOrderPrice);
        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnSave = findViewById(R.id.imgBtnSave);
    }

    @Override
    public void registerEvent() {
        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("edit");
        final Order order = (Order) bundle.getSerializable("order1");
        tvOrderId.setText(order.getOrderId());
        tvTotalOrderPrice.setText((int)order.getTotalOrderPrice()+"");
        edtCustomerName.setText(order.getCustomerName());
        edtOrderDate.setText(order.getOrderDate());

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US.US);

                edtOrderDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setCustomerName(edtCustomerName.getText().toString());
                order.setOrderDate(myCalendar.getTime());
                Intent intent1 = new Intent();
                intent1.putExtra("edit",bundle);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });

        edtOrderDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditOrdersContextMenuActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void processData() {

    }
}
