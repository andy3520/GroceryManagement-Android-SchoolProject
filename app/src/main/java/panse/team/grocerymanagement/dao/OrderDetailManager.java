package panse.team.grocerymanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import panse.team.grocerymanagement.entities.OrderDetails;

public class OrderDetailManager {
    static MySQLHelper helper;
    static SQLiteDatabase db;

    private final String TAG = "OrderDetailManager";
    private static final String TABLE_OrderDetail = "dbOrderDetail";
    private static final String ORDDETAILID = "ordDetailID";
    private static final String ORDID = "ordID";
    private static final String PROID = "proID";
    private static final String QTY = "qTy";
    private static final String TOTALPRODUCT = "totalProduct";
    private static final String DATEORD = "dateOrd";

    public OrderDetailManager(Context context) {
        helper = new MySQLHelper(context);
    }

    public static long createOrderDetail(OrderDetails od) {
        long n;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDDETAILID, od.getOrderDetailId());
        values.put(ORDID, od.getOrderId());
        values.put(PROID, od.getProductId());
        values.put(QTY, od.getOrderDetailQty());
        values.put(TOTALPRODUCT, od.getOrderDetailPrice());
        values.put(DATEORD,od.getOrderDetailDateString());

        n = db.insert(TABLE_OrderDetail, null, values);
        db.close();
        return n;
    }

    public ArrayList<OrderDetails> getAllOrderDetail() {
        ArrayList<OrderDetails> listOrderDetail = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_OrderDetail;

        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OrderDetails od = new OrderDetails();
                od.setOrderDetailId(cursor.getString(0));
                od.setOrderId(cursor.getString(1));
                od.setProductId(cursor.getString(2));
                od.setOrderDetailQty(cursor.getInt(3));
                od.setOrderDetailPrice(cursor.getDouble(4));
                od.setOrderDetailDate(cursor.getString(5));
                listOrderDetail.add(od);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOrderDetail;
    }

    public ArrayList<OrderDetails> getAllOrderDetailByOrdID(String ordID){
        ArrayList<OrderDetails> listOrderDetail = new ArrayList<>();

        String query = "select * from dbOrderDetail where ordID = '"+ordID+"'";
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                OrderDetails o = new OrderDetails(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getDouble(4), cursor.getString(5));
                listOrderDetail.add(o);
            } while (cursor.moveToNext());
        }

        db.close();
        return listOrderDetail;
    }

    //get all detial by proid
    public ArrayList<OrderDetails> getAllOrderDetailByproID(String proID){
        ArrayList<OrderDetails> listOrderDetail = new ArrayList<>();

        String query = "select * from dbOrderDetail where proID = '"+proID+"'";
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                OrderDetails o = new OrderDetails(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getDouble(4), cursor.getString(5));
                listOrderDetail.add(o);
            } while (cursor.moveToNext());
        }

        db.close();
        return listOrderDetail;
    }

    public long updateOrderDetail(String ordDtId, OrderDetails newOrdDt) {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QTY, newOrdDt.getOrderDetailQty());
        contentValues.put(TOTALPRODUCT, newOrdDt.getOrderDetailPrice());
        contentValues.put(DATEORD, newOrdDt.getOrderDetailDateString());
        return db.update(TABLE_OrderDetail, contentValues, ORDDETAILID + "=?", new String[]{ordDtId});
    }

    public long deletManyOrderDetail(ArrayList<OrderDetails> lstOrdDt) {
        db = helper.getWritableDatabase();
        long n = 0;
        for (int i = 0; i < lstOrdDt.size(); i++) {
            n = db.delete(TABLE_OrderDetail, ORDDETAILID + "=?", new String[]{String.valueOf(lstOrdDt.get(i).orderDetailId)});
        }
        return n;
    }

    public long deletManyOrderDetailByOrdId(String ordId) {
        db = helper.getWritableDatabase();
        long n = 0;
        n = db.delete(TABLE_OrderDetail, ORDID + "=?", new String[]{String.valueOf(ordId)});
        return n;
    }

    public long deletOneOrderDetail(String OrdDtId) {
        db = helper.getWritableDatabase();
        long n = 0;
        n = db.delete(TABLE_OrderDetail, ORDDETAILID + "=?", new String[]{String.valueOf(OrdDtId)});
        return n;
    }

    public Date parseDate(String Sdate) {
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            date = df.parse(Sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
