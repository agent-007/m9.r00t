package ru.meizu.m9.r00t;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import static android.util.Log.e;

public class MainActivity extends Activity {

	private TextView outputView;
    private Handler handler = new Handler();
    private final CommonTasks commonTasks = new CommonTasks();

    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		outputView = (TextView) findViewById(R.id.outputView);

        Button localRunButton;
        localRunButton = (Button) findViewById(R.id.localRunButton);
        localRunButton.setOnClickListener(onLocalRunButtonClick);

        Button localDisableButton;
        localDisableButton = (Button) findViewById(R.id.localDisableButton);
        localDisableButton.setOnClickListener(onLocalDisableButtonClick);

        commonTasks.copyfile("su");
        commonTasks.copyfile("busybox");
        commonTasks.copyfile("levitator");
        commonTasks.copyfile("local.sh");
        commonTasks.copyfile("unroot.sh");

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
            e("m9.r00t: RunExploit", "Can't find basedir");
        }

		String output;
        output = commonTasks.exec(basedir + "/" + "levitator");
        output(output);
	}



    private void RunUnroot() {
		String basedir = null;

		try {
			basedir = getBaseContext().getFilesDir().getAbsolutePath();
		} catch (Exception e) {
            e("m9.r00t: RunUnroot", "Can't find basedir");
		}
		String output = commonTasks.exec(basedir + "/" + "unroot.sh");
		output(output);
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