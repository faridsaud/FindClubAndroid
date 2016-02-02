package ec.edu.epn.findclub.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ec.edu.epn.findclub.DiscotecaEliminar;
import ec.edu.epn.findclub.DiscotecaInfo;
import ec.edu.epn.findclub.DiscotecaModificar;
import ec.edu.epn.findclub.R;
import ec.edu.epn.findclub.VO.Discoteca;

/**
 * Created by aasf9_000 on 31/1/2016.
 */
public class DiscotecaAdapter extends ArrayAdapter<Discoteca> {
    public DiscotecaAdapter(Context context, ArrayList<Discoteca> discos) {
        super(context, 0, discos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Discoteca disco = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listdiscos, parent, false);
        }
        // Lookup view for data population
        TextView discoNombre = (TextView) convertView.findViewById(R.id.textView11);
        // Populate the data into the template view using the data object
        discoNombre.setText(disco.getNombre());
        final int idDisco=disco.getIdDiscoteca();
        // Return the completed view to render on screen
        ImageButton bVer = (ImageButton) convertView.findViewById(R.id.imageButton);
        ImageButton bEliminar = (ImageButton) convertView.findViewById(R.id.imageButton3);
        ImageButton bModificar = (ImageButton) convertView.findViewById(R.id.imageButton2);
        bVer.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Info", "Info"+idDisco);
                Intent intent = new Intent(getContext(), DiscotecaInfo.class);
                intent.putExtra("IDDISCO", idDisco);
                getContext().startActivity(intent);
                //Do stuff here
            }
        });
        bEliminar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Eliminar", "Eliminar"+idDisco);
                Intent intent = new Intent(getContext(), DiscotecaEliminar.class);
                intent.putExtra("IDDISCO", idDisco);
                getContext().startActivity(intent);
                //Do stuff here
            }
        });
        bModificar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Modificar", "Modificar" + idDisco);
                Intent intent = new Intent(getContext(), DiscotecaModificar.class);
                intent.putExtra("IDDISCO", idDisco);
                getContext().startActivity(intent);
                //Do stuff here
            }
        });
        return convertView;
    }

}
