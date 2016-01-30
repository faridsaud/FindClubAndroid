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

public class CuentaRegistrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_registrar);
    }
    public void registrar(View view) {

        new InvocaServicioREST().execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private class InvocaServicioREST extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                EditText tUsuario = (EditText) findViewById(R.id.editText);
                EditText tPassword = (EditText) findViewById(R.id.editText2);
                EditText tNombre = (EditText) findViewById(R.id.editText3);
                EditText tApellido = (EditText) findViewById(R.id.editText4);

                MyApp variables=((MyApp) getApplication());
                final String url = variables.getUrlServicio()+"/Usuario?email="+tUsuario.getText()+"&password="+tPassword.getText()+"&nombre="+tNombre.getText()+"&apellido="+tApellido.getText();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.postForObject(url,null, Void.class);
            } catch (Exception e) {
                Log.e("Cliente Rest", e.getMessage(), e);
            }
            return null;
        }

    }
}
