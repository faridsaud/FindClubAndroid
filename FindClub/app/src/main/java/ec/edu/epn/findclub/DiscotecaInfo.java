package ec.edu.epn.findclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Discoteca;

public class DiscotecaInfo extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoteca_info);
        int idDisco=getIntent().getExtras().getInt("IDDISCO");
        new llenarDatos().execute();
    }
    private class llenarDatos extends AsyncTask<Void, Void, Discoteca> {

        @Override
        protected Discoteca doInBackground(Void... params) {
            try {
                int idDisco=getIntent().getExtras().getInt("IDDISCO");
                MyApp variables=((MyApp) getApplication());
                final String url = variables.getUrlServicio()+"/ServiceDiscoteca/Buscar/"+idDisco;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Discoteca disco = restTemplate.getForObject(url, Discoteca.class);
                return disco;
            } catch (Exception e) {
                Log.e("Discoteca Rest", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Discoteca discoteca) {
            Log.d("Discoteca", "onPostExecute: " + discoteca.getIdDiscoteca());
            EditText tNombre=(EditText)findViewById(R.id.editText9);
            EditText tDescripcion=(EditText)findViewById(R.id.editText13);
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            tNombre.setText(discoteca.getNombre());
            tDescripcion.setText(discoteca.getDescripcion());
        }
    }public void verFiestasDisco(){
        new fiestasREST().execute();
        Intent intent = new Intent(this, FiestaResultadosAdministrar.class);
        startActivity(intent);
    }
    private class fiestasREST extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int idDisco=getIntent().getExtras().getInt("IDDISCO");
                MyApp variables=((MyApp) getApplication());
                final String url = variables.getUrlServicio()+"/ServiceFiesta/BuscarFiestasByDisco/"+idDisco;
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
