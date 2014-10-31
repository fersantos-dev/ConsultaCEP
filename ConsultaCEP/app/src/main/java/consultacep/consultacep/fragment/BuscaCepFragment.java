package consultacep.consultacep.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import consultacep.consultacep.R;
import consultacep.consultacep.activity.ConsultaCEPActivity;
import consultacep.consultacep.activity.Mask;
import consultacep.consultacep.dao.InternalStorage;
import consultacep.consultacep.model.Endereco;
import consultacep.consultacep.webservice.NetworkDetector;

/**
 * Created by Fernando on 07/09/2014.
 */
public class BuscaCepFragment extends Fragment {

    EditText digitaCEP;
    ImageButton busca;
    ArrayList<Endereco> enderecos = new ArrayList<Endereco>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_consultacep, container, false);


        digitaCEP = (EditText) layout.findViewById(R.id.digitaCEP);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.otf");
        digitaCEP.setTypeface(font);


        digitaCEP.addTextChangedListener(Mask.insert("#####-###", digitaCEP));
        busca = (ImageButton) layout.findViewById(R.id.buscaButton);


        try {
            enderecos = InternalStorage.readObject(getActivity().getApplicationContext(), "enderecos");
        } catch (Exception e) {
            e.printStackTrace();
        }


        digitaCEP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard(getActivity().getApplicationContext(), digitaCEP);

                    NetworkDetector networkDetector = new NetworkDetector(getActivity().getApplicationContext());
                    boolean isConnected = networkDetector.detectConection();
                    buscaCEP(isConnected);

                    return true;
                }


                return false;
            }
        });

        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity().getApplicationContext(), digitaCEP);

                NetworkDetector networkDetector = new NetworkDetector(getActivity().getApplicationContext());
                boolean isConnected = networkDetector.detectConection();

                buscaCEP(isConnected);

            }
        });

        return layout;
    }

    private void buscaCEP(boolean isConnected) {
        if (isConnected) {
            Log.i("Consulta CEP", "Network detected!");
            if (digitaCEP.getText().toString() != null && digitaCEP.getText().length() == 9) {

                ConsultaCEPActivity consultaCEPActivity = (ConsultaCEPActivity) getActivity();
                consultaCEPActivity.buscaCep(digitaCEP.getText().toString());

            } else{
                Toast.makeText(getActivity().getApplicationContext(), "Digite o CEP corretamente.", Toast.LENGTH_SHORT).show();
                Log.i("Consulta CEP", "CEP digitado errado!");
            }


        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Conexão não encontrada.", Toast.LENGTH_SHORT).show();
            Log.i("Consulta CEP", "Network not detected!");
        }
    }

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void limparCEP() {
        digitaCEP.setText("");
        digitaCEP.clearFocus();

     }
}
