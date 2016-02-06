package obs.org.samplebtowedapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import obs.org.samplebtowedapp.Constants.ConstantVariables;

public class ForgotPassword extends AppCompatActivity {

    TextView text_error_forgot;
    EditText forgot_pass_edit;
    ImageView back_image, logout_image;
    Button button_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_screen);

        text_error_forgot = (TextView)findViewById(R.id.text_error_forgot);
        text_error_forgot.setVisibility(View.INVISIBLE)
        ;
        forgot_pass_edit = (EditText)findViewById(R.id.forgot_pass_edit);
        back_image = (ImageView)findViewById(R.id.back_image);
        logout_image = (ImageView)findViewById(R.id.logout_image);
        button_submit = (Button)findViewById(R.id.button_submit);

        ConstantVariables.words = forgot_pass_edit.getText().toString();
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(back_intent);
                overridePendingTransition(R.anim.open_scale, R.anim.close_translate);
            }
        });

        logout_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout_intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(logout_intent);
                overridePendingTransition(R.anim.open_scale, R.anim.close_translate);
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validEmailCheck();
                progressDialog();
                if(ConstantVariables.check_at.equals(false) && ConstantVariables.check_dot.equals(false)) {

                    forgot_pass_edit.setBackgroundResource(R.drawable.input_feild_error);
                    ErrorMessage();

                } else if(forgot_pass_edit.getText().toString(). equals("btowed1007@gmail.com")){

                    Toast.makeText(getApplicationContext(),R.string.toast_message,Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void validEmailCheck(){
        //do this in a seperate method.
        for(int i=0; i<ConstantVariables.words.length(); i++) {

            ConstantVariables.letters = ConstantVariables.words.charAt(i);
            if (ConstantVariables.letters == ConstantVariables.at){

                ConstantVariables.check_at = true;

            }
            if(ConstantVariables.letters == ConstantVariables.dot) {

                ConstantVariables.check_dot = true;
           
            }
        }
    }

    public void progressDialog(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loging In");
        progressDialog.show();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                progressDialog.dismiss();
                timer.cancel();
            }
        },1000);
    }

    public void ErrorMessage() {
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(800);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(in);
        text_error_forgot.setAnimation(animationSet);
        text_error_forgot.setVisibility(View.VISIBLE);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_up);
                text_error_forgot.startAnimation(animation1);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down);
                        text_error_forgot.startAnimation(animation2);
                        text_error_forgot.setVisibility(View.INVISIBLE);

                        animation2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                                final Animation out = new AlphaAnimation(1.0f, 0.0f);
                                out.setDuration(800);

                                AnimationSet as = new AnimationSet(true);
                                as.addAnimation(out);

                                text_error_forgot.startAnimation(as);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
