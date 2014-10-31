package consultacep.consultacep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import consultacep.consultacep.R;
import consultacep.consultacep.model.Endereco;

/**
 * Created by Fernando on 13/09/2014.
 */
public class EnderecoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Endereco> enderecos;

    public EnderecoAdapter(Context context, ArrayList<Endereco> enderecos){
        this.context = context;
        this.enderecos = enderecos;
    }

    @Override
    public int getCount() {
        return enderecos.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Endereco getItem(int i) {

        return enderecos.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Endereco endereco = enderecos.get(position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_historico, null);

        TextView historicoCEP = (TextView)view.findViewById(R.id.historico_cep);
        historicoCEP.setText(endereco.getCep());

        TextView historicoLogradouro = (TextView) view.findViewById(R.id.historico_logradouro);
        historicoLogradouro.setText(endereco.getLogradouro());

        TextView historicoCidade = (TextView) view.findViewById(R.id.historico_cidade);
        historicoCidade.setText(endereco.getCidade());


        return view;
    }
}
