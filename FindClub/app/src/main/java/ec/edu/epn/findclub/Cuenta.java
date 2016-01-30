package ec.edu.epn.findclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Cuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
    }
    public void iniciarSesion(View view){

        Intent intent = new Intent(this, CuentaIniciarSesion.class);
        startActivity(intent);
    }

    public void cerrarSesion(View view){
        Intent intent = new Intent(this, Cuenta.class);
        startActivity(intent);
    }

    public void registrar(View view){
        Intent intent = new Intent(this, CuentaRegistrar.class);
        startActivity(intent);
    }

    public void administrar(View view){

        Intent intent = new Intent(this, CuentaAdministrar.class);
        startActivity(intent);
    }

}
