package ec.edu.epn.findclub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CuentaModificar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_modificar);
        String email=getIntent().getExtras().getString("EMAIL");
        EditText eEmail=(EditText)findViewById(R.id.editText);
        eEmail.setText(email);
    }
}
