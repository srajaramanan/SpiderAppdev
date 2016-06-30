package com.example.rajaramanan.project40;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    Button submitbutton;
    EditText nametext,rolltext;



    String[] celebrities = {
            "Computer science ",
            "Chemical",
            "EEE",
            "ECE",
            "Civil",
            "Production ",
            "MME",
            "Mechanical",
            "ICE"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, celebrities);
        submitbutton=(Button)findViewById(R.id.submitbutton);
        nametext = (EditText)findViewById(R.id.nameinput);
        rolltext=(EditText)findViewById(R.id.noinput);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int
                                position = spinner.getSelectedItemPosition();
                        Toast.makeText(getApplicationContext(),"You have selected "+celebrities[+position],Toast.LENGTH_LONG).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }


        );




    }

    public void onClick(View view)
    {
        EditText nametext=(EditText)findViewById(R.id.nameinput) ;

        if(nametext.getText().toString().length()==0||rolltext.getText().toString().length()==0 )
        {
            Toast.makeText(getApplicationContext(),"Name and Roll no. fields cannot be left vacant",Toast.LENGTH_LONG).show();

        }
        else {

            Intent intent = new Intent(this, Main2Activity.class);
            String c = nametext.getText().toString();
            intent.putExtra("name", c);
            startActivity(intent);
        }
    }
}
