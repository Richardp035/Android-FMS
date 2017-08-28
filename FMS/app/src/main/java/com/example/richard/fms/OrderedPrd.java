package com.example.richard.fms;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OrderedPrd extends AppCompatActivity {

    Intent in;
    String phno;
    ArrayAdapter<String> dataAdapter;
    Context con=this;
    DatabaseHelper  userdb;
    SQLiteDatabase sqldb;
    ListView lsve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordered_prd);

        //=========================================
        Bundle si=getIntent().getExtras();
        phno=si.getString("");
        //=========================================

        lsve = (ListView) findViewById(R.id.listing);
        final List<String> list = new ArrayList<String>();
        userdb=new DatabaseHelper(con);
        sqldb=userdb.getWritableDatabase();
        Cursor cs=userdb.OrderEDprdview(sqldb,phno);
        if(cs.moveToFirst()){
            do{
                list.add(cs.getString(0));
            }while(cs.moveToNext());
        }
        else{
            list.add("Not Found");
        }
        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        lsve.setAdapter(dataAdapter);
        lsve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parant, final View view, int position, long id) {
                final String selected_list = list.get(position);
                final Cursor vReg = userdb.viewOrderEDprd(selected_list, sqldb);
                StringBuffer buff = new StringBuffer();
                while (vReg.moveToNext()) {
                    buff.append("Order Id\t:\t" + vReg.getString(0) + "\n");
                    buff.append("Orderer Phone No\t:\t" + vReg.getString(1) + "\n");
                    buff.append("Product Id\t:\t"+vReg.getString(2)+"\n");
                    buff.append("Product Name\t:\t"+vReg.getString(3)+"\n");
                    buff.append("Ordered Quantity\t:\t"+vReg.getString(4)+"\n");
                    buff.append("Order Total Amount\t:\t"+vReg.getString(5)+"\n");
                    buff.append("Ordered Date\t:\t"+vReg.getString(6)+"\n");

                    //Alert Dialog=======================================
                    final AlertDialog builder = new AlertDialog.Builder(OrderedPrd.this).create();
                    builder.setCancelable(false);
                    builder.setCanceledOnTouchOutside(false);
                    builder.setTitle("Ordered Product");
                    builder.setMessage(buff.toString());

                    builder.setButton(AlertDialog.BUTTON_POSITIVE,"Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    builder.setButton(AlertDialog.BUTTON_NEUTRAL,"Delete Product", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean ret=userdb.deleteorderedPrd(selected_list);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                            if(ret==false)
                                Toast.makeText(getApplicationContext(),"Deleting Failed",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(),"Deleting Success",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                    //builder.getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(false);
                }
                userdb.close();
            }
        });
    }
}