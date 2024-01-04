package com.example.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SporActivity2 extends AppCompatActivity {

    int newArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spor2);

        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        newArray = new int[]{

                R.id.exercise_pose,R.id.exercise_pose2, R.id.exercise_pose3,R.id.exercise_pose4, R.id.exercise_pose5,R.id.exercise_pose6, R.id.exercise_pose7,R.id.exercise_pose8,
                R.id.exercise_pose9,R.id.exercise_pose10


        };





    }

    public void Imagebuttonclicker(View view) {

        for(int i=0;i<newArray.length;i++){
            if(view.getId()==newArray[i]){
                int value=i+1;
                Log.i("FIRST",String.valueOf(value));
                Intent intent=new Intent(SporActivity2.this,SporActivity4.class);
                intent.putExtra("value",String.valueOf(value));
                startActivity(intent);
            }
        }








    }
}