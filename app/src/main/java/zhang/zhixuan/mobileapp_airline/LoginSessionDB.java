package zhang.zhixuan.mobileapp_airline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

/**
 * Created by ruicai on 4/11/15.
 */
public class LoginSessionDB {
    LoginSessionDBHelper       DBHelper;
    SQLiteDatabase db;
    final Context context;

    public LoginSessionDB(Context ctx) {
        this.context = ctx;
        DBHelper = new LoginSessionDBHelper(this.context);
    }


    public LoginSessionDB open() {
        db = DBHelper.getWritableDatabase();

        Toast.makeText(context, Environment.getDataDirectory().toString(), Toast.LENGTH_SHORT).show();

        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertLoginSession(String status, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(LoginSessionDBHelper.columnName_memberLoginStatus, status);
        initialValues.put(LoginSessionDBHelper.columnName_memberEmail,email );



        return db.insert(LoginSessionDBHelper.tableName, null, initialValues);
    }

    public int deleteFurniture(long id) {
        return  db.delete(LoginSessionDBHelper.tableName, LoginSessionDBHelper.columnName_memberID + "=" + id, null);
    }

    public int deleteAllSession() {
        return db.delete(LoginSessionDBHelper.tableName, "1", null);    // delete all records
    }

    public int updateLoginStatus(String status, String email){

        ContentValues initialValues = new ContentValues();
        initialValues.put(LoginSessionDBHelper.columnName_memberLoginStatus, status);
        initialValues.put(LoginSessionDBHelper.columnName_memberEmail, email);


        return db.update(LoginSessionDBHelper.tableName, initialValues, LoginSessionDBHelper.columnName_memberEmail + "=?", new String[]{email + ""});
    }

    public Cursor getAllSession() {
        return db.query(
                LoginSessionDBHelper.tableName,
                new String[]{
                        LoginSessionDBHelper.columnName_memberID,
                        LoginSessionDBHelper.columnName_memberLoginStatus,
                        LoginSessionDBHelper.columnName_memberEmail,
                },
                null,null, null, null, null);
    }


//    public Cursor getMemberByEmail(String email) {
//        Cursor mCursor = db.query(LoginSessionDBHelper.tableName,
//                new String[] {
//                        LoginSessionDBHelper.columnName_memberID,
//                        LoginSessionDBHelper.columnName_memberFirstName,
//                        MyDBHelper.columnName_memberSecondName,
//                        MyDBHelper.columnName_memberGender,
//                        MyDBHelper.columnName_memberTitle,
//                        MyDBHelper.columnName_memberAddress,
//                        MyDBHelper.columnName_memberCountry,
//                        MyDBHelper.columnName_memberCity,
//                        MyDBHelper.columnName_memberZipcode,
//                        MyDBHelper.columnName_memberContactN},
//                MyDBHelper.columnName_memberEmail+"=?",
//                new String[]{email}, null, null, null, null);
//
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor;
//    }
//    public Cursor getMemberPasswordByEmail(String email) {
//        Cursor mCursor = db.query(MyDBHelper.tableName,
//                new String[] {
//                        MyDBHelper.columnName_memberID,
//                        MyDBHelper.columnName_memberPassword},
//                MyDBHelper.columnName_memberEmail+"=?",
//                new String[]{email}, null, null, null, null);
//
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor;
//    }



}
