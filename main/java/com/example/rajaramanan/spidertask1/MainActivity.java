package com.example.rajaramanan.spidertask1;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button clickbutton;
    TextView displaytext;
    int c=0;
    String c2;

    public void change(int a)
    {
        displaytext.setText(Integer.toString(a));
    }



    @Nullable
    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickbutton = (Button) findViewById(R.id.clickbutton);
        displaytext = (TextView) findViewById(R.id.displaytext);

        clickbutton.setOnClickListener(
                new Button.OnClickListener(){
                    public void  onClick(View v)
                    {
                        c++;
                        change(c);

                    }

                }
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        c2= Integer.toString(c);
        outState.putString("count",c2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        c=Integer.parseInt(savedInstanceState.getString("count"));
        change(c);


    }
}
