package consultacep.consultacep.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import consultacep.consultacep.R;
import consultacep.consultacep.model.Endereco;

/**
 * Created by Fernando on 08/09/2014.
 */
public class InformacoesFragment extends Fragment {

    TextView cep;
    TextView logradouro;
    TextView tipo;
    TextView bairro;
    TextView cidade;
    TextView estado;
    Button historicoCEPs;
    private Endereco endereco;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_informacoes, container, false);

        cep = (TextView) layout.findViewById(R.id.cep);
        logradouro = (TextView) layout.findViewById(R.id.logradouro);
        tipo = (TextView) layout.findViewById(R.id.tipo);
        bairro = (TextView) layout.findViewById(R.id.bairro);
        estado = (TextView) layout.findViewById(R.id.estado);
        cidade = (TextView) layout.findViewById(R.id.cidade);


        if (getArguments() != null){
            this.endereco = (Endereco) getArguments().getSerializable("endereco");
        }

        if (endereco != null){
            cep.setText(endereco.getCep());
            logradouro.setText(endereco.getLogradouro());
            tipo.setText(endereco.getTipodelogradouro());
            bairro.setText(endereco.getBairro());
            estado.setText(endereco.getEstado());
            cidade.setText(endereco.getCidade());
        }

        return layout;
    }


    public void lidaComRetorno(Endereco enderecoRetornado) {
        this.endereco = enderecoRetornado;

        cep.setText(endereco.getCep());
        logradouro.setText(endereco.getLogradouro());
        tipo.setText(endereco.getTipodelogradouro());
        bairro.setText(endereco.getBairro());
        cidade.setText(endereco.getCidade());
        estado.setText(endereco.getEstado());

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_informacoes, this);
        fragmentTransaction.commit();

    }

    public void lidaComErro(int respostaJson) {

        cep.setText("-");
        logradouro.setText("-");
        tipo.setText("-");
        bairro.setText("-");
        cidade.setText("-");
        estado.setText("-");


        switch (respostaJson) {
            case 400:
                Toast.makeText(getActivity(), "Erro na requisição ao servidor. Tente mais tarde", Toast.LENGTH_SHORT).show();
                break;
            case 404:
                Toast.makeText(getActivity(), "Cep não localizado.", Toast.LENGTH_SHORT).show();
                break;
            case 408:
                Toast.makeText(getActivity(), "Não foi possível completar sua consulta. Por favor, tente novamente.", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
