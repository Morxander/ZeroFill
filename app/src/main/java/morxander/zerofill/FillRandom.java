package morxander.zerofill;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by morxander on 4/25/16.
 */
public class FillRandom extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Runtime.getRuntime().exec("dd if=/dev/urandom of=/mnt/sdcard/random");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
