package ec.edu.epn.findclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Discoteca;
import ec.edu.epn.findclub.VO.Fiesta;

public class FiestaRegistrar extends AppCompatActivity {
    Map<String,Integer> myHashMap = new HashMap<String,Integer>();
    Fiesta fiesta = new Fiesta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiesta_registrar);
        llenarCombo();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void registrar (View view){
        try {
            MyApp app=((MyApp) getApplication());
            Spinner comboDiscoteca = (Spinner) findViewById(R.id.cmbFieRegDiscoteca);
            int idDiscoteca = myHashMap.get(String.valueOf(comboDiscoteca.getSelectedItem()));
            TextView txtNombre = (EditText) findViewById(R.id.txtFieRegNombre);
            TextView txtFecha = (EditText) findViewById(R.id.txtFieRegFecha);
            TextView txtHora = (EditText) findViewById(R.id.txtFieRegHora);
            TextView txtDescripcion = (EditText) findViewById(R.id.txtFieRegDescripcion);
            fiesta.setNombreFiesta(txtNombre.getText().toString());
            fiesta.setIdDiscoteca(idDiscoteca);
            fiesta.setEmail(app.getUsuarioLogeado().getEmail());
            fiesta.setFecha(txtFecha.getText().toString());
            fiesta.setHora(txtHora.getText().toString());
            fiesta.setDescripcion(txtDescripcion.getText().toString());
            new RegistrarFiestaREST().execute();
            Intent intent = new Intent(this, FiestaHome.class);
            startActivity(intent);
        } catch (Exception e){
            Log.e("Error al registrar:\n", e.getMessage(), e);
        }
    }

    public void llenarCombo (){
        new ListarDiscotecasREST().execute();
    }

    private class ListarDiscotecasREST extends AsyncTask<Void, Void, List<Discoteca>> {

        @Override
        protected List<Discoteca> doInBackground(Void... params) {
            try {
                MyApp app = ((MyApp) getApplication());
                final String url = app.getUrlServicio() + "/ServiceDiscoteca/Listar";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<Discoteca> listaDiscoteca = Arrays.asList(restTemplate.getForObject(url, Discoteca[].class));
                return listaDiscoteca;
            } catch (Exception e){
                Log.e("Llenar Combo", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Discoteca> result) {
            try {
                for (Discoteca discoteca : result) {
                    myHashMap.put(discoteca.getNombre(), discoteca.getIdDiscoteca());
                }
                List<String> list = new ArrayList<String>(myHashMap.keySet());
                Discoteca disc = result.get(1);
                System.out.println(disc.getNombre());
                Spinner comboDiscoteca = (Spinner) findViewById(R.id.cmbFieRegDiscoteca);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                comboDiscoteca.setAdapter(adapter);
            }catch (Exception e){
                Log.e("Post Llenar combo", e.getMessage(), e);
            }
        }
    }

    private class RegistrarFiestaREST extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                MyApp app = ((MyApp) getApplication());
                final String url = app.getUrlServicio() + "/Fiesta/Registrar/?nombre=" + fiesta.getNombreFiesta() +
                        "&email=" + fiesta.getEmail() + "&idDiscoteca=" + fiesta.getIdDiscoteca() +
                        "&fecha=" + fiesta.getFecha() + "&hora=" + fiesta.getHora() + "&descripcion=" + fiesta.getDescripcion();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String mensaje = (String) restTemplate.getForObject(url, String.class);
                return mensaje + "\nSe ha enviado:\n\t" + fiesta.getEmail() + "\n\t" + fiesta.getNombreFiesta() + "\n\t" + fiesta.getIdDiscoteca()
                        + "\n\t" + fiesta.getFecha() + "\t\n" + fiesta.getHora() + "\n\t" + fiesta.getDescripcion();
            } catch (Exception e) {
                Log.e("Cliente Rest", e.getMessage(), e);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            Log.i("Post ", result);
        }
    }
}
