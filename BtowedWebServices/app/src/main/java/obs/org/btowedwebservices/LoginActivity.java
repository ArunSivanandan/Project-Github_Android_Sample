package obs.org.btowedwebservices;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import obs.org.btowedwebservices.Fragments.LoginFragment;
import obs.org.btowedwebservices.Network.WebServiceClass;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LogFragment{

    TextView text_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        text_error = (TextView)findViewById(R.id.text_error);
        text_error.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
        //overridePendingTransition(R.anim.open_scale, R.anim.close_translate);
    }

    @Override
    public void errorMessage(String frag_user, String frag_pass) {
        //LoginFragment loginFragment = (LoginFragment)getSupportFragmentManager().findFragmentById(R.id.login_fragment);
        if(WebServiceClass.status_code != 200) {

            text_error.setText(R.string.alertmessage_invalid_username_password_message);
            animatedErrorMessage();

        } else if(!frag_user.equals(WebServiceClass.email)) {

            text_error.setText(R.string.alertmessage_invalid_username_message);
            animatedErrorMessage();

        } else if(!frag_pass.equals(WebServiceClass.password)){

            text_error.setText(R.string.alertmessage_invalid_password_message);
            animatedErrorMessage();

        }
    }

    public void animatedErrorMessage(){
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(800);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(in);
        text_error.setAnimation(animationSet);
        text_error.setVisibility(View.VISIBLE);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_up);
                text_error.startAnimation(animation1);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down);
                        text_error.startAnimation(animation2);
                        text_error.setVisibility(View.INVISIBLE);

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

                                text_error.startAnimation(as);
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
