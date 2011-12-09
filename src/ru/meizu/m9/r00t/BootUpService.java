package ru.meizu.m9.r00t;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import static android.util.Log.e;

public class BootUpService extends Service {

    private final CommonTasks commonTasks = new CommonTasks();

	public BootUpService() {
	
	}

	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.i("m9.r00t: onStartCommand", "Received start id " + startId + ": " + intent);

		commonTasks.copyfile("su");
        commonTasks.copyfile("busybox");
        commonTasks.copyfile("levitator");
        commonTasks.copyfile("local.sh");

		RunExploit();

		return startId;

	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDestroy() {
		NotifyUser();
	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	private void RunExploit() {

		String basedir = null;

		try {
			basedir = getBaseContext().getFilesDir().getAbsolutePath();
		} catch (Exception e) {
            e("m9.r00t: RunExploit", "Can't find basedir");
		}

		@SuppressWarnings("unused")
		String output = commonTasks.exec(basedir + "/" + "levitator");

	}

	private void NotifyUser() {

		Context context = getApplicationContext();
		CharSequence text = "Now you have r00t rights. Service stopped.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();

	}

}
