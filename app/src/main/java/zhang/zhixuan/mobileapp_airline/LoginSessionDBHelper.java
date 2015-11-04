package zhang.zhixuan.mobileapp_airline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ruicai on 4/11/15.
 */
public class LoginSessionDBHelper extends SQLiteOpenHelper {

    public static final int    databaseVersion                  = 1;
    public static final String databaseName                     = "loginSessionDB";

    public static final String tableName                        = "loginSessionTable";
    public static final String columnName_memberID                = "_id";
    public static final String columnName_memberLoginStatus              = "loginStatus";
    public static final String columnName_memberEmail              = "email";



    private static final String SQLite_CREATE =
            "CREATE TABLE " + tableName + "(" + columnName_memberID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + columnName_memberLoginStatus + " TEXT,"+columnName_memberEmail + " TEXT);";

    private static final String SQLite_DELETE = "DROP TABLE IF EXISTS " + tableName;


    public LoginSessionDBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }


    // note: becase MyDBHelper extends SQLiteOpenHelper, we need to implement onCreate
    //       and onUpgrade, else Android Studio will complain of error.

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLite_CREATE);
    }

    // onUpgrade is called if the database version is increased in your application code
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLite_DELETE);
        onCreate(db);
    }


}