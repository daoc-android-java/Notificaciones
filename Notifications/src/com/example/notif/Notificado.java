package com.example.notif;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class Notificado extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView tv = new TextView(this);
		tv.setText("Fui llamado desde la notificación");
		tv.setTextSize(40);
		tv.setTextColor(Color.BLUE);
		setContentView(tv);

	}

}
