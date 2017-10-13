package com.tangdi.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by tangdi on 10/12/17.
 */

public class CustomerViewActivity extends AppCompatActivity {

    private CustomerProgess progess;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_activity);
        progess = (CustomerProgess)findViewById(R.id.progess);
        progess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomerViewActivity.this, "dianjile", Toast.LENGTH_SHORT).show();
                progess.overThread();
                Intent intent = new Intent(CustomerViewActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        progess.setOverLinstner(new CustomerProgess.overCallback() {
            @Override
            public void over() {
                Intent intent = new Intent(CustomerViewActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
