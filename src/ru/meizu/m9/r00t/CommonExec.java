package ru.meizu.m9.r00t;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommonExec {
    public CommonExec() {
    }

    String exec(String command) {

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
}