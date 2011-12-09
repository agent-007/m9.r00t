package ru.meizu.m9.r00t;

import android.app.Activity;
import android.util.Log;

import java.io.*;


public class CommonTasks extends Activity {
    public CommonTasks() {
    }

    // Executes UNIX command.
    public String exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int read;
            char[] buffer;
            buffer = new char[4096];
            StringBuffer output;
            output = new StringBuffer();
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




}