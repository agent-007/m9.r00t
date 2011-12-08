package ru.meizu.m9.r00t;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import java.io.*;

import ru.meizu.m9.r00t.R;

public class MainActivity extends Activity {

	private TextView outputView;
	private Button localRunButton;
	private Button localDisableButton;
	private Handler handler = new Handler();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		outputView = (TextView) findViewById(R.id.outputView);

		localRunButton = (Button) findViewById(R.id.localRunButton);
		localRunButton.setOnClickListener(onLocalRunButtonClick);

		localDisableButton = (Button) findViewById(R.id.localDisableButton);
		localDisableButton.setOnClickListener(onLocalDisableButtonClick);

		copyfile("su");
		copyfile("busybox");
		copyfile("levitator");
		copyfile("local.sh");
		copyfile("unroot.sh");

	}

	private OnClickListener onLocalRunButtonClick = new OnClickListener() {
		public void onClick(View v) {
			RunExploit();
		}
	};

	private OnClickListener onLocalDisableButtonClick = new OnClickListener() {
		public void onClick(View v) {
			RunUnroot();
		}
	};

	private void RunExploit() {

		String basedir = null;

		try {
			basedir = getBaseContext().getFilesDir().getAbsolutePath();
		} catch (Exception e) {
		}

		String output = exec(basedir + "/" + "levitator");
		output(output);
	}

	private void RunUnroot() {
		String basedir = null;

		try {
			basedir = getBaseContext().getFilesDir().getAbsolutePath();
		} catch (Exception e) {
		}
		String output = exec(basedir + "/" + "unroot.sh");
		output(output);
	}

	// Executes UNIX command.
	private String exec(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((read = reader.read(buffer)) > 0) {
				output.append(buffer, 0, read);
			}
			reader.close();
			process.waitFor();
			return output.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void copyfile(String file) {
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

	private void output(final String str) {
		Runnable proc = new Runnable() {
			public void run() {
				outputView.setText(str);
			}
		};
		handler.post(proc);
	}

}