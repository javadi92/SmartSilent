package com.javadi92.smartsilent.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.javadi92.smartsilent.reciever.MyReciever;

import java.util.Calendar;

public class AlarmHelper {

    private Context context;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;

    public AlarmHelper(Context context){
        this.context=context;
    }

    public void setAlarm(int hour,int minute){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,minute);
        if(calendar.getTimeInMillis()< System.currentTimeMillis()){
            calendar.add(Calendar.DATE,1);
        }
        alarmManager= (AlarmManager) context.getApplicationContext().getSystemService(context.getApplicationContext().ALARM_SERVICE);
        intent=new Intent(context, MyReciever.class);
        intent.setAction("com.javadi92.smartsilent");
        pendingIntent=PendingIntent.getBroadcast(context.getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if(Build.VERSION.SDK_INT>=23){

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }
        else{
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }
    }

    public void cancelAlarm(){
        if(alarmManager!=null){
            alarmManager= (AlarmManager) context.getApplicationContext().getSystemService(context.getApplicationContext().ALARM_SERVICE);
            intent=new Intent(context, MyReciever.class);
            intent.setAction("com.javadi92.smartsilent");
            pendingIntent=PendingIntent.getBroadcast(context.getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
    }

}
