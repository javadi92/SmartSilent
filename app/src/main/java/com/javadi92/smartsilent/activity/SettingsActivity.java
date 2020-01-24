package com.javadi92.smartsilent.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.javadi92.smartsilent.R;
import com.javadi92.smartsilent.utils.App;

public class SettingsActivity extends AppCompatActivity {

    Button buttonConfirm;
    NumberPicker numberPickerHour,numberPickerMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        buttonConfirm=findViewById(R.id.button_confirm);
        numberPickerHour=findViewById(R.id.numberPicker_hour);
        numberPickerMinute=findViewById(R.id.numberPicker_minute);

        numberPickerHour.setMaxValue(23);
        numberPickerHour.setMinValue(0);

        numberPickerMinute.setMaxValue(59);
        numberPickerMinute.setMinValue(0);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher_background);
        setTitle("تنظیمات");
        setTitle("تنظیمات");

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour=numberPickerHour.getValue();
                int minute=numberPickerMinute.getValue();
                App.sharedPreferences.edit().putInt("hour",hour).commit();
                App.sharedPreferences.edit().putInt("minute",minute).commit();
                setResult(RESULT_OK);
                Toast.makeText(SettingsActivity.this, "زمان با موفقیت دخیره شد", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
