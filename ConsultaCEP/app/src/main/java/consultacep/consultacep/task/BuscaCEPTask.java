package consultacep.consultacep.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import consultacep.consultacep.converter.EnderecoConverter;
import consultacep.consultacep.dao.InternalStorage;
import consultacep.consultacep.fragment.InformacoesFragment;
import consultacep.consultacep.model.Endereco;
import consultacep.consultacep.webservice.WebClient;

/**
 * Created by Fernando on 31/08/2014.
 */
public class BuscaCEPTask extends AsyncTask<String, Void, Endereco> {


    private final Context context;
    private final InformacoesFragment informacoesFragment;
    private ArrayList<Endereco> enderecos;
    private String cep;
    private Endereco endereco = null;
    private int statusCode;
    ProgressDialog progressDialog;


    public BuscaCEPTask(Context context, InformacoesFragment informacoesFragment, ArrayList<Endereco> enderecos, String cep) {
        this.context = context;
        this.informacoesFragment = informacoesFragment;
        this.enderecos = enderecos;
        this.cep = cep.replace("-", "");
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Buscando CEP...");
        progressDialog.show();
    }

    @Override
    protected Endereco doInBackground(String... strings) {

        Log.i("Consulta CEP", "BuscaCEPTask iniciado!");
        String respostaJson = WebClient.get(cep);
        Log.i("Consulta CEP", respostaJson);

        if (respostaJson.equalsIgnoreCase("404") || respostaJson.equalsIgnoreCase("400") || respostaJson.equalsIgnoreCase("408")) {
            statusCode = Integer.parseInt(respostaJson);
        } else {
            statusCode = 200;
            EnderecoConverter converter = new EnderecoConverter(respostaJson);
            endereco = converter.converte();

            enderecos.add(endereco);

            try {
                InternalStorage.writeObject(context, "enderecos", enderecos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return endereco;

    }


    @Override
    protected void onPostExecute(Endereco enderecoRetornado) {

        progressDialog.dismiss();

        if (statusCode == 200) {
            informacoesFragment.lidaComRetorno(endereco);
        } else {
            informacoesFragment.lidaComErro(statusCode);
        }
    }
}
