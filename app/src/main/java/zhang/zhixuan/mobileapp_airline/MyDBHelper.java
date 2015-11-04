package zhang.zhixuan.mobileapp_airline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ruicai on 31/10/15.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    public static final int    databaseVersion                  = 2;
    public static final String databaseName                     = "memberDB";

    public static final String tableName                        = "memberTable";
    public static final String columnName_memberID                = "_id";
    public static final String columnName_memberUserName              = "userName";
    public static final String columnName_memberTitle              = "title";
    public static final String columnName_memberAddress              = "address";
    public static final String columnName_memberFirstName              = "firstName";
    public static final String columnName_memberSecondName    = "secondName";
    public static final String columnName_memberGender    = "gender";
    public static final String columnName_memberCountry    = "country";
    public static final String columnName_memberCity    = "city";
    public static final String columnName_memberZipcode    = "zipcode";
    public static final String columnName_memberContactN    = "contactNumber";
    public static final String columnName_memberEmail    = "email";
    public static final String columnName_memberPassword    = "password";


    private static final String SQLite_CREATE =
            "CREATE TABLE " + tableName + "(" + columnName_memberID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + columnName_memberFirstName + " TEXT,"+columnName_memberSecondName + " TEXT,"
                    +columnName_memberGender + " TEXT," +columnName_memberUserName + " TEXT,"
                    +columnName_memberTitle + " TEXT,"+columnName_memberAddress + " TEXT,"
                    +columnName_memberCountry + " TEXT,"+columnName_memberCity + " TEXT,"
                    +columnName_memberZipcode + " TEXT,"+columnName_memberContactN + " TEXT,"
                    +columnName_memberPassword + " TEXT NOT NULL,"+columnName_memberEmail + " TEXT NOT NULL);";

    private static final String SQLite_DELETE = "DROP TABLE IF EXISTS " + tableName;


    public MyDBHelper(Context context) {
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
