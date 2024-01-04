package com.example.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SporActivity1 extends AppCompatActivity {

    int newArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spor1);

        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        newArray = new int[]{

                R.id.boat_pose,R.id.boat_pose2, R.id.boat_pose3,R.id.boat_pose4, R.id.boat_pose5,R.id.boat_pose6, R.id.boat_pose7,R.id.boat_pose8,
                R.id.boat_pose9,R.id.boat_pose10, R.id.boat_pose11,R.id.boat_pose12, R.id.boat_pose13,R.id.boat_pose14,R.id.boat_pose15


        };





    }

    public void Imagebuttonclicker(View view) {

        for(int i=0;i<newArray.length;i++){
            if(view.getId()==newArray[i]){
                int value=i+1;
                Log.i("FIRST",String.valueOf(value));
                Intent intent=new Intent(SporActivity1.this,SporActivity3.class);
                intent.putExtra("value",String.valueOf(value));
                startActivity(intent);
            }
        }








    }
}