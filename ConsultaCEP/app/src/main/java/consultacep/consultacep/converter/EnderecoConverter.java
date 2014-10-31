package consultacep.consultacep.converter;

import com.google.gson.Gson;

import consultacep.consultacep.model.Endereco;

/**
 * Created by Fernando on 31/08/2014.
 */
public class EnderecoConverter {

    private String enderecoJson;
    private Endereco endereco;

    public EnderecoConverter(String enderecoJson) {
    this.enderecoJson = enderecoJson;
   }

    public Endereco converte(){
        Gson gson = new Gson();
        endereco = gson.fromJson(enderecoJson, Endereco.class);
        return endereco;
    }
}
