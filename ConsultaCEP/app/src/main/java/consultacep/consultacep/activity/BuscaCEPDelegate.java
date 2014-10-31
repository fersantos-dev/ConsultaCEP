package consultacep.consultacep.activity;

import java.io.IOException;

import consultacep.consultacep.model.Endereco;

/**
 * Created by Fernando on 31/08/2014.
 */
public interface BuscaCEPDelegate {

    void lidaComRetorno(Endereco endereco) throws IOException;

    void lidaComErro(int respostaJson);
}
