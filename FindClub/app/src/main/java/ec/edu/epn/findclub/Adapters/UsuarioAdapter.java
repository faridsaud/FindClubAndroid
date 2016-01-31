package ec.edu.epn.findclub.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ec.edu.epn.findclub.CuentaModificar;
import ec.edu.epn.findclub.CuentaRegistrar;
import ec.edu.epn.findclub.R;
import ec.edu.epn.findclub.VO.Usuario;

/**
 * Created by farid on 1/30/2016.
 */
public class UsuarioAdapter extends ArrayAdapter<Usuario> {
    public UsuarioAdapter(Context context, ArrayList<Usuario> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Usuario user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }
        // Lookup view for data population
        TextView tEmail = (TextView) convertView.findViewById(R.id.textView30);
        // Populate the data into the template view using the data object
        tEmail.setText(user.getEmail());
        final String email=user.getEmail();
        // Return the completed view to render on screen
        Button bEliminar = (Button) convertView.findViewById(R.id.button15);
        Button bModificar = (Button) convertView.findViewById(R.id.button16);
        bEliminar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Eliminar", "Eliminar"+email);
                //Do stuff here
            }
        });
        bModificar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Modificar", "Modificar"+email);
                Intent intent = new Intent(getContext(), CuentaModificar.class);
                intent.putExtra("EMAIL", email);
                getContext().startActivity(intent);
                //Do stuff here
            }
        });
        return convertView;
    }
}