package com.example.rajaramanan.task3spider;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   MediaPlayer mediaPlayer;
    SensorManager mySensormanager;
    Sensor myProximitySensor;
    float swipecheck=1;
    int enablecheck=0;

    int[] ids={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h};
    int imagecount=1;
    int timercount=0;
    int k;

    Thread imagethread;
    Thread timerthread;
    ImageView imageView;
    Button enablebutton;
    Button disablebutton;
    Button slideshowbutton;
    int disablecheck=0;
    int stoptimer=0;
    int stopslideshow=0;
    Spinner spinner;
    int selected;

    String[] track={"ipl","icc"};
    int[] idstrack={R.raw.ipl,R.raw.icc};
     //final  int id2= getResources().getIdentifier("icc.mp3","raw",getPackageName());




    Handler timerhandler= new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            TextView timertext= (TextView)findViewById(R.id.timertext);
            assert timertext != null;
            timertext.setText(Integer.toString(timercount));


        }
    };

    Handler imagehandler= new Handler()
    {


        @Override
        public void handleMessage(Message msg) {
            imageView=(ImageView)findViewById(R.id.imageView);
            assert imageView!=null;
            imageView.setImageDrawable(getResources().getDrawable(ids[imagecount]));
            imagecount++;
            imagecount=imagecount%8;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mySensormanager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        myProximitySensor= mySensormanager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        slideshowbutton=(Button)findViewById(R.id.slidebutton);
         enablebutton=(Button)findViewById(R.id.enable);
        spinner=(Spinner)findViewById(R.id.spinner);
        disablebutton=(Button)findViewById(R.id.disable);
        disablebutton.setEnabled(false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, track);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        selected = spinner.getSelectedItemPosition();
                        mediaPlayer=MediaPlayer.create(getApplicationContext(),idstrack[selected]);

                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }


        );






    }

    public void slideshow(View view)
    {
        enablebutton.setEnabled(true);
        Runnable imagerun= new Runnable() {
            @Override
            public void run() {

                long time=System.currentTimeMillis()+(9-imagecount)*3000;
                while (System.currentTimeMillis()<time && enablecheck==0 && stopslideshow==0)
                {

                synchronized (this)
                {
                    try{
                        wait(3000);

                            imagehandler.sendEmptyMessage(0);
                        time=time+3000;


                    }catch (Exception e){}
                }
            }
                if(enablecheck==1||stopslideshow==1)
                {
                    stoptimer=1;
                }

            }
        };
        imagethread=new Thread(imagerun);
        imagethread.start();

        Runnable timerrun= new Runnable() {
            @Override
            public void run() {
                //long timenow=System.currentTimeMillis()+3000;
                while(  stoptimer==0)
                {

                    synchronized (this)
                    {
                        try
                        {
                            wait(1000);
                            timercount++;
                                timerhandler.sendEmptyMessage(0);


                        }catch (Exception e){}
                    }
                }
            }
        };timerthread= new Thread(timerrun);
        timerthread.start();
    }

    public void play(View view)
    {
        Runnable playrun = new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    try{
                        mediaPlayer.start();
                    }catch(Exception e){}
                }
            }
        };
        Thread playthread=new Thread(playrun);
        playthread.start();


    }

    public void stop(View view)
    {
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();}


    public void enable (View view)
    {
        k=imagecount;
        disablecheck=0;
        enablebutton.setEnabled(false);
        disablebutton.setEnabled(true);
        slideshowbutton.setEnabled(false);

        enablecheck=1;


        Runnable enablerun=new Runnable() {
            @Override
            public void run() {
                if(myProximitySensor==null)
                {
                    Toast.makeText(getApplicationContext(),"No Proximity Sensor",Toast.LENGTH_LONG);
                }

                else
                {
                    mySensormanager.registerListener(proximitySensorListener,myProximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
                }


            }
        };Thread enablethread= new Thread(enablerun);
        enablethread.start();}




    SensorEventListener proximitySensorListener = new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_PROXIMITY)
            {
                swipecheck=event.values[0];
                if(swipecheck==0)
                {
                    changeimage(disablecheck);
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void changeimage(int check)
    {
        if(check==0) {
            imageView.setImageDrawable(getResources().getDrawable(ids[k]));
            k++;
            k=k%8;
        }

    }

    public void disable(View view)
    {
        disablebutton.setEnabled(false);
        slideshowbutton.setEnabled(true);
        enablecheck=0;
        disablecheck=1;
        stoptimer=0;

    }

    public void stopshow (View view)
    {
        stopslideshow=1;
    }




}

