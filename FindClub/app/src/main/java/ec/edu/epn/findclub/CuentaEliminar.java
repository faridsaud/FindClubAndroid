package ec.edu.epn.findclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Usuario;

public class CuentaEliminar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new InvocaServicioREST().execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class InvocaServicioREST extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                MyApp variables=((MyApp) getApplication());
                String email=getIntent().getExtras().getString("EMAIL");
                final String url = variables.getUrlServicio()+"/Usuario/"+email;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.delete(url, Void.class);


            } catch (Exception e) {
                Log.e("Cliente Rest", e.getMessage(), e);
            }
            return null;
        }





    }

}
