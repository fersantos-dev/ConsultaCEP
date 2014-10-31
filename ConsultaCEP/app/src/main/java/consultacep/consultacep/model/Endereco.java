package consultacep.consultacep.model;

import java.io.Serializable;

/**
 * Created by Fernando on 31/08/2014.
 */
public class Endereco implements Serializable{
    String bairro;
    String cep;
    String cidade;
    String estado;
    String logradouro;
    String tipoDeLogradouro;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getTipodelogradouro() {
        return tipoDeLogradouro;
    }

    public void setTipodelogradouro(String tipodelogradouro) {
        this.tipoDeLogradouro = tipodelogradouro;
    }


}
