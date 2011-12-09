package ru.meizu.m9.r00t;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.util.Log.e;

public class MainActivity extends Activity {

    private TextView outputView;
    private Handler handler = new Handler();

    private final CommonExec commonExec = new CommonExec();

    /**
     * Called when the activity is first created.
     */
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

        PrepareFiles();
    }

    private void PrepareFiles() {
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

    private void copyfile(String file) {
        String basedir = null;
        String of;
        of = file;
        File f = new File(of);

        try {
            basedir = getBaseContext().getFilesDir().getAbsolutePath();
        } catch (Exception e) {
            Log.e("m9.r00t: copyfile", "Can't find basedir");
        }

        if (!f.exists()) {
            try {
                InputStream in = getAssets().open(file);
                FileOutputStream out = getBaseContext().openFileOutput(of,
                        MainActivity.MODE_PRIVATE);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
                Runtime.getRuntime().exec("chmod 755 " + basedir + "/" + of);
            } catch (IOException e) {
                Log.e("m9.r00t: copyfile", "Can't open file" + f);
            }
        }
    }


    private void RunExploit() {
        String basedir = getBasedir();
        String output;
        output = commonExec.exec(basedir + "/" + "levitator");
        output(output);
    }

    private String getBasedir() {
        String basedir = null;
        try {
            basedir = getBaseContext().getFilesDir().getAbsolutePath();
        } catch (Exception e) {
            e("m9.r00t: RunExploit", "Can't find basedir");
        }
        return basedir;
    }


    private void RunUnroot() {
        String basedir = getBasedir();
        String output = commonExec.exec(basedir + "/" + "unroot.sh");
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