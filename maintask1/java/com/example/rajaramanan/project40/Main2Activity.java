package com.example.rajaramanan.project40;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView =(TextView)findViewById(R.id.textView);

        Intent intent=getIntent();
        Button button=(Button)findViewById(R.id.button2);
        String c= intent.getExtras().getString("name");
        String mytime = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());

        if (textView != null) {
            textView.setText(" Thank you " +c+ "!"    +  "  Your response has been recorded at " + mytime );

        }

    }

    public void onClick1(View view)
    {
        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
