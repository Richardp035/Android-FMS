package com.example.richard.fms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class userView extends AppCompatActivity {

    Intent in;
    String phno;
    TextView cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view);
        Bundle si=getIntent().getExtras();
        phno=si.getString("");
        cont=(TextView)findViewById(R.id.tvphno);
        cont.setText("welcome > "+phno+" < now you can order ond view your product");
    }
    public void userview(View v)
    {
        if (v.getId()==R.id.prd_order_btn){
            in=new Intent(this,OrderedPrd.class);
            in.putExtra("",phno);
            startActivity(in);
        }
        else if(v.getId()==R.id.prd_to_order_btn){
            in=new Intent(getApplicationContext(),Order.class);
            in.putExtra("",phno);
            startActivity(in);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Intent reg=new Intent(this,Navigation.class);
            startActivity(reg);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
