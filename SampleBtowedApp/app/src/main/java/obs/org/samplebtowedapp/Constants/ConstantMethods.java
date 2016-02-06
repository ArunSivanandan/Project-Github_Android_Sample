package obs.org.samplebtowedapp.Constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConstantMethods {


    public boolean checkInternetConnection(Context c){
        ConnectivityManager connectivityManager = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
