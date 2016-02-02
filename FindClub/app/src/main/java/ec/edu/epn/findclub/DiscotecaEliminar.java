package ec.edu.epn.findclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ec.edu.epn.findclub.Application.MyApp;

public class DiscotecaEliminar extends AppCompatActivity {

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
                int idDisco=getIntent().getExtras().getInt("IDDISCO");
                final String url = variables.getUrlServicio()+"/ServiceDiscoteca/Eliminar/"+idDisco;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getForObject(url, Void.class);
            } catch (Exception e) {
                Log.e("Discoteca Rest", e.getMessage(), e);
            }
            return null;
        }
    }
}
