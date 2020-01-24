package com.javadi92.smartsilent.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.javadi92.smartsilent.alarm.AlarmHelper;
import com.javadi92.smartsilent.utils.App;

public class MyReciever extends BroadcastReceiver {

    AudioManager audioManager;
    AlarmHelper alarmHelper;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equalsIgnoreCase("com.javadi92.smartsilent")){

            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            if(audioManager.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE ||
                audioManager.getRingerMode()==AudioManager.RINGER_MODE_SILENT){
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
            if(alarmHelper!=null){
                alarmHelper.cancelAlarm();
            }
            else {
                alarmHelper=new AlarmHelper(context.getApplicationContext());
                alarmHelper.cancelAlarm();
            }
            App.sharedPreferences.edit().putBoolean("enabled",false).commit();
        }
    }
}
