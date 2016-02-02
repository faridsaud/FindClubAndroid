package ec.edu.epn.findclub;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Discoteca;

public class DiscotecaModificar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoteca_modificar);
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
            Log.d("Discoteca", "onPostExecute: " + discoteca.getNombre());
            EditText tNombre=(EditText)findViewById(R.id.editText14);
            EditText tDescripcion=(EditText)findViewById(R.id.editText15);
            tNombre.setText(discoteca.getNombre());
            tDescripcion.setText(discoteca.getDescripcion());
        }
    }
    public void actualizarDisco(View v){
        new actualizarREST().execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private class actualizarREST extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int idDisco=getIntent().getExtras().getInt("IDDISCO");
                MyApp variables=((MyApp) getApplication());
                EditText tNombre=(EditText)findViewById(R.id.editText14);
                EditText tDescripcion=(EditText)findViewById(R.id.editText15);
                final String url = variables.getUrlServicio()+"/ServiceDiscoteca/Modificar/"+tNombre.getText().toString()+"/"+
                        variables.getUsuarioLogeado().getEmail()+"/"+tDescripcion.getText().toString()+"/Imagen/"+idDisco;
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
