package nexustech.com.ng.nexusquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Entry extends AppCompatActivity {
    Button start;
    EditText ip, sch;
    ImageView hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

        hide = (ImageView) findViewById(R.id.hide);
        ip = (EditText) findViewById(R.id.ipaddress);
        start = (Button) findViewById(R.id.start);
        sch = (EditText) findViewById(R.id.school);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String school = sch.getText().toString().trim();
                if (school.isEmpty()) {
                    sch.setError("Insert the name of your School");
                    Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    mVibrator.vibrate(500);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sch.setError(null);
                        }
                    }, 1500);
                    // Toast.makeText(Entry.this, "Insert the name of your School", Toast.LENGTH_SHORT).show();
                } else {
                    String ipad = ip.getText().toString().trim();
                    RadioGroup rg = (RadioGroup) findViewById(R.id.networkgroup);
                    RadioButton local = (RadioButton) findViewById(R.id.rb_local);
                    RadioButton external = (RadioButton) findViewById(R.id.rb_external);
                    if (ipad.isEmpty()) {
                        if (rg.getCheckedRadioButtonId() == local.getId()) {
                            ipad = local.getText().toString().trim();
                        }
                        if (rg.getCheckedRadioButtonId() == external.getId()) {
                            ipad = external.getText().toString().trim();
                        }

                    }
                    SharedPref.getInstance(Entry.this).setIP(ipad);
                    SharedPref.getInstance(Entry.this).setSch(school);
                    startActivity(new Intent(Entry.this, Picker.class));

                }

            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ip.getVisibility() == View.VISIBLE) {
                    ip.setVisibility(View.GONE);
                    hide.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
                } else {
                    ip.setVisibility(View.VISIBLE);
                    hide.setImageResource(R.drawable.ic_close_black_24dp);
                }

            }
        });
    }
}
