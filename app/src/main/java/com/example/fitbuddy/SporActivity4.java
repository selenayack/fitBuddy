package com.example.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SporActivity4 extends AppCompatActivity {

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
                setContentView(R.layout.activity_run);
                break;
            case 2:
                setContentView(R.layout.activity_squat);
                break;
            case 3:
                setContentView(R.layout.activity_mountain_climber);
                break;
            case 4:
                setContentView(R.layout.activity_jumpsquat);
                break;
            case 5:
                setContentView(R.layout.activity_bridge2);
                break;
            case 6:
                setContentView(R.layout.activity_burpee);
                break;
            case 7:
                setContentView(R.layout.activity_bicyclecrunch2);
                break;
            case 8:
                setContentView(R.layout.activity_jumping_jack);
                break;
            case 9:
                setContentView(R.layout.activity_ip_atlama);
                break;
            case 10:
                setContentView(R.layout.activity_crunch2);
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
                    Intent intent=new Intent(SporActivity4.this,SporActivity4.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newvalue));
                    startActivity(intent);
                }
                else{
                    newvalue=1;
                    Intent intent=new Intent(SporActivity4.this,SporActivity4.class);
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