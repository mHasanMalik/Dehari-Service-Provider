package com.nfg.devlot.dehariprovider.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by hassan on 4/8/18.
 */

public class NetworkChecker
{

    Context context;

    public NetworkChecker(Context context)
    {
        this.context = context;
    }

    public boolean haveNetworkConnection()
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            Network[] netinfo = cm.getAllNetworks();
            for (Network net :  netinfo)
            {
                if(cm.getNetworkInfo(net).getTypeName().equalsIgnoreCase("WIFI"))
                {
                    if(cm.getNetworkInfo(net).isConnected())
                    {
                        haveConnectedWifi = true;
                    }
                }
                if(cm.getNetworkInfo(net).getTypeName().equalsIgnoreCase("MOBILE"))
                {
                    if(cm.getNetworkInfo(net).isConnected())
                    {
                        haveConnectedMobile = true;
                    }
                }
            }
        }
        else
        {
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        }

        return haveConnectedWifi || haveConnectedMobile;
    }
}
