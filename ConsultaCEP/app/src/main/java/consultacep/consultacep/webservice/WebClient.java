package consultacep.consultacep.webservice;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Fernando on 31/08/2014.
 */
public class WebClient {

    private static String URL = "http://correiosapi.apphb.com/cep/";
    private static String resposta;



    public static String get(String cepParaBuscar) {
        String resposta;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(URL + cepParaBuscar);

        Log.i("BuscaCEP", "EFETUANDO O GET PARA CEP: " + cepParaBuscar );

        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");

        try {
            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                resposta = EntityUtils.toString(response.getEntity());
                Log.i("BuscaCEP", "CEP Encontrado: " + resposta);
            } else {
                Log.i("BuscaCEP", "Problema na requisição - Error:" + statusCode);
                resposta = Integer.toString(statusCode);
            }

            return resposta;


        } catch (IOException e) {
            Log.e("BuscaCEP", e.getMessage());
            e.printStackTrace();

            return null;
        }
    }
}
