package panse.team.grocerymanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import panse.team.grocerymanagement.entities.Product;

public class ProductManager {
    MySQLHelper helper;
    SQLiteDatabase db;

    private final String TAG = "ProductManager";
    private static final String TABLE_Product = "dbProduct";
    private static final String PROID = "proID";
    private static final String PRONAME = "proName";
    private static final String PRICE = "price";
    private static final String QTY = "qTy";
    private static final String INFO = "info";

    public ProductManager(Context context) {
        helper = new MySQLHelper(context);
    }

    public long createProduct(Product p) {
        long n = 0;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROID, p.getProductId());
        values.put(PRONAME, p.getProductName());
        values.put(PRICE, p.getProductPrice());
        values.put(QTY, p.getProductQty());
        values.put(INFO, p.getInformation());

        n = db.insert(TABLE_Product, null, values);
        db.close();
        Log.d(TAG, "add Product Successfuly");
        return n;
    }

    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> listProduct = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_Product;

        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Product p = new Product();
                p.setProductId(cursor.getString(0));
                p.setProductName(cursor.getString(1));
                p.setProductPrice(cursor.getDouble(2));
                p.setProductQty(cursor.getInt(3));
                p.setInformation(cursor.getString(4));
                listProduct.add(p);

            } while (cursor.moveToNext());
        }
        db.close();
        return listProduct;
    }

    public Product getProductByID(String proID) {

        String selectQuery = "SELECT * FROM " + TABLE_Product + " WHERE proID = '"+proID+"'";
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor != null)
            cursor.moveToFirst();
        Product prod = new Product(cursor.getString(0), cursor.getString(1), cursor.getInt(3), cursor.getDouble(2), cursor.getString(4));
        cursor.close();
        return prod;
    }

    public Product getProductByName(String proN) {

        String selectQuery = "SELECT * FROM " + TABLE_Product + " WHERE proName = '"+proN+"'";
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor != null)
            cursor.moveToFirst();
        Product prod = new Product(cursor.getString(0), cursor.getString(1), cursor.getInt(3), cursor.getDouble(2), cursor.getString(4));

        cursor.close();
        return prod;

    }


    public long updateProduct(String proId, Product newPro) {
        long n;
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRONAME, newPro.getProductName());
        contentValues.put(PRICE, newPro.getProductPrice());
        contentValues.put(QTY, newPro.getProductQty());
        contentValues.put(INFO, newPro.getInformation());
        n = db.update(TABLE_Product, contentValues, PROID + "=?", new String[]{String.valueOf(proId)});
        return n;
    }

    public long deleteProductById(String  proId){
        long n;
        db = helper.getWritableDatabase();
        n = db.delete(TABLE_Product,PROID+"=?",new String[] {String.valueOf(proId)});
        return n;
    }

    public ArrayList<Product> alarmProduct(int soluong){
        ArrayList<Product> lstPro = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_Product + " Where qTy < '"+soluong+"' ";

        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product p = new Product();
                p.setProductId(cursor.getString(0));
                p.setProductName(cursor.getString(1));
                p.setProductPrice(cursor.getDouble(2));
                p.setProductQty(cursor.getInt(3));
                p.setInformation(cursor.getString(4));
                lstPro.add(p);

            } while (cursor.moveToNext());
        }
        db.close();
        return lstPro;
    }

    // trả về List Product chỉ có 4 gtrị = "ID","NAME","QtySale","TotalSale"
    public ArrayList<Product> sortProductBySale(){
        ArrayList<Product> lstPro = new ArrayList<>();
        // SELECT c.c3, p.c2, SUM(c.c4), SUM(c.c5) FROM 'pro' p join 'ct' c on p.c1 = c.c3 group by c.c3, p.c2 order by c.c5;
        String selectQuery = "SELECT dt.proID, p.proName,SUM(dt.qTy), SUM(dt.totalProduct) FROM dbOrderDetail dt join dbProduct p on dt.proID = p.proID group by dt.proID, p.proName order by dt.totalProduct";

        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product p = new Product();
                p.setProductId(cursor.getString(0));
                p.setProductName(cursor.getString(1));
                p.setProductQty(cursor.getInt(2));
                p.setProductPrice(cursor.getDouble(3));
                lstPro.add(p);
            } while (cursor.moveToNext());
        }
        db.close();
        return lstPro;
    }

}
