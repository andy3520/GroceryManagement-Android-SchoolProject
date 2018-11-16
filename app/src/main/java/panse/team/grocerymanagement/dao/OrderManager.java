package panse.team.grocerymanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import panse.team.grocerymanagement.entities.Order;
import panse.team.grocerymanagement.entities.OrderDetails;
import panse.team.grocerymanagement.entities.Product;

public class OrderManager {
    MySQLHelper helper;
    SQLiteDatabase db;

    private final String TAG = "OrdertManager";
    private static final String TABLE_Order = "dbOrder";
    private static final String ORDID = "ordID";
    private static final String USERNAME = "userName";
    private static final String TOTALPRICE = "totalPrice";
    private static final String DATEORD = "dateOrd";

    public OrderManager(Context context) {
        helper = new MySQLHelper(context);
    }

    public long createOrder(Order o, ArrayList<Product> lstpro) {
        long n, m = 0;
        String maOrder = getIDAdd("OD");
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDID, maOrder);
        values.put(USERNAME, o.getCustomerName());
        values.put(TOTALPRICE, o.getTotalOrderPrice());
        values.put(DATEORD, getNgayHienTai());
        n = db.insert(TABLE_Order, null, values);
        if (n > 0) {
            for (int i = 0; i < lstpro.size(); i++) {
                Log.d("dbs", "ODT " + i + " - Size" + lstpro.size());
                m = OrderDetailManager.createOrderDetail(new OrderDetails(getIDAdd("ODT") + i, maOrder, lstpro.get(i).getProductId(), lstpro.get(i).getProductQty(), lstpro.get(i).getProductPrice() * lstpro.get(i).getProductQty(), getNgayHienTai()));
            }
        }
        db.close();
        Log.d(TAG, "add Order Successfuly");
        return m;
    }

    public ArrayList<Order> getAllOrder() {
        ArrayList<Order> listOrder = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_Order;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Order o = new Order();
                o.setOrderId(cursor.getString(0));
                o.setCustomerName(cursor.getString(1));
                o.setTotalOrderPrice(cursor.getDouble(2));
                o.setOrderDate(cursor.getString(3));
                listOrder.add(o);
            } while (cursor.moveToNext());
        }
        db.close();
        return listOrder;
    }

    public ArrayList<Order> getAllOrderByCustomerName(String cusName) {
        ArrayList<Order> listOrder = new ArrayList<>();
        db = helper.getReadableDatabase();
        String query = "select * from " + TABLE_Order + " where " + USERNAME + " like '%" + cusName + "%'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Order o = new Order();
                o.setOrderId(cursor.getString(0));
                o.setCustomerName(cursor.getString(1));
                o.setTotalOrderPrice(cursor.getDouble(2));
                o.setOrderDate(cursor.getString(3));
                listOrder.add(o);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOrder;
    }


    public Order getOrderById(String ordID) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_Order, new String[]{ORDID,
                        USERNAME, TOTALPRICE, DATEORD}, ORDID + "=?",
                new String[]{String.valueOf(ordID)}, null, null, null, null);
//        Cursor cursor;
//        cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME +" WHERE id = '1'",null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Order o = new Order(cursor.getString(0), cursor.getString(1), cursor.getString(3), cursor.getDouble(2));
        cursor.close();
        db.close();
        return o;
    }

    public long updateOrder(String ordId, Order newOrd) {
        long n;
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, newOrd.getCustomerName());
        contentValues.put(TOTALPRICE, newOrd.getTotalOrderPrice());
        contentValues.put(DATEORD, newOrd.getOrderDate());
        n = db.update(TABLE_Order, contentValues, ORDID + "=?", new String[]{String.valueOf(ordId)});
        return n;
    }


    public long deleteOrderById(String ordId) {
        long n;
        db = helper.getWritableDatabase();
        n = db.delete(TABLE_Order, ORDID + "=?", new String[]{ordId});
        return n;
    }

    public double totalSaleByTime(String startDate, String endDate) {
        double sale = 0;
        db = helper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT SUM(totalPrice) FROM " + TABLE_Order +
                " WHERE julianday(substr(dateOrd,7)||'-'||substr(dateOrd,4,2)||'-'||substr(dateOrd,1,2))  " +
                "between julianday(substr('" + startDate + "',7)||'-'||substr('" + startDate + "',4,2)||'-'||substr('" + startDate + "',1,2)) " +
                "and julianday(substr('" + endDate + "',7)||'-'||substr('" + endDate + "',4,2)||'-'||substr('" + endDate + "',1,2)) ", null);
        if (cursor.moveToFirst()) {
            sale = cursor.getDouble(0);
        }
        db.close();
        return sale;
    }


    public static String getNgayHienTai() {
        //Tạo đối tượng date sẽ lấy date hiện tại
        Date date = new Date();

        //Muốn xuất Ngày/Tháng/Năm , ví dụ 12/04/2013
        String strDateFormat = "dd/MM/yyyy";
        //tạo đối tượng SimpleDateFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        //gọi hàm format để lấy chuỗi ngày tháng năm đúng theo yêu cầu
        //System.out.println("Ngày hôm nay : " + sdf.format(date));
        return sdf.format(date);
    }

    public static String getIDAdd(String ma) {
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat timeformat = new SimpleDateFormat("hhmmssddMMyyyy");
        String s = timeformat.format(today.getTime());
        return ma + s;
    }

    public Date parseDate(String Sdate) {
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            date = df.parse(Sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
