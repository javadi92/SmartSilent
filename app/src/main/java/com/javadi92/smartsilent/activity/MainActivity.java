package com.javadi92.smartsilent.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.javadi92.smartsilent.R;
import com.javadi92.smartsilent.alarm.AlarmHelper;
import com.javadi92.smartsilent.utils.App;

public class MainActivity extends AppCompatActivity {

    Button buttonSilent,buttonCancel;
    AudioManager audioManager;
    AlarmHelper alarmHelper;
    TextView textViewHour,textViewMinute;
    int saved_hour,saved_minute;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("سایلنت هوشمند");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(App.sharedPreferences.getBoolean("first_time",true) && Build.VERSION.SDK_INT>=Build.VERSION_CODES.M
            && !notificationManager.isNotificationPolicyAccessGranted()){

            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("برای استفاده از این برنامه باید مجوز دسترسی به تغییر حالت سایلنت را بدهید");
            builder.setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    App.sharedPreferences.edit().putBoolean("first_time",false).commit();
                    //open settings phone

                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("نمی خوام", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                    Toast.makeText(MainActivity.this, "برنامه قادر به کار نیست", Toast.LENGTH_SHORT).show();
                }
            });
            dialog=builder.create();
            dialog.show();
        }



        textViewHour=findViewById(R.id.textView_hour);
        textViewMinute=findViewById(R.id.textView_minute);
        buttonCancel=findViewById(R.id.button_cancel);
        buttonSilent=findViewById(R.id.button_silent);

        saved_hour=App.sharedPreferences.getInt("hour",7);
        saved_minute=App.sharedPreferences.getInt("minute",30);
        if(saved_hour<10){
            textViewHour.setText("0"+saved_hour);
        }
        else {
            textViewHour.setText(saved_hour+"");
        }
        if(saved_minute<10){
            textViewMinute.setText("0"+saved_minute);
        }
        else {
            textViewMinute.setText(saved_minute+"");
        }

        if(App.sharedPreferences.getBoolean("enabled",false)==false){
            buttonSilent.setVisibility(View.VISIBLE);
            buttonCancel.setVisibility(View.GONE);
        }
        else {
            buttonSilent.setVisibility(View.GONE);
            buttonCancel.setVisibility(View.VISIBLE);
        }

        buttonSilent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour= App.sharedPreferences.getInt("hour",0);
                int minute=App.sharedPreferences.getInt("minute",0);
                alarmHelper=new AlarmHelper(MainActivity.this);
                alarmHelper.setAlarm(hour,minute);
                audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(MainActivity.this, "گوشی به حالت سایلنت رفت", Toast.LENGTH_LONG).show();
                App.sharedPreferences.edit().putBoolean("enabled",true).commit();
                buttonSilent.setVisibility(View.GONE);
                buttonCancel.setVisibility(View.VISIBLE);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alarmHelper!=null){
                    alarmHelper.cancelAlarm();
                }
                else {
                    alarmHelper=new AlarmHelper(MainActivity.this);
                    alarmHelper.cancelAlarm();
                }
                audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(MainActivity.this, "گوشی از حالت سایلنت درآمد", Toast.LENGTH_LONG).show();
                App.sharedPreferences.edit().putBoolean("enabled",false).commit();
                buttonCancel.setVisibility(View.GONE);
                buttonSilent.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.manu_settings):
                Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent,1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            saved_hour=App.sharedPreferences.getInt("hour",7);
            saved_minute=App.sharedPreferences.getInt("minute",30);
            if(saved_hour<10){
                textViewHour.setText("0"+saved_hour);
            }
            else {
                textViewHour.setText(saved_hour+"");
            }
            if(saved_minute<10){
                textViewMinute.setText("0"+saved_minute);
            }
            else {
                textViewMinute.setText(saved_minute+"");
            }
        }
    }

}
