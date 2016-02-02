package ec.edu.epn.findclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ec.edu.epn.findclub.Application.MyApp;

/**
 * Created by Penelope on 01/02/2016.
 */
public class FiestaEliminar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiesta_registrar);
        new EliminarFiestaREST().execute();
        Intent intent = new Intent(this, FiestaHome.class);
        startActivity(intent);
    }

    private class EliminarFiestaREST extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            try {
                MyApp app = ((MyApp) getApplication());
                int idFiesta = getIntent().getExtras().getInt("FIESTA");
                final String url = app.getUrlServicio() + "/Fiesta/Eliminar/" + idFiesta;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String mensaje = (String) restTemplate.getForObject(url, String.class);
                return mensaje;
            } catch (Exception e){
                Log.e("Eliminar Fiesta",e.getMessage(), e);
            }
            return null;
        }
    }
}
