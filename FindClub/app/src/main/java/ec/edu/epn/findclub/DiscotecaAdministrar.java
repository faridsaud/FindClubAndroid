package ec.edu.epn.findclub;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ec.edu.epn.findclub.Adapters.DiscotecaAdapter;
import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Discoteca;
public class DiscotecaAdministrar extends AppCompatActivity {
    private DiscotecaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoteca_administrar);
        ArrayList<Discoteca> arrayDeDiscos = new ArrayList<Discoteca>();
        adapter = new DiscotecaAdapter(this, arrayDeDiscos);
        ListView listView = (ListView) findViewById(R.id.listView20);
        listView.setAdapter(adapter);
        }
    public void buscarDisco(View v){
        new InvocaServicioREST().execute();
    }
    private class InvocaServicioREST extends AsyncTask<Void, Void, List<Discoteca>> {

        @Override
        protected List<Discoteca> doInBackground(Void... params) {
            try {
                EditText tDisco = (EditText) findViewById(R.id.editText40);
                if (tDisco.getText()==null){
                    tDisco.setText("0");
                }
                MyApp variables=((MyApp) getApplication());
                Log.d("URLSERVICIO!!!!!!! ", variables.getUrlServicio());
                final String url = variables.getUrlServicio()+"/ServiceDiscoteca/ListaDiscos/"+tDisco.getText();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<Discoteca> listaDiscotecas=Arrays.asList(restTemplate.getForObject(url, Discoteca[].class));
                return listaDiscotecas;
            } catch (Exception e) {
                Log.e("Discoteca Rest", e.getMessage(), e);
            }
            return null;
        }
        protected void onPostExecute(List<Discoteca> listaDiscotecas) {
            adapter.clear();
            for(Discoteca discoteca:listaDiscotecas){
                Log.d("lista de discotecas", discoteca.getNombre());
                adapter.add(discoteca);
            }
        }
    }
}
