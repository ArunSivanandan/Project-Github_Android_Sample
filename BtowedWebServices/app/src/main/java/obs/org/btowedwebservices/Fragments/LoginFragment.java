package obs.org.btowedwebservices.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import obs.org.btowedwebservices.Constants.ConstantMethods;
import obs.org.btowedwebservices.ForgotPassword;
import obs.org.btowedwebservices.Network.WebServiceClass;
import obs.org.btowedwebservices.R;

public class LoginFragment extends Fragment {

    TextView forgot_password_text;
    static EditText email_edit_text, password_edit_text;
    Button login_button;
    CheckBox remember_me_checkbox;
    String username, password2;
    Boolean checkbox_status;
    AlertDialog.Builder alertmessage;

    public interface LogFragment {
        void errorMessage(String frag_user, String frag_pass);
    }

    LogFragment fragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        forgot_password_text = (TextView)view.findViewById(R.id.forgot_password_text);
        email_edit_text = (EditText)view.findViewById(R.id.email_edit_text);
        password_edit_text = (EditText)view.findViewById(R.id.password_edit_text);
        login_button = (Button)view.findViewById(R.id.login_button);
        remember_me_checkbox = (CheckBox)view.findViewById(R.id.remember_me_checkbox);

        loadLoginInfo();
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceClass webServiceClass = new WebServiceClass();
                new ConstantMethods().checkInternetConnection(getActivity());
                webServiceClass.postRequest();
                try {
                    Thread.sleep(1600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog();
                saveLoginInfo();
            }
        });

        forgot_password_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot_pass_intent = new Intent(getActivity(), ForgotPassword.class);
                getActivity().startActivity(forgot_pass_intent);
                getActivity().overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
            }
        });
        return view;
    }

    public interface InputFragment {
        String email_input = email_edit_text.getText().toString();
        String password_input = password_edit_text.getText().toString();

    }

    //Saving Username and Password..
    public void saveLoginInfo(){

        if(WebServiceClass.status_code == 200) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SecurityInfoData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if(remember_me_checkbox.isChecked()) {

                editor.putString("Username_Key", email_edit_text.getText().toString());
                editor.putString("Password_Key", password_edit_text.getText().toString());
                editor.putBoolean("Checkbox_Key", remember_me_checkbox.isChecked());
                editor.commit();

            }else {

                editor.putString("Username_Key", "");
                editor.putString("Password_Key", "");
                editor.putBoolean("Checkbox_Key", remember_me_checkbox.isChecked());
                editor.commit();

            }

            //Intent comes here if needed.
            dialog();

        } else if (!email_edit_text.getText().toString().equals (WebServiceClass.email) && (!password_edit_text.getText().toString().equals (WebServiceClass.password))){

            email_edit_text.setBackgroundResource(R.drawable.input_feild_error);
            password_edit_text.setBackgroundResource(R.drawable.input_feild_error);
            buttons();

        } else if (!password_edit_text.getText().toString().equals (WebServiceClass.password)){

            password_edit_text.setBackgroundResource(R.drawable.input_feild_error);
            buttons();

        } else {

            email_edit_text.setBackgroundResource(R.drawable.input_feild_error);
            buttons();

        }
    }

    //Loading Username and Password..
    public void loadLoginInfo(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SecurityInfoData", Context.MODE_PRIVATE);

        //checking checkbox status before using it in if condition..
        checkbox_status = sharedPreferences.getBoolean("Checkbox_Key", Boolean.parseBoolean(""));

        //if checked load..
        if(checkbox_status){

            username = sharedPreferences.getString("Username_Key", "");
            password2 = sharedPreferences.getString("Password_Key", "");
            email_edit_text.setText(username);
            password_edit_text.setText(password2);
            remember_me_checkbox.setChecked(checkbox_status);

        } // if not checked set all as empty values..
        else {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            email_edit_text.setText("");
            password_edit_text.setText("");
            remember_me_checkbox.setChecked(checkbox_status);
            editor.clear();
            editor.commit();

        }
    }

    // For Dialog box messages this method is called..
    public void dialog() {

        alertmessage = new AlertDialog.Builder(getActivity());
        alertmessage.setTitle(R.string.alertmessage_title);
        alertmessage.setMessage(R.string.alertmessage_success);
        alertmessage.setPositiveButton(R.string.alertmessage_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        alertmessage.create();
        alertmessage.show();

    }

    public void progressDialog(){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loging In");
        progressDialog.show();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                progressDialog.dismiss();
                timer.cancel();
            }
        }, 2000);

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        try{
            fragmentListener = (LogFragment)activity;
        }catch (ClassCastException cce){
            throw new ClassCastException(activity.toString());
        }

    }
    public void buttons(){

        fragmentListener.errorMessage(email_edit_text.getText().toString(), password_edit_text.toString());

    }
}
