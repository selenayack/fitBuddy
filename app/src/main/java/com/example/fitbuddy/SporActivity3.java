package com.example.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SporActivity3 extends AppCompatActivity {

    String buttonvalue;
    Button startBtn;
    private CountDownTimer countDownTimer;
    TextView mtextview;
    private boolean MTimeRunning;
    private long MTimeLeftingmills;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spor3);

        Intent intent=getIntent();
        buttonvalue=intent.getStringExtra("value");

        int intvalue=Integer.valueOf(buttonvalue);
        switch (intvalue){
            case 1:
                setContentView(R.layout.activity_mountain);
                break;
            case 2:
                setContentView(R.layout.activity_crunch);
                break;
            case 3:
                setContentView(R.layout.activity_bench);
                break;
            case 4:
                setContentView(R.layout.activity_bicyclecrunch);
                break;
            case 5:
                setContentView(R.layout.activity_legraise);
                break;
            case 6:
                setContentView(R.layout.activity_heeltouch);
                break;
            case 7:
                setContentView(R.layout.activity_kneecrunch);
                break;
            case 8:
                setContentView(R.layout.activity_situp);
                break;
            case 9:
                setContentView(R.layout.activity_vups);
                break;
            case 10:
                setContentView(R.layout.activity_plank);
                break;
            case 11:
                setContentView(R.layout.activity_legplank);
                break;
            case 12:
                setContentView(R.layout.activity_twist);
                break;
            case 13:
                setContentView(R.layout.activity_bridge);
                break;
            case 14:
                setContentView(R.layout.activity_windmill);
                break;
            case 15:
                setContentView(R.layout.activity_legup);
                break;


        }

        startBtn=findViewById(R.id.startButton);
        mtextview=findViewById(R.id.time);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MTimeRunning){
                    stoptimer();

                }
                else{
                    starttimer();

                }

            }
        });




    }

    private void stoptimer(){
        countDownTimer.cancel();
        MTimeRunning=false;
        startBtn.setText("BAŞLA");

    }
    private  void starttimer(){
        final CharSequence value1=mtextview.getText();
        String num1=value1.toString();
        String num2=num1.substring(0,2);
        String num3=num1.substring(3,5);

        final int number=Integer.valueOf(num2)*60+Integer.valueOf(num3);
        MTimeLeftingmills=number*1000;

        countDownTimer=new CountDownTimer(MTimeLeftingmills,1000) {
            @Override
            public void onTick(long l) {

            MTimeLeftingmills=l;
            updateTimer();


            }

            @Override
            public void onFinish() {

                int newvalue=Integer.valueOf(buttonvalue)+1;
                if(newvalue<=7){
                    Intent intent=new Intent(SporActivity3.this,SporActivity3.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newvalue));
                    startActivity(intent);
                }
                else{
                    newvalue=1;
                    Intent intent=new Intent(SporActivity3.this,SporActivity3.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newvalue));
                    startActivity(intent);



                }






            }

        }.start();
        startBtn.setText("DURDUR");
        MTimeRunning=true;

    }



    private void updateTimer() {

        int minutes=(int)MTimeLeftingmills/60000;
        int seconds=(int)MTimeLeftingmills%60000/1000;

        String timeLeftText="";
        if(minutes<10)
            timeLeftText="0";
        timeLeftText=timeLeftText+minutes+":";
        if(seconds<10)
            timeLeftText="0";
        timeLeftText+=seconds;
        mtextview.setText(timeLeftText);




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }


}