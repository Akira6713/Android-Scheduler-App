package Activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import Adapters.MyListAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "channel ID";
    private ViewPager viewPager;
    public static boolean COME_FROM = false;
    AlarmManager alarmManager;
    Intent intent;
    PendingIntent broadcast;
    private NotificationManager notificationManager;
    private int cyear;
    private int cmonth;
    private int cdateOfMonth;
    java.util.Calendar calendar;
    String aDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        MyListAdapter adapter = new MyListAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        calendar = java.util.Calendar.getInstance();
        cyear = calendar.get(Calendar.YEAR);
        cmonth = calendar.get(Calendar.MONTH);
        cdateOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        aDate = new StringBuilder().append(cmonth+1).append('/').append(cdateOfMonth).append('/').append(cyear).toString();
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyListAdapter adapter = new MyListAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        createNotification();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        Drawable icon1 = menu.getItem(0).getIcon();
        icon1.mutate();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        LinearLayout linearLayout = new LinearLayout(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (20 * scale + 0.5f);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);

        final RadioButton radioButton1 = new RadioButton(this);
        radioButton1.setText("Add Term");
        radioGroup.addView(radioButton1);

        RadioButton radioButton2 = new RadioButton(this);
        radioButton2.setText("Add Course");
        radioGroup.addView(radioButton2);
        linearLayout.addView(radioGroup);

        AlertDialog dialog = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle).setTitle("Select Action").setView(linearLayout)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(radioButton1.isChecked()){
                            Intent intent1 = new Intent(MainActivity.this, AddTermActivity.class);
                            startActivity(intent1);
                        }else{
                            Intent intent1 = new Intent(MainActivity.this, AddCourseActivity.class);
                            startActivity(intent1);
                        }
                    }
                }).setNegativeButton("Cancel", null).create();
        dialog.show();
        return super.onOptionsItemSelected(item);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            String description = "channel desc";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    private void createNotification(){

        SharedPreferences settings = getSharedPreferences("ASSESSMENT",MODE_PRIVATE);
        if(settings.contains("assessment")){
            Set<String> set = settings.getStringSet("assessment", null);
            if( set != null){
                if(set.size() > 0){
                    for(String value : set){
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Date date = null;
                        try {
                            date = sdf.parse(value);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            if(value.equals(aDate)){
                                createAlarm(cal);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }


        settings = getSharedPreferences("COURSE",
                MODE_PRIVATE);
        if(settings.contains("courses")){
            Set<String> set1 = settings.getStringSet("courses", null);
            if(set1 != null ){
                if(set1.size() > 0){
                    for(String value : set1){
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Date date = null;
                        try {
                            date = sdf.parse(value);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            if(value.equals(aDate)){
                                createAlarm(cal);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }

    }

    private void createAlarm(Calendar calendar) {
        COME_FROM = true;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(getApplicationContext(),MyNotificationPublisher.class);
        broadcast = PendingIntent.getBroadcast(this,100, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,now.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+4, broadcast);
    }


}
