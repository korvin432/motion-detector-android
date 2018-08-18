package com.jjoe64.motiondetection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class OpenActivity extends AppCompatActivity implements View.OnClickListener{

    EditText check, linec, luma;
    Button apply, start;
    SeekBar zoom;

    private int check_interval;
    private int leniency;
    private int min_luma;
    private static int new_zoom;

    SharedPreferences sPref;
    final String SAVED_CHECK = "saved_check";
    final String SAVED_LINEC = "saved_linec";
    final String SAVED_LUMA = "saved_luma";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        check = (EditText) findViewById(R.id.check);
        linec = (EditText) findViewById(R.id.linec);
        luma = (EditText) findViewById(R.id.luma);

        apply = (Button) findViewById(R.id.apply);
        start = (Button) findViewById(R.id.start);

        zoom = (SeekBar) findViewById(R.id.zoom);

        apply.setOnClickListener(this);
        start.setOnClickListener(this);

        loadText();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply:

                check_interval = Integer.parseInt(check.getText().toString());
                leniency = Integer.parseInt(linec.getText().toString());
                min_luma = Integer.parseInt(luma.getText().toString());
                new_zoom = zoom.getProgress();

                break;

            case R.id.start:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("check_interval", check_interval);
                intent.putExtra("leniency", leniency);
                intent.putExtra("min_luma", min_luma);
                startActivity(intent);
                break;
        }
    }

    public int getNew_zoom(){
        return new_zoom;
    }

    private void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedCheck = sPref.getString(SAVED_CHECK, "400");
        String savedLinec = sPref.getString(SAVED_LINEC, "20");
        String savedLuma = sPref.getString(SAVED_LUMA, "1000");
        check.setText(savedCheck);
        linec.setText(savedLinec);
        luma.setText(savedLuma);
    }

    private void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_CHECK, check.getText().toString());
        ed.putString(SAVED_LUMA, luma.getText().toString());
        ed.putString(SAVED_LINEC, linec.getText().toString());
        ed.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }
}
