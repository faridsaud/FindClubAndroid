package ec.edu.epn.findclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.edu.epn.findclub.Adapters.FiestaAdapter;
import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Discoteca;
import ec.edu.epn.findclub.VO.Fiesta;

public class FiestaAdministrar extends AppCompatActivity {
    Map<String,Integer> myHashMap = new HashMap<String,Integer>();
    Fiesta fiesta = new Fiesta();
    private FiestaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiesta_administrar);
        llenarCombo();
        MyApp variables = ((MyApp) getApplication());

        if (variables.getUsuarioLogeado() == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Log.d("Administrar Fiestas", "onCreate: ");
            ArrayList<Fiesta> arrayFiesta = new ArrayList<Fiesta>();
            // Create the adapter to convert the array to views
            adapter = new FiestaAdapter(this, arrayFiesta);

            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.listViewAdministrarFiesta);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void buscarFiestas (View view) {
        MyApp app=((MyApp) getApplication());
        Spinner comboDiscoteca = (Spinner) findViewById(R.id.cmbFieAdmDiscoteca);
        int idDiscoteca = myHashMap.get(String.valueOf(comboDiscoteca.getSelectedItem()));
        TextView txtNombre = (EditText) findViewById(R.id.txtFieAdmNombre);
        fiesta.setEmail(app.getUsuarioLogeado().getEmail());
        fiesta.setNombreFiesta(txtNombre.getText().toString());
        fiesta.setIdDiscoteca(idDiscoteca);
        new BuscarFiestasREST().execute();
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
                Spinner comboDiscoteca = (Spinner) findViewById(R.id.cmbFieAdmDiscoteca);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                comboDiscoteca.setAdapter(adapter);
            }catch (Exception e){
                Log.e("Post Llenar combo", e.getMessage(), e);
            }
        }
    }

    private class BuscarFiestasREST extends AsyncTask<Void, Void, List<Fiesta>>{

        @Override
        protected List<Fiesta> doInBackground(Void... params) {
            try {
                MyApp app = ((MyApp) getApplication());
                final String url = app.getUrlServicio() + "/Fiesta/Listar/?nombreFiesta=" + fiesta.getNombreFiesta() +
                        "&idDiscoteca=" + fiesta.getIdDiscoteca() + "&email=" + fiesta.getEmail();
                System.out.println(url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<Fiesta> listaFiesta = Arrays.asList(restTemplate.getForObject(url, Fiesta[].class));
                return listaFiesta;
            } catch (Exception e){
                Log.e("Listar fiesta", e.getMessage(), e);
            }
            return null;
        }

        protected void onPostExecute (List<Fiesta> listaFiesta){
            adapter.clear();
            for (Fiesta fiesta : listaFiesta){
                Log.d("Lista de fiestas", fiesta.getNombreFiesta());
                adapter.add(fiesta);
            }
        }
    }
}
