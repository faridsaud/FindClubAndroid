package ec.edu.epn.findclub.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.CiudadAdministrar;
import ec.edu.epn.findclub.CuentaEliminar;
import ec.edu.epn.findclub.CuentaModificar;
import ec.edu.epn.findclub.FiestaEliminar;
import ec.edu.epn.findclub.FiestaModificar;
import ec.edu.epn.findclub.R;
import ec.edu.epn.findclub.VO.Fiesta;

/**
 * Created by Penelope on 01/02/20 16.
 */
public class FiestaAdapter extends ArrayAdapter<Fiesta> {

    public FiestaAdapter (Context context, ArrayList<Fiesta> listaFiesta){
        super(context, 0, listaFiesta);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Fiesta fiesta = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_fiesta_resultados_administrar, parent, false);
        }

        // Lookup view for data population
        TextView txtFiesta = (TextView) convertView.findViewById(R.id.txtFiestaView);
        txtFiesta.setText(fiesta.getNombreFiesta());
        final int idFiesta=fiesta.getIdFiesta();

        // Return the completed view to render on screen
        ImageButton bEliminar = (ImageButton) convertView.findViewById(R.id.btnViewFiestaEliminar);
        ImageButton bModificar = (ImageButton) convertView.findViewById(R.id.btnViewFiestaModificar);
        bEliminar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Eliminar", "Eliminar" + idFiesta);
                Intent intent = new Intent(getContext(), FiestaEliminar.class);
                intent.putExtra("FIESTA", idFiesta);
                getContext().startActivity(intent);
                //Do stuff here
            }
        });
        bModificar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Modificar", "Modificar"+idFiesta);
                Intent intent = new Intent(getContext(), FiestaModificar.class);
                intent.putExtra("FIESTA", idFiesta);
                getContext().startActivity(intent);
                //Do stuff here
            }
        });
        return convertView;
    }
}
