package zhang.zhixuan.mobileapp_airline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ruicai on 31/10/15.
 */
public class MyDB {
    MyDBHelper       DBHelper;
    SQLiteDatabase db;
    final Context context;

    public MyDB(Context ctx) {
        this.context = ctx;
        DBHelper = new MyDBHelper(this.context);
    }


    public MyDB open() {
        db = DBHelper.getWritableDatabase();

   //     Toast.makeText(context, Environment.getDataDirectory().toString(), Toast.LENGTH_SHORT).show();

        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertMember(String userName, String password, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MyDBHelper.columnName_memberUserName, userName);
        initialValues.put(MyDBHelper.columnName_memberPassword,password );
        initialValues.put(MyDBHelper.columnName_memberEmail, email);

        return db.insert(MyDBHelper.tableName, null, initialValues);
    }

    public int deleteFurniture(long id) {
        return  db.delete(MyDBHelper.tableName, MyDBHelper.columnName_memberID + "=" + id, null);
    }

    public int deleteAllFurniture() {
        return db.delete(MyDBHelper.tableName, "1", null);    // delete all records
    }

    public int updateMember(String email, String firstN, String secondN,  String title, String address, String country, String city, String zipcode, String contactN) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MyDBHelper.columnName_memberFirstName, firstN);
        initialValues.put(MyDBHelper.columnName_memberSecondName, secondN);
        initialValues.put(MyDBHelper.columnName_memberTitle, title);
        initialValues.put(MyDBHelper.columnName_memberAddress, address);
        initialValues.put(MyDBHelper.columnName_memberCountry, country);
        initialValues.put(MyDBHelper.columnName_memberCity, city);
        initialValues.put(MyDBHelper.columnName_memberZipcode, zipcode);
        initialValues.put(MyDBHelper.columnName_memberContactN, contactN);

        return db.update(MyDBHelper.tableName, initialValues, MyDBHelper.columnName_memberEmail + "=?", new String[]{email + ""});
    }

    public Cursor getAllMembers() {
        return db.query(
                MyDBHelper.tableName,
                new String[]{
                        MyDBHelper.columnName_memberEmail},
                null, null, null, null, null);
    }


    public Cursor getMemberByEmail(String email) {
        Cursor mCursor = db.query(MyDBHelper.tableName,
                new String[] {
                        MyDBHelper.columnName_memberID,
                        MyDBHelper.columnName_memberFirstName,
                        MyDBHelper.columnName_memberSecondName,
                        MyDBHelper.columnName_memberGender,
                        MyDBHelper.columnName_memberTitle,
                        MyDBHelper.columnName_memberAddress,
                        MyDBHelper.columnName_memberCountry,
                        MyDBHelper.columnName_memberCity,
                        MyDBHelper.columnName_memberZipcode,
                        MyDBHelper.columnName_memberContactN},
                MyDBHelper.columnName_memberEmail+"=?",
                new String[]{email}, null, null, null, null);
        
        return mCursor;
    }
    public Cursor getMemberPasswordByEmail(String email) {
        Cursor mCursor = db.query(MyDBHelper.tableName,
                new String[] {
                        MyDBHelper.columnName_memberID,
                        MyDBHelper.columnName_memberPassword},
                MyDBHelper.columnName_memberEmail+"=?",
                new String[]{email}, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
