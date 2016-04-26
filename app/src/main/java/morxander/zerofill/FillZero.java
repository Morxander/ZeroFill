package morxander.zerofill;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by morxander on 4/25/16.
 */
public class FillZero extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Runtime.getRuntime().exec("dd if=/dev/zero of=/mnt/sdcard/zero");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
