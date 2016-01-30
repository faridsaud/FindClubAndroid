package ec.edu.epn.findclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ec.edu.epn.findclub.Application.MyApp;

public class CuentaAdministrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_administrar);
        MyApp variables=((MyApp) getApplication());
        if(variables.getUsuarioLogeado()==null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Log.d("Usuario Logeado", "onCreate: " + variables.getUsuarioLogeado().getEmail());
        }
    }
}
