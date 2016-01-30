package ec.edu.epn.findclub;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ec.edu.epn.findclub.VO.Usuario;

public class CuentaIniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_iniciar_sesion);
    }
    private class InvocaServicioREST extends AsyncTask<Void, Void, Usuario> {

        @Override
        protected Usuario doInBackground(Void... params) {
            try {
                EditText tUsuario = (EditText) findViewById(R.id.editText);

                EditText tPassword = (EditText) findViewById(R.id.editText2);

                final String url = "http://192.168.2.149:8080/FindClub/REST/Usuario/buscar?email="+tUsuario.getText()+"&password="+tPassword.getText();
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
            Log.d("Usuario", "onPostExecute: "+usr.getEmail());
        }
    }

}
