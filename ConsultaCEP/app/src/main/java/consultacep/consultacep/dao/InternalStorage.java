package consultacep.consultacep.dao;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import consultacep.consultacep.model.Endereco;

/**
 * Created by Fernando on 06/09/2014.
 */
public class InternalStorage {


    public static void writeObject(Context context, String key, ArrayList<Endereco> enderecos) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oss= new ObjectOutputStream(fos);

        oss.writeObject(enderecos);
        oss.close();
        fos.close();
    }


    public static ArrayList<Endereco> readObject(Context context, String key) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Endereco> enderecos = (ArrayList<Endereco>) ois.readObject();
        return enderecos;
    }
}
