package ru.meizu.m9.r00t;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.util.Log.e;

public class BootUpService extends Service {

    private final CommonExec commonExec = new CommonExec();

    public BootUpService() {
        // TODO Auto-generated constructor stub
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("m9.r00t", "Received start id " + startId + ": " + intent);

        PrepareFiles();
        RunExploit();

        Log.i("m9.r00t", "pwned");

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

    private String getBasedir() {
        String basedir = null;
        try {
            basedir = getBaseContext().getFilesDir().getAbsolutePath();
        } catch (Exception e) {
            e("m9.r00t: RunExploit", "Can't find basedir");
        }
        return basedir;
    }

    private void PrepareFiles() {
        copyfile("su");
        copyfile("busybox");
        copyfile("levitator");
        copyfile("local.sh");
        copyfile("unroot.sh");
    }

    private void RunExploit() {
        String basedir;
        basedir = getBasedir();
        commonExec.exec(basedir + "/" + "levitator");
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
        String basedir;
        basedir = getBasedir();
        File f;
        f = new File(file);

        if (!f.exists()) {
            try {
                InputStream in = getAssets().open(file);
                FileOutputStream out = getBaseContext().openFileOutput(file,
                        MODE_PRIVATE);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
                Runtime.getRuntime().exec("chmod 755 " + basedir + "/" + file);
            } catch (IOException e) {
            }
        }
    }

}
