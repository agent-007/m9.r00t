package ru.meizu.m9.r00t;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver {

	public BootUpReceiver() {

	}

	@Override
	public void onReceive(Context context, Intent intent) {

		Intent service = new Intent(context, BootUpService.class);
		service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(service);
		context.stopService(service);
	}

}
