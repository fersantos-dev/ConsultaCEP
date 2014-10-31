package consultacep.consultacep.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Fernando on 07/09/2014.
 */
public class NetworkDetector {

    private Context context;

    public NetworkDetector(Context context) {
        this.context = context;
    }

    public boolean detectConection(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null){
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();

            // Se não existe nenhum tipo de conexão retorna false
            if (netInfo == null){
                return false;
            }

            int netType = netInfo.getType();

            // Verifica se a conexão é do tipo WiFi ou Mobile e
            // retorna true se estiver conectado ou false em
            // caso contrário

            if (netType == ConnectivityManager.TYPE_WIFI || netType == ConnectivityManager.TYPE_MOBILE){
                return netInfo.isConnected();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
