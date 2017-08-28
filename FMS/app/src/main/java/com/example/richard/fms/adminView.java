package com.example.richard.fms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class adminView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view);
    }
    public void AdminView(View v)
    {
        if (v.getId()==R.id.viewbtn){
            Intent adminview=new Intent(this,viewRegistered.class);
            startActivity(adminview);
        }
        else if(v.getId()==R.id.admin_registerbtn){
            Intent adminreg=new Intent(this,adminRegister.class);
            startActivity(adminreg);
        }
        else if(v.getId()==R.id.add_product)
        {
            Intent addprod=new Intent(this,addProduct.class);
            startActivity(addprod);
        }
        else if(v.getId()==R.id.viewprdbut)
        {
            Intent addprod=new Intent(this,viewProduct.class);
            startActivity(addprod);
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
