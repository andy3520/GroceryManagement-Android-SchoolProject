package panse.team.grocerymanagement.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLHelper extends SQLiteOpenHelper {
    private final String TAG = "SQLiteHelper";
    private static final String DATABASE_NAME = "Nhom1Android2";
    private static int VERSION = 1;
    private Context context;

    public static final String createTBProduct = "Create table dbProduct(" +
            "proID varchar(50) primary key," +
            "proName nvarchar(100)," +
            "price double," +
            "qTy int," +
            "info nvarchar(250) )";

    public static final String createTBOrder = "Create table dbOrder(" +
            "ordID varchar(50) primary key," +
            "userName nvarchar(100)," +
            "totalPrice double," +
            "dateOrd varchar(50))";

    public static final String createTBOrderDetail = "Create table dbOrderDetail(" +
            "ordDetailID varchar(50) primary key," +
            "ordID varchar(50) ," +
            "proID varchar(50) ," +
            "qTy int ," +
            "totalProduct double," +
            "dateOrd varchar(50)," +
            "constraint fk_ordID foreign key (ordID) references dbOrder(ordID) ON DELETE CASCADE," +
            "constraint fk_proID foreign key (proID) references dbProduct(proID) )";

    public MySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        Log.d(TAG, "MySQLHelper: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTBProduct);
        db.execSQL(createTBOrder);
        db.execSQL(createTBOrderDetail);

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }
}
