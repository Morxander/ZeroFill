package morxander.zerofill;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;


public class MainActivityFragment extends Fragment {

    // The Views
    private Button bt_start, bt_clean;
    private TextView txt_free_space, txt_status;
    private RadioButton radio_zero, radio_random;

    // Filling objects
    private FillZero fillZero;
    private FillRandom fillRandom;
    private Handler handler = new Handler();
    private Runnable runnable;

    // This var will hold the type of filling
    private int filling_type;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Init the views of the fragment
        initViews(rootView);
        // Set the onclick listeners on the buttons
        setOnClick();
        return rootView;
    }


    private void initViews(View rootView) {
        bt_start = (Button) rootView.findViewById(R.id.bt_start);
        bt_clean = (Button) rootView.findViewById(R.id.bt_clear);
        txt_free_space = (TextView) rootView.findViewById(R.id.txt_free_space);
        txt_status = (TextView) rootView.findViewById(R.id.txt_status);
        radio_zero = (RadioButton) rootView.findViewById(R.id.radio_zero);
        radio_random = (RadioButton) rootView.findViewById(R.id.radio_random);
        updateTextView();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateTextView();
                handler.postDelayed(this, 1000);
            }
        };
    }

    private void setOnClick() {
        // Setting OnClickListener on the start button
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_zero.isChecked()) {
                    // If zero is selected
                    fillZero = new FillZero();
                    fillZero.doInBackground(null);
                    filling_type = 0;
                } else if (radio_random.isChecked()) {
                    // If random is selected
                    fillRandom = new FillRandom();
                    fillRandom.doInBackground(null);
                    filling_type = 1;
                }
                // Changing status
                txt_status.setText(R.string.filling);
                // Disable start button
                bt_start.setEnabled(false);
            }
        });
        // Setting OnClickListener on the clean button
        bt_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_status.setText(R.string.cleaning);
                if (filling_type == 0) {
                    // If zero was selected
                    try {
                        // Delete the filled file
                        Runtime.getRuntime().exec("rm /mnt/sdcard/zero");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // If random was selected
                    try {
                        // Delete the filled file
                        Runtime.getRuntime().exec("rm /mnt/sdcard/random");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // Changing the status
                txt_status.setText(R.string.waiting);
                // Enable the start button
                bt_start.setEnabled(true);
                bt_clean.setEnabled(false);
            }
        });
    }

    // This method will run every second to update the textview
    // And also to check if the filling finished
    private void updateTextView() {
        // Updating the empty space text view
        txt_free_space.setText(getString(R.string.free_space) + String.valueOf((Utilities.getFreeExternalMemory() / 1024) / 1024) + getString(R.string.mb));
        // If the filling is done
        if (Utilities.getFreeExternalMemory() == 0) {
            // Changing the status
            txt_status.setText(R.string.finished);
            // Enable the clean button
            bt_clean.setEnabled(true);
        }
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void onResume() {
        handler.postDelayed(runnable, 1000);
        super.onResume();
    }
}
