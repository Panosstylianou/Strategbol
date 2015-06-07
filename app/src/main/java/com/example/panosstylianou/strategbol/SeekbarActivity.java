package com.example.panosstylianou.strategbol;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by Damalas on 07/06/15.
 */

public class SeekbarActivity extends BaseActivity {

    private SeekBar volumeControl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar);

        volumeControl = (SeekBar) findViewById(R.id.volume_bar);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SeekbarActivity.this, "seek bar progress:" + progressChanged,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
