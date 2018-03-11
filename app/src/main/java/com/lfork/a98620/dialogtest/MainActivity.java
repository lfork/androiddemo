package com.lfork.a98620.dialogtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {

    private static final String TAG = "MainActivity";

    private StringBuffer date = new StringBuffer();

    private StringBuffer time = new StringBuffer();

    private TextView currentDate;

    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDateTime();
        currentDate = findViewById(R.id.textView4);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.
                    this);
            dialog.setTitle("当前状态: 开")
                    .setMessage("预计关门时间\n" + "2018年3月11日15:41:52")
                    .setCancelable(false)
                    .setPositiveButton("更新开关门信息", (dialog12, which) -> {
                        setDoorStatus();
                    })
                    .setNegativeButton("OK", (dialog1, which) -> {
                    });

            dialog.show();

        });
    }

    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
    }

    private void setDoorStatus() {
        dateSetting();
//        timeSetting();  这个方法在dateSetting里面进行连续调用。

        time.append(hour).append(":").append(minute);
        String str = date + " " + time;

        Log.d(TAG, "当前时间 " + str);
        currentDate.setText(str);
        //调用后台操作 ，向服务器提交处理结果

    }

    private void dateSetting() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.
                this);
        dialog.setTitle("当前状态: 开")
                .setCancelable(false)
                .setPositiveButton("下一步", (dialog12, which) -> {
                    if (date.length() > 0) { //清除上次记录的日期
                        date.delete(0, date.length());
                    }
                    date.append(year).append(".").append(month).append(".").append(day);
                    timeSetting();
                })
                .setNegativeButton("取消", (dialog1, which) -> {
                });
        View dialogView1 = View.inflate(getApplicationContext(), R.layout.mainview_index_doorstatus_date_setting, null);
        DatePicker datePicker = dialogView1.findViewById(R.id.datePicker);
        datePicker.init(year, month - 1, day, this);
        datePicker.setCalendarViewShown(false);
        dialog.setView(dialogView1);
        dialog.show();
    }

    private void timeSetting() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.
                this);
        dialog.setTitle("当前状态: 开")
                .setCancelable(false)
                .setPositiveButton("完成", (dialog12, which) -> {
                    if (time.length() > 0) { //清除上次记录的日期
                        time.delete(0, time.length());
                    }
                })
                .setNegativeButton("上一步", (dialog1, which) -> {
                    dateSetting();
                });

        View dialogView2 = View.inflate(getApplicationContext(), R.layout.mainview_index_doorstatus_time_setting, null);
        TimePicker timePicker = dialogView2.findViewById(R.id.timePicker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        timePicker.setIs24HourView(true); //设置24小时制
        timePicker.setOnTimeChangedListener(this);
        dialog.setView(dialogView2);
        dialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Log.d(TAG, "onDateChanged: ");
        this.hour = hourOfDay;
        this.minute = minute;
    }
}
