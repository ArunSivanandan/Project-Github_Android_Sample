package obs.org.btowedwebservices.Network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import obs.org.btowedwebservices.Constants.ConstantMethods;
import obs.org.btowedwebservices.Constants.ConstantVariables;
import obs.org.btowedwebservices.Fragments.LoginFragment;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebServiceClass extends ConstantVariables implements LoginFragment.InputFragment, LoginFragment.LogFragment {

    OkHttpClient okHttpClientToken, okHttpClientLogin;
    RequestBody requestBodyToken, requestBodyLogin;
    Request requestToken, requestLogin;
    Response responseToken, responseLogin;
    JSONObject jsonObjectToken, jsonObjectLogin;

    public static String email = "";
    public static String password = "";
    public static String tokenData, loginData, token_type, access_token;
    public static int status_code = 0;
    public static String mail, pass;

    public void postRequest() {
        errorMessage(mail, pass);
        new Token().execute();
        new Login().execute();

    }

    @Override
    public void errorMessage(String frag_user, String frag_pass) {
        mail = pass;
    }

    public class Token extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                okHttpClientToken = new OkHttpClient();

                //Seting params using requestbody and formbody.builder
                requestBodyToken = new FormBody.Builder()
                        .addEncoded("username", PARAMS_USERNAME)
                        .addEncoded("password", PARAMS_PASSWORD)
                        .addEncoded("grant_type", PARAMS_GRANT_TYPE)
                        .build();

                //setting (url header and request type)
                requestToken = new Request.Builder()
                        .url(TOKEN_URL)
                        .addHeader("Authorization", "Basic c3VuYm94OnN1bmJveA==")
                        .post(requestBodyToken)
                        .build();

                responseToken = okHttpClientToken.newCall(requestToken).execute();
                tokenData = responseToken.body().string();
                Log.e("Token Response : ", tokenData);
                jsonObjectToken = new JSONObject(tokenData);
                token_type = jsonObjectToken.getString("token_type");
                access_token = jsonObjectToken.getString("access_token");


                // loginPostRequest();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    public class Login extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {

            try{

            okHttpClientLogin = new OkHttpClient();

                new ConstantMethods().StatusConditon();
                HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGIN_URL).newBuilder();
                urlBuilder.addQueryParameter("email", email_input);
                urlBuilder.addQueryParameter("password", password_input);
                String paramData = urlBuilder.build().toString();

                requestLogin = new Request.Builder()
                    .addHeader("Authorization", token_type+" "+access_token)
                    .url(paramData)
                    .build();

                Log.e("Debug ", paramData);
            responseLogin =  okHttpClientLogin.newCall(requestLogin).execute();
            loginData = responseLogin.body().string();
                Log.e("Token Response : ", loginData);

            jsonObjectLogin = new JSONObject(loginData);
                JSONObject statusDataObject = jsonObjectLogin.getJSONObject("status");
                status_code = statusDataObject.getInt("status");

                JSONObject userDataObject = jsonObjectLogin.getJSONObject("user");
                email = userDataObject.getString("email");
                password = userDataObject.getString("password");

                Log.e("status : ", String.valueOf(status_code));
                Log.e("Email ", email);
                Log.e("password ", password);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return 1;
        }
    }
}