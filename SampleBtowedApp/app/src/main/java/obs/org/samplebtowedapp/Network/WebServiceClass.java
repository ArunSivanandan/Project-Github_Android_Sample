package obs.org.samplebtowedapp.Network;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import obs.org.samplebtowedapp.Constants.ConstantVariables;
import obs.org.samplebtowedapp.Fragments.LoginFragment;

// Deprecated and removed imports
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;


public class WebServiceClass extends ConstantVariables implements LoginFragment.InputFragment{


    public static String jsonTokenMessage, jsonLoginMessage;
    public static String token_type = "";
    public static String access_token = "";
    public static int status_code = 0;
    public static String mail;
    public static String password;
    public String tokenLines;
    public String loginLines;

    public void executeAsyncTask(){

        new TokenPost().execute();
        new LoginPost().execute();

    }

    //HTTP POST TOKEN..
    public class TokenPost extends AsyncTask{

        JSONObject jsonObject;

        @Override
        protected Object doInBackground(Object[] objects) {

            //Http Post starting here..
            try {

                //creating HttpClient
                HttpClient httpTokenClient = new DefaultHttpClient();
                //creating http post
                HttpPost httpTokenPost = new HttpPost(TOKEN_URL); //url of the token

                List<NameValuePair> nameValuePairsToken = new ArrayList<NameValuePair>(3);
                //Header
                httpTokenPost.setHeader("Authorization", "Basic c3VuYm94OnN1bmJveA=="); //Authorization : Basic c3VuYm94OnN1bmJveA==

                //Params
                nameValuePairsToken.add(new BasicNameValuePair("username", PARAMS_USERNAME)); // adding username param
                nameValuePairsToken.add(new BasicNameValuePair("password", PARAMS_PASSWORD)); // adding password param
                nameValuePairsToken.add(new BasicNameValuePair("grant_type", PARAMS_GRANT_TYPE)); // adding grant_type param

                httpTokenPost.setEntity(new UrlEncodedFormEntity(nameValuePairsToken));
                Log.w("Post Execute:", "Values added. Executing Post Request");
                Log.e("Post Execute:", "Values added. Executing Post Request");

                //Http response here..
                HttpResponse httpResponse = httpTokenClient.execute(httpTokenPost);

                jsonTokenMessage = tokenResponseMessage(httpResponse.getEntity().getContent()).toString();
                Log.i("Success :", "Success connected");
                Log.w("Token", jsonTokenMessage);
                Log.e("Token", jsonTokenMessage);

                //Using JSON the value is stored in a variable
                jsonObject = new JSONObject(jsonTokenMessage);
                access_token = jsonObject.getString("access_token");
                token_type = jsonObject.getString("token_type");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 1;
        }

        public StringBuilder tokenResponseMessage(InputStream is){

            StringBuilder total = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            try{
                while ((tokenLines = bufferedReader.readLine()) != null){
                    total.append(tokenLines);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return total;
        }
    }

    //HTTP POST LOGIN..
    public class LoginPost extends AsyncTask{

        JSONObject jsonObject;

        @Override
        protected Object doInBackground(Object[] objects) {

            try {

                HttpClient httpLoginClient = new DefaultHttpClient();

                HttpPost httpLoginPost = new HttpPost(LOGIN_URL);

                List<NameValuePair> nameValuePairsLogin = new ArrayList<NameValuePair>(2);
                httpLoginPost.setHeader("Authorization", token_type + " " + access_token);

                nameValuePairsLogin.add(new BasicNameValuePair("email", email_input));
                nameValuePairsLogin.add(new BasicNameValuePair("password", password_input));

                Log.i("Response", "posted");
                Log.e("Response", "posted");
                Log.w("Response", "posted");

                httpLoginPost.setEntity(new UrlEncodedFormEntity(nameValuePairsLogin));
                HttpResponse httpLoginResponse = httpLoginClient.execute(httpLoginPost);

                jsonLoginMessage = loginResponseMessage(httpLoginResponse.getEntity().getContent()).toString();
                Log.i("Response", jsonLoginMessage);
                Log.e("Response", jsonLoginMessage);
                Log.w("Response", jsonLoginMessage);

                status_code = httpLoginResponse.getStatusLine().getStatusCode();

                jsonObject = new JSONObject(jsonLoginMessage);
                JSONObject userDataObject = jsonObject.getJSONObject("user");
                mail = userDataObject.getString("email");
                password = userDataObject.getString("password");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 1;
        }
    }

    private StringBuilder loginResponseMessage(InputStream input) {

        StringBuilder data = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

        try{
            while ((loginLines = bufferedReader.readLine()) != null){
                data.append(loginLines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
