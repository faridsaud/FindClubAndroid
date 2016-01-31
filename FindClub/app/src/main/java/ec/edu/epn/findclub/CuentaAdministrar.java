package ec.edu.epn.findclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ec.edu.epn.findclub.Adapters.UsuarioAdapter;
import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Usuario;

public class CuentaAdministrar extends AppCompatActivity {
    private UsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_administrar);
        MyApp variables=((MyApp) getApplication());
        /*
        if(variables.getUsuarioLogeado()==null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Log.d("Usuario Logeado", "onCreate: " + variables.getUsuarioLogeado().getEmail());
        }
        */
        // Construct the data source
        ArrayList<Usuario> arrayOfUsers = new ArrayList<Usuario>();

// Create the adapter to convert the array to views
        adapter = new UsuarioAdapter(this, arrayOfUsers);
// Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listView3);
        listView.setAdapter(adapter);

    }
    public void buscar(View v){
        new InvocaServicioREST().execute();
    }
    private class InvocaServicioREST extends AsyncTask<Void, Void, List<Usuario>> {

        @Override
        protected List<Usuario> doInBackground(Void... params) {
            try {

                EditText tUsuario = (EditText) findViewById(R.id.editText5);
                MyApp variables=((MyApp) getApplication());
                if(tUsuario.getText()==null){
                    tUsuario.setText("");
                }
                final String url = variables.getUrlServicio()+"/Usuario/?email="+tUsuario.getText()+"&emailLog="+variables.getUsuarioLogeado().getEmail();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<Usuario> listaUsuarios=Arrays.asList(restTemplate.getForObject(url, Usuario[].class));
                return listaUsuarios;
            } catch (Exception e) {
                Log.e("Cliente Rest", e.getMessage(), e);
            }
            return null;
        }
        protected void onPostExecute(List<Usuario> listaUsuarios) {
           adapter.clear();
            for(Usuario usr:listaUsuarios){
               Log.d("lista de usuarios", usr.getEmail());
               adapter.add(usr);
           }
            /*
            ArrayList<Usuario> arrayUsuario= new ArrayList<Usuario>(listaUsuarios);
            arrayOfUsers=arrayUsuario;
*/
        }

    }


}
