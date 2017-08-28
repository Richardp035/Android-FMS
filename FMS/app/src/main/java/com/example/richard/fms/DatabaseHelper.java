package com.example.richard.fms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Richard on 03-08-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="fms.db";

    //===================================================================================================================
    private static final String TABLE_NAME1="register";
    private static final String TABLE_NAME2="product";
    private static final String TABLE_NAME3="location";
    //===================================================================================================================
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_PHNO="phone_no";
    private static final String COLUMN_DOB="dob";
    private static final String COLUMN_ADDRESS="address";
    private static final String COLUMN_STREET="street";
    private static final String COLUMN_CITY="city";
    private static final String COLUMN_STATE="state";
    private static final String COLUMN_ZIPCODE="zipcode";
    private static final String COLUMN_GENDER="gender";
    private static final String COLUMN_PASSWORD="password";
    private static final String COLUMN_TYPE="type";
    //===================================================================================================================
    private static final String COLUMN_PRODUCTID="prd_id";
    private static final String COLUMN_PRODUCTNAME="prd_name";
    private static final String COLUMN_PRODUCTSHOPID="shopid";
    private static final String COLUMN_PRODUCTTYPE="prd_type";
    private static final String COLUMN_PRODUCTQUANTITY="prd_quantity";
    private static final String COLUMN_PRODUCTPRICE="prd_price";
    private static final String COLUMN_PRODUCTADDEDDATE="prd_add_date";
    //===================================================================================================================
    private static final String COLUMN_LOCATION="location";
    //===================================================================================================================
    private static final String COLUMN_ORDERID="orderid";
    private static final String COLUMN_ORDERQUANTITY="orderquantity";
    private static final String COLUMN_ORDERTOTAL="ordertotal";
    private static final String COLUMN_ORDERDATE="orderdate";
    //===================================================================================================================

    private static final String TABLE_CREATE1="CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + "(" +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_PHNO +" INTEGER PRIMARY KEY NOT NULL, " +
            COLUMN_DOB + " TEXT NOT NULL, " +
            COLUMN_ADDRESS + " TEXT NOT NULL, " +
            COLUMN_STREET + " TEXT NOT NULL, " +
            COLUMN_CITY + " TEXT NOT NULL, " +
            COLUMN_STATE + " TEXT NOT NULL, " +
            COLUMN_ZIPCODE + " INTEGER NOT NULL, " +
            COLUMN_GENDER + " TEXT NOT NULL, " +
            COLUMN_PASSWORD + " TEXT NOT NULL, " +
            COLUMN_TYPE + " TEXT NOT NULL)";
    //==============================================================================================================

    //======================Product Table===========================================================================
    private static final String TABLE_CREATE2="CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + "(" +
            COLUMN_PRODUCTID + " TEXT PRIMARY KEY NOT NULL, " +
            COLUMN_PRODUCTNAME +" TEXT NOT NULL UNIQUE, " +
            COLUMN_LOCATION + " TEXT NOT NULL, " +
            COLUMN_PRODUCTSHOPID + " TEXT NOT NULL, " +
            COLUMN_PRODUCTTYPE + " TEXT NOT NULL, " +
            COLUMN_PRODUCTQUANTITY + " TEXT NOT NULL, " +
            COLUMN_PRODUCTPRICE + " TEXT NOT NULL, " +
            COLUMN_PRODUCTADDEDDATE + " TEXT NOT NULL)";
    //==============================================================================================================

    //============================== Location Table=================================================================
    private static final String TABLE_CREATE3="CREATE TABLE IF NOT EXISTS " + TABLE_NAME3 + "(" +
            COLUMN_ORDERID + " TEXT PRIMARY KEY NOT NULL, " +
            COLUMN_PHNO + " TEXT NOT NULL, " +
            COLUMN_PRODUCTID + " TEXT NOT NULL, " +
            COLUMN_PRODUCTNAME+ " TEXT NOT NULL, " +
            COLUMN_ORDERQUANTITY + " TEXT NOT NULL, " +
            COLUMN_ORDERTOTAL + " TEXT NOT NULL, " +
            COLUMN_ORDERDATE + " TEXT NOT NULL)";
    //==============================================================================================================

    private SQLiteDatabase sqldb;

    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,5);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE1);
        db.execSQL(TABLE_CREATE2);
        db.execSQL(TABLE_CREATE3);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME3);
        onCreate(db);
    }

    public boolean insertuser(String name, String phno, String dob, String address, String street, String city, String state,
                              String zipcode,String gender, String pass ) {
        sqldb=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_PHNO,phno);
        values.put(COLUMN_DOB,dob);
        values.put(COLUMN_ADDRESS,address);
        values.put(COLUMN_STREET,street);
        values.put(COLUMN_CITY,city);
        values.put(COLUMN_STATE,state);
        values.put(COLUMN_ZIPCODE,zipcode);
        values.put(COLUMN_GENDER,gender);
        values.put(COLUMN_PASSWORD,pass);
        values.put(COLUMN_TYPE,"user");
        long res=sqldb.insert(TABLE_NAME1, null, values);
        if (res==-1)
            return false;
        else
            return true;
    }
    public boolean insertadmin(String name, String phno, String dob, String address, String street, String city, String state,
                              String zipcode,String gender, String pass ) {
        sqldb=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_PHNO,phno);
        values.put(COLUMN_DOB,dob);
        values.put(COLUMN_ADDRESS,address);
        values.put(COLUMN_STREET,street);
        values.put(COLUMN_CITY,city);
        values.put(COLUMN_STATE,state);
        values.put(COLUMN_ZIPCODE,zipcode);
        values.put(COLUMN_GENDER,gender);
        values.put(COLUMN_PASSWORD,pass);
        values.put(COLUMN_TYPE,"admin");
        long res=sqldb.insert(TABLE_NAME1, null, values);
        if (res==-1)
            return false;
        else
            return true;
    }

    public String loginsearch(String phno,String pass)
    {
        sqldb=this.getReadableDatabase();
        Cursor cursor;
        String[] projection={COLUMN_PHNO,COLUMN_PASSWORD,COLUMN_TYPE};
        String sec=COLUMN_PHNO+"=? And "+COLUMN_PASSWORD+"=?";
        String[] sec_arg={phno,pass};
        cursor=sqldb.query(TABLE_NAME1,projection,sec,sec_arg,null,null,null);
        String a="Not Found";
        if(cursor.moveToFirst())
        {
            do{
                if(cursor.getCount() > 0){
                    a = cursor.getString(2);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return a;
    }

    //---------full data view=========

    public Cursor viewregistered(String selected_list, SQLiteDatabase db){
        Cursor cur;
        String[] projection={COLUMN_NAME,COLUMN_PHNO,COLUMN_DOB,COLUMN_ADDRESS,COLUMN_STREET,COLUMN_CITY,COLUMN_STATE,COLUMN_ZIPCODE,COLUMN_GENDER,COLUMN_PASSWORD,COLUMN_TYPE};
        String sec=COLUMN_PHNO+"=?";
        String[] sec_arg={selected_list};
        cur=db.query(TABLE_NAME1,projection,sec,sec_arg,null,null,null);
        return cur;
    }

    //------------View in list-----------

    public Cursor viewdata(SQLiteDatabase db) {
        Cursor res;
        String[] projection={COLUMN_PHNO};
        res=db.query(TABLE_NAME1,projection,null,null,null,null,null);
        return res;
    }

    //----------Delete------------

    public boolean deleteregistered(String list) {
        sqldb=this.getWritableDatabase();
        String[] arg={list};
        boolean ret=sqldb.delete(TABLE_NAME1,COLUMN_PHNO+"=?",arg)>0;
        return ret;
    }

    //========update register==========

    public boolean updateRegister(String phno,String name, String dob, String address, String street, String city, String state,
                                  String zipcode, String gender, String pass, String type)
    {
        sqldb=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_DOB,dob);
        values.put(COLUMN_ADDRESS,address);
        values.put(COLUMN_STREET,street);
        values.put(COLUMN_CITY,city);
        values.put(COLUMN_STATE,state);
        values.put(COLUMN_ZIPCODE,zipcode);
        values.put(COLUMN_GENDER,gender);
        values.put(COLUMN_PASSWORD,pass);
        values.put(COLUMN_TYPE,type);
        String[] arg={phno};
        long res=sqldb.update(TABLE_NAME1,values,COLUMN_PHNO+"=?",arg);
        if (res==-1)
            return false;
        else
            return true;
    }

    //==========full data=========
    public Cursor updateedit(String list){
        sqldb=this.getReadableDatabase();
        Cursor cur;
        String[] projection={COLUMN_NAME,COLUMN_DOB,COLUMN_ADDRESS,COLUMN_STREET,COLUMN_CITY,COLUMN_STATE,COLUMN_ZIPCODE,COLUMN_GENDER,COLUMN_PASSWORD,COLUMN_TYPE};
        String sec=COLUMN_PHNO+"=?";
        String[] sec_arg={list};
        cur=sqldb.query(TABLE_NAME1,projection,sec,sec_arg,null,null,null);
        return cur;
    }

    //================================= Product Data ===============================================================================

    //product insert
    public boolean insertProduct(String pid, String pname,String location, String shopid, String ptype,
                                 String pquantity, String pprice, String padddate)
    {
        sqldb=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_PRODUCTID,pid);
        values.put(COLUMN_PRODUCTNAME,pname);
        values.put(COLUMN_LOCATION,location);
        values.put(COLUMN_PRODUCTSHOPID,shopid);
        values.put(COLUMN_PRODUCTTYPE,ptype);
        values.put(COLUMN_PRODUCTQUANTITY,pquantity);
        values.put(COLUMN_PRODUCTPRICE,pprice);
        values.put(COLUMN_PRODUCTADDEDDATE,padddate);
        long res=sqldb.insert(TABLE_NAME2, null, values);
        if (res==-1)
            return false;
        else
            return true;
    }

    public Cursor prdview(SQLiteDatabase db) {
        Cursor res;
        String[] projection={COLUMN_PRODUCTNAME};
        res=db.query(TABLE_NAME2,projection,null,null,null,null,null);
        return res;
    }

    //---------full data view=========

    public Cursor viewPrd(String list, SQLiteDatabase db){
        Cursor cur;
        String[] projection={COLUMN_PRODUCTID,COLUMN_PRODUCTNAME,COLUMN_PRODUCTSHOPID,COLUMN_PRODUCTTYPE,COLUMN_PRODUCTQUANTITY,
                COLUMN_PRODUCTPRICE,COLUMN_PRODUCTADDEDDATE};
        String sec=COLUMN_PRODUCTNAME+"=?";
        String[] sec_arg={list};
        cur=db.query(TABLE_NAME2,projection,sec,sec_arg,null,null,null);
        return cur;
    }

    //==============================================================================

    public Cursor Orderprdview(SQLiteDatabase db) {
        Cursor res;
        String[] projection = {COLUMN_PRODUCTNAME};
        res = db.query(TABLE_NAME2, projection, null, null, null, null, null);
        return res;
    }
    //================================================================================================================================

    public Cursor viewPrdbyName(String selected_list, SQLiteDatabase db){
        Cursor cur;
        String[] projection={COLUMN_PRODUCTQUANTITY, COLUMN_PRODUCTPRICE,COLUMN_PRODUCTID};
        String sec=COLUMN_PRODUCTNAME+"=?";
        String[] sec_arg={selected_list};
        cur=db.query(TABLE_NAME2,projection,sec,sec_arg,null,null,null);
        return cur;
    }

    public boolean orderprd(String orderid, String phno, String pid, String list, String qunstr, String tot, String date) {
        sqldb=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_ORDERID,orderid);
        values.put(COLUMN_PHNO,phno);
        values.put(COLUMN_PRODUCTID,pid);
        values.put(COLUMN_PRODUCTNAME,list);
        values.put(COLUMN_ORDERQUANTITY,qunstr);
        values.put(COLUMN_ORDERTOTAL,tot);
        values.put(COLUMN_ORDERDATE,date);
        long res=sqldb.insert(TABLE_NAME3, null, values);
        if (res==-1)
            return false;
        else
            return true;
    }

    public boolean orderprdupdate(String list, double orgqnt) {
        sqldb=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_PRODUCTQUANTITY,orgqnt);
        String[] arg={list};
        long res=sqldb.update(TABLE_NAME2,values,COLUMN_PRODUCTNAME+"=?",arg);
        if (res==-1)
            return false;
        else
            return true;
    }

    public Cursor updatePrdEdit(String pnme) {
        sqldb=this.getReadableDatabase();
        Cursor cur;
        String[] projection={COLUMN_PRODUCTID,COLUMN_PRODUCTNAME,COLUMN_LOCATION,COLUMN_PRODUCTSHOPID,COLUMN_PRODUCTTYPE,
                COLUMN_PRODUCTQUANTITY,COLUMN_PRODUCTPRICE,COLUMN_PRODUCTADDEDDATE};
        String sec=COLUMN_PRODUCTNAME+"=?";
        String[] sec_arg={pnme};
        cur=sqldb.query(TABLE_NAME2,projection,sec,sec_arg,null,null,null);
        return cur;
    }

    public boolean UpdateProduct(String pnme, String ptype, String pquantity, String pprice) {
        sqldb=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_PRODUCTTYPE,ptype);
        values.put(COLUMN_PRODUCTQUANTITY,pquantity);
        values.put(COLUMN_PRODUCTPRICE,pprice);
        String[] arg={pnme};
        long res=sqldb.update(TABLE_NAME2,values,COLUMN_PRODUCTNAME+"=?",arg);
        if (res==-1)
            return false;
        else
            return true;
    }

    public Cursor OrderEDprdview(SQLiteDatabase db, String phno) {
        Cursor res;
        String[] projection = {COLUMN_ORDERID};
        String sec=COLUMN_PHNO+"=?";
        String[] sec_arg={phno};
        res = db.query(TABLE_NAME3, projection, sec, sec_arg, null, null, null);
        return res;
    }

    public Cursor viewOrderEDprd(String list, SQLiteDatabase sqldb) {
        Cursor cur;
        String[] projection={COLUMN_ORDERID,COLUMN_PHNO,COLUMN_PRODUCTID,COLUMN_PRODUCTNAME,COLUMN_ORDERQUANTITY,
                COLUMN_ORDERTOTAL,COLUMN_ORDERDATE};
        String sec=COLUMN_ORDERID+"=?";
        String[] sec_arg={list};
        cur=sqldb.query(TABLE_NAME3,projection,sec,sec_arg,null,null,null);
        return cur;
    }

    public boolean deleteorderedPrd(String list) {
        sqldb=this.getWritableDatabase();
        String[] arg={list};
        boolean ret=sqldb.delete(TABLE_NAME3,COLUMN_ORDERID+"=?",arg)>0;
        return ret;
    }
}
