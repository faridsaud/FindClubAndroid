package ec.edu.epn.findclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ec.edu.epn.findclub.Application.MyApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cuenta(View view){

        Intent intent = new Intent(this, Cuenta.class);
        startActivity(intent);
    }

    public void fiesta (View view){
        MyApp app = ((MyApp) getApplication());
        if (app.getUsuarioLogeado() != null) {
            Intent intent = new Intent(this, FiestaHome.class);
            startActivity(intent);
        }
    }
    public void discotecaHome(View view){

        Intent intent = new Intent(this, DiscotecaHome.class);
        startActivity(intent);
    }
}
