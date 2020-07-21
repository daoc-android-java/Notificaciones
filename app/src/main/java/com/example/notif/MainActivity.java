package com.example.notif;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	private final int NOTIF_BUTTON_ID = 1;
	private final int NOTIF_SEEKBAR_ID = 2;
	private final int NOTIF_TOGGLE_ID = 3;
	
	private final int REQ_CODE = 2;
	private int counter = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setOnSeekBarChangeListener(sbChListener);

		createNotificationChannel();
	}
	
	//Toast con layout personalizado
	public void miToast(View v) {
		View layout = getLayoutInflater().inflate(R.layout.mi_toast, null);
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("Esta es mi tostada");

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);//posición en la pantalla
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);//caga el layout en el toast
		toast.show();
	}

	private void createNotificationChannel() {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = "CanalPrueba";
			String description = "Canal de pruebas";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel("IdPrueba", name, importance);
			channel.setDescription(description);
			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}


	public void notificar(View view) {
		//http://developer.android.com/guide/topics/ui/notifiers/notifications.html
		
		//http://developer.android.com/reference/android/app/Notification.Builder.html
		Notification.Builder notifB =  new Notification.Builder(this, "IdPrueba");
		notifB.setSmallIcon(R.drawable.ic_stat_name);
		notifB.setContentTitle("Botón");
		notifB.setContentText("Has sido notificado " + (counter++) + " veces.");
		notifB.setAutoCancel(true);//la notificación desaparece al darle click
		
		//http://developer.android.com/reference/android/app/PendingIntent.html
		Intent intent = new Intent(this, Notificado.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notifB.setContentIntent(pendingIntent);
		
		//http://developer.android.com/reference/android/app/Notification.html
		Notification notif = notifB.build();
		
		//http://developer.android.com/reference/android/app/NotificationManager.html
		NotificationManager notifMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notifMgr.notify(NOTIF_BUTTON_ID, notif);		
	}
	
	OnSeekBarChangeListener sbChListener = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			Notification.Builder notifB =  new Notification.Builder(getApplicationContext(),"IdPrueba");
			notifB.setSmallIcon(R.drawable.ic_stat_name);
			notifB.setContentTitle("SeekBar");
			notifB.setContentText("Progreso: " + progress);
			notifB.setAutoCancel(true);
			
			notifB.setProgress(10, progress, false);
			
			Intent intent = new Intent(getApplicationContext(), Notificado.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			notifB.setContentIntent(pendingIntent);

			Notification notif = notifB.build();
			
			NotificationManager notifMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			notifMgr.notify(NOTIF_SEEKBAR_ID, notif);			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {}
		
	};
	
	public void progreso(View view) {
		ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton1);
		
		Notification.Builder notifB =  new Notification.Builder(getApplicationContext(),"IdPrueba");
		notifB.setSmallIcon(R.drawable.ic_stat_name);
		notifB.setContentTitle("Toggle");
		notifB.setContentText("Trabajando !!!");
		notifB.setAutoCancel(true);
		
		if(tb.isChecked()) {
			notifB.setProgress(0, 0, true);
		} else {
			notifB.setProgress(0, 0, false);
			notifB.setContentText("Trabajo listo !!!");
		}
		
		
		Intent intent = new Intent(getApplicationContext(), Notificado.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notifB.setContentIntent(pendingIntent);

		Notification notif = notifB.build();
		
		NotificationManager notifMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notifMgr.notify(NOTIF_TOGGLE_ID, notif);		
	}
	
}
