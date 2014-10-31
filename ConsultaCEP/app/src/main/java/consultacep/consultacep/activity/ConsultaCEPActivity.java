package consultacep.consultacep.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import consultacep.consultacep.R;
import consultacep.consultacep.adapter.EnderecoAdapter;
import consultacep.consultacep.dao.InternalStorage;
import consultacep.consultacep.fragment.BuscaCepFragment;
import consultacep.consultacep.fragment.InformacoesFragment;
import consultacep.consultacep.model.Endereco;
import consultacep.consultacep.task.BuscaCEPTask;

public class ConsultaCEPActivity extends FragmentActivity {

    EditText digitaCEP;
    ImageButton busca;

    TextView cep;
    TextView logradouro;
    TextView tipo;
    TextView bairro;
    TextView cidade;
    TextView estado;
    Button historicoCEPs;

    ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
    ArrayList<Endereco> enderecosInvertido;

    public BuscaCepFragment buscaCepFragment = new BuscaCepFragment();
    public InformacoesFragment informacoesFragment = new InformacoesFragment();
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_consulta_cep);


        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_consultacep, buscaCepFragment);
        fragmentTransaction.replace(R.id.framelayout_informacoes, informacoesFragment);

        fragmentTransaction.commit();

        historicoCEPs = (Button) findViewById(R.id.historicoCEPs);

        historicoCEPs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    enderecos = InternalStorage.readObject(getApplicationContext(), "enderecos");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (enderecos.size() > 0) {
                    enderecosInvertido = enderecos;
                    Collections.reverse(enderecosInvertido);
                    final Dialog dialog = new Dialog(ConsultaCEPActivity.this);


                    EnderecoAdapter enderecoAdapter = new EnderecoAdapter(ConsultaCEPActivity.this, enderecosInvertido);

                    ListView listView = new ListView(ConsultaCEPActivity.this);
                    listView.setAdapter(enderecoAdapter);


                    dialog.setContentView(listView);
                    dialog.setTitle("Histórico de CEPs");
                    dialog.show();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Endereco endereco = enderecos.get(i);
                            abreCepDoHistorico(endereco);
                            dialog.dismiss();

                        }
                    });

                } else {
                    Toast.makeText(ConsultaCEPActivity.this, "Não há histórico para ser exibido", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void buscaCep(String cepDigitado) {
        try {
            enderecos = InternalStorage.readObject(getApplicationContext(), "enderecos");
        } catch (Exception e) {
            e.printStackTrace();
        }

        BuscaCEPTask buscaCEPTask = new BuscaCEPTask(ConsultaCEPActivity.this, informacoesFragment, enderecos, cepDigitado);
        buscaCEPTask.execute();
    }


    public void abreCepDoHistorico(Endereco endereco) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("endereco", endereco);

        informacoesFragment = new InformacoesFragment();
        informacoesFragment.setArguments(bundle);

        buscaCepFragment.limparCEP();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_informacoes, informacoesFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sobre:
                Intent intent = new Intent(ConsultaCEPActivity.this, SobreActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
