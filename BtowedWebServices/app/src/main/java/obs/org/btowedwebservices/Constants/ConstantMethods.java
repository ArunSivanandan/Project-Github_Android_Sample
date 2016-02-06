package obs.org.btowedwebservices.Constants;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import obs.org.btowedwebservices.Network.WebServiceClass;

public class ConstantMethods {

    ConnectivityManager connectivityManager;
    static NetworkInfo networkInfo;

    public boolean checkInternetConnection(Context c){
        connectivityManager = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null){
            ConstantVariables.state = false;
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(c);
            alertdialog.setTitle("Network Error");
            alertdialog.setMessage("Check your internet connection");
            alertdialog.setCancelable(true);
            alertdialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertbox = alertdialog.create();
            alertbox.show();
        }
        ConstantVariables.state = true;

        return networkInfo != null && networkInfo.isConnected();
    }

    public void StatusConditon(){
        if(WebServiceClass.status_code == 200 || WebServiceClass.status_code == 206 || WebServiceClass.status_code == 205){
            WebServiceClass.mail = "";
            WebServiceClass.pass = "";
        }
    }
}
