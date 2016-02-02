package ec.edu.epn.findclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FiestaHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiesta_home);
    }

    public void registrarFiesta (View view){
        Intent intent = new Intent(this, FiestaRegistrar.class);
        startActivity(intent);
    }

    public void administrarFiesta (View view) {
        Intent intent = new Intent(this, FiestaAdministrar.class);
        startActivity(intent);
    }
}
