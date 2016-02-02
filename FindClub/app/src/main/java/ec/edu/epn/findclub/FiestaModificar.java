package ec.edu.epn.findclub;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Discoteca;
import ec.edu.epn.findclub.VO.Fiesta;

public class FiestaModificar extends AppCompatActivity {
    Map<String,Integer> myHashMap = new HashMap<String,Integer>();
    Fiesta fiesta = new Fiesta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiesta_modificar);
        llenarCombo();
        new llenarDatos().execute();
    }

    public void modificarFiesta(View view){
        try {
            MyApp app = ((MyApp) getApplication());
            Spinner comboDiscoteca = (Spinner) findViewById(R.id.cmbModificarDiscoteca);
            int idDiscoteca = myHashMap.get(String.valueOf(comboDiscoteca.getSelectedItem()));
            EditText txtNombre = (EditText) findViewById(R.id.txtModificarFiesta);
            EditText txtFecha = (EditText) findViewById(R.id.txtModificarFecha);
            EditText txtHora = (EditText) findViewById(R.id.txtModificarHora);
            EditText txtDescripcion = (EditText) findViewById(R.id.txtModificarDeescripcion);
            fiesta.setNombreFiesta(txtNombre.getText().toString());
            fiesta.setIdDiscoteca(idDiscoteca);
            fiesta.setFecha(txtFecha.getText().toString());
            fiesta.setHora(txtHora.getText().toString());
            fiesta.setDescripcion(txtDescripcion.getText().toString());
            new ModificarFiesta().execute();
        } catch (Exception e){
            Log.e("Modificar fiesta", e.getMessage(), e);
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
                Spinner comboDiscoteca = (Spinner) findViewById(R.id.cmbModificarDiscoteca);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                comboDiscoteca.setAdapter(adapter);
            }catch (Exception e){
                Log.e("Post Llenar combo", e.getMessage(), e);
            }
        }
    }

    public class llenarDatos extends AsyncTask<Void, Void, Fiesta>{

        @Override
        protected Fiesta doInBackground(Void... params) {
            try{
                MyApp app = ((MyApp) getApplication());
                int idFiesta = getIntent().getExtras().getInt("FIESTA");
                final String url = app.getUrlServicio() + "/Fiesta/Buscar/" + idFiesta;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Fiesta fiesta = restTemplate.getForObject(url, Fiesta.class);
                return fiesta;
            }catch (Exception e){
                Log.e("LLenar datos Fiesta", e.getMessage(), e);
            }
            return null;
        }

        protected void onPostExecute(Fiesta fiesta){
            Log.d("LLenar Fiesta", "onPostExecute: " + fiesta.getNombreFiesta());
            Spinner comboDiscoteca = (Spinner) findViewById(R.id.cmbModificarDiscoteca);
            EditText txtNombre = (EditText) findViewById(R.id.txtModificarFiesta);
            EditText txtFecha = (EditText) findViewById(R.id.txtModificarFecha);
            EditText txtHora = (EditText) findViewById(R.id.txtModificarHora);
            EditText txtDescripcion = (EditText) findViewById(R.id.txtModificarDeescripcion);
            txtNombre.setText(fiesta.getNombreFiesta());
            txtFecha.setText(fiesta.getFecha());
            txtHora.setText(fiesta.getHora());
            txtDescripcion.setText(fiesta.getDescripcion());
        }
    }

    public class ModificarFiesta extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) {
            try{
                MyApp app = ((MyApp) getApplication());
                final String url = app.getUrlServicio() + "/Fiesta/Modificar?idFiesta=" + fiesta.getIdFiesta() +
                        "&nombre=" + fiesta.getNombreFiesta() + "&idDiscoteca=" + fiesta.getIdDiscoteca() +
                        "&fecha=" + fiesta.getFecha() + "&hora=" + fiesta.getHora() + "&descripcion=" + fiesta.getDescripcion();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String mensaje = (String) restTemplate.getForObject(url, String.class);
                return mensaje + "\nSe ha enviado:\n\t" + fiesta.getEmail() + "\n\t" + fiesta.getNombreFiesta() + "\n\t" + fiesta.getIdDiscoteca()
                        + "\n\t" + fiesta.getFecha() + "\t\n" + fiesta.getHora() + "\n\t" + fiesta.getDescripcion();
            }catch (Exception e){
                Log.e("Modificar fiesta", e.getMessage(), e);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            Log.i("Post ", result);
        }
    }

}
