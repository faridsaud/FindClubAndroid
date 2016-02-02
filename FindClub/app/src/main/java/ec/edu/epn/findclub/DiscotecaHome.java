package ec.edu.epn.findclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DiscotecaHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoteca_home);
    }
    public void ingresarDisco(View view){

        Intent intent = new Intent(this, DiscotecaIngresar.class);
        startActivity(intent);
    }

    public void administrarDisco(View view){
        Intent intent = new Intent(this, DiscotecaAdministrar.class);
        startActivity(intent);
    }
}
