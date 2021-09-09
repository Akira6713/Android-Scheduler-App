package Activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.c196.R;

public class MyNotificationPublisher extends BroadcastReceiver {

    int notifyID = 1;
    String CHANNEL_ID = "my_channel_01";
    String title = "New Course Alert!";
    String descirption = "Check the course notification.";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent;
        if(CourseDetail.COME_FROM){
            notificationIntent = new Intent(context, MainActivity.class);
            CourseDetail.COME_FROM = false;
        }else{
            notificationIntent = new Intent(context, AddCourseActivity.class);

        }
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(CourseDetail.class);
        taskStackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        CharSequence name = "my_channel_01";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Notification notification = builder.setContentTitle("Course Notification")
                .setContentText(descirption)
                .setTicker(title)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentIntent(pendingIntent).build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(notifyID, notification);
    }
}

