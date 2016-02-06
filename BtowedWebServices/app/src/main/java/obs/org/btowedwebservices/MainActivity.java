package obs.org.btowedwebservices;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2700);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
