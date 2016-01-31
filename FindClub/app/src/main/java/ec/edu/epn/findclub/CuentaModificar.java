package ec.edu.epn.findclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Usuario;

public class CuentaModificar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_modificar);
        String email=getIntent().getExtras().getString("EMAIL");
        EditText eEmail=(EditText)findViewById(R.id.editText);
        eEmail.setText(email);
        new llenarDatos().execute();
    }

    private class llenarDatos extends AsyncTask<Void, Void, Usuario> {

        @Override
        protected Usuario doInBackground(Void... params) {
            try {
                String email=getIntent().getExtras().getString("EMAIL");
                MyApp variables=((MyApp) getApplication());
                final String url = variables.getUrlServicio()+"/Usuario/"+email;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Usuario usr = restTemplate.getForObject(url, Usuario.class);
                return usr;
            } catch (Exception e) {
                Log.e("Cliente Rest", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Usuario usr) {
            Log.d("Usuario", "onPostExecute: " + usr.getEmail());
            EditText tEmail=(EditText)findViewById(R.id.editText);
            EditText tPassword=(EditText)findViewById(R.id.editText2);
            EditText tNombre=(EditText)findViewById(R.id.editText3);
            EditText tApellido=(EditText)findViewById(R.id.editText4);
            tEmail.setText(usr.getEmail());
            tPassword.setText(usr.getPassword());
            tNombre.setText(usr.getNombre());
            tApellido.setText(usr.getApellido());

        }
    }
    public void actualizar(View v){
        new actualizarREST().execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private class actualizarREST extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String email=getIntent().getExtras().getString("EMAIL");
                MyApp variables=((MyApp) getApplication());
                EditText tEmail=(EditText)findViewById(R.id.editText);
                EditText tPassword=(EditText)findViewById(R.id.editText2);
                EditText tNombre=(EditText)findViewById(R.id.editText3);
                EditText tApellido=(EditText)findViewById(R.id.editText4);

                final String url = variables.getUrlServicio()+"/Usuario/?email="+email+"&newEmail="+tEmail.getText()+"&newNombre="+tNombre.getText()+"&newApellido="+tApellido.getText()+"&newPassword="+tPassword.getText();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.put(url,null);
            } catch (Exception e) {
                Log.e("Cliente Rest", e.getMessage(), e);
            }
            return null;
        }

    }

}
