package ru.meizu.m9.r00t;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.*;

public class BootUpService extends Service {

    private final CommonExec commonExec = new CommonExec();

	public BootUpService() {
		// TODO Auto-generated constructor stub
	}

	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.i("m9.r00t", "Received start id " + startId + ": " + intent);

		copyfile("su");
		copyfile("busybox");
		copyfile("levitator");
		copyfile("local.sh");

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
		// TODO Auto-generated method stub
		return null;
	}

	private void RunExploit() {

		String basedir = null;

		try {
			basedir = getBaseContext().getFilesDir().getAbsolutePath();
		} catch (Exception e) {
		}

		String output = commonExec.exec(basedir + "/" + "levitator");

	}

	private void NotifyUser() {

		Context context = getApplicationContext();
		CharSequence text = "Now you have r00t rights. Service stopped.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();

	}

	// Executes UNIX command.


	private void copyfile(String file) {
		String basedir = null;
		String of = file;
		File f = new File(of);

		try {
			basedir = getBaseContext().getFilesDir().getAbsolutePath();
		} catch (Exception e) {
		}

		if (!f.exists()) {
			try {
				InputStream in = getAssets().open(file);
				FileOutputStream out = getBaseContext().openFileOutput(of,
						MODE_PRIVATE);

				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				in.close();
				Runtime.getRuntime().exec("chmod 755 " + basedir + "/" + of);
			} catch (IOException e) {
			}
		}
	}

}
