package sv.edu.catolica.paradadebuses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListaAdapter  extends ArrayAdapter<Datos> {
    private final Activity context;
    private final List<Datos> datos;

    public ListaAdapter(Activity context, List<Datos> datos) {
        super(context,R.layout.activity_row,datos);
        this.context=context;
        this.datos=datos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_row,null,true);
        TextView txtTitle=(TextView) rowView.findViewById(R.id.titulo);
        TextView txtSubtitle=(TextView) rowView.findViewById(R.id.descripcion);
        TextView txtUbicacion=(TextView) rowView.findViewById(R.id.ubicacion);
        ImageView img= (ImageView) rowView.findViewById(R.id.imagen);
        Datos item=datos.get(position);
        txtTitle.setText(item.getNombre());
        txtSubtitle.setText(item.getDescripcion());
        txtUbicacion.setText(item.getLatitud() + " , " + item.getLongitud());
        if (item.getTipo().equals("Parada Particular")) {
            img.setImageResource(R.drawable.busformal);
        } else if (item.getTipo().equals("Parada Informal")) {
            img.setImageResource(R.drawable.paradadebusinformal);
        } else if (item.getTipo().equals("Parada Escolar")) {
            img.setImageResource(R.drawable.busescolar);
        } else if (item.getTipo().equals("Terminal")) {
        img.setImageResource(R.drawable.terminalbus);
        }
        return rowView;
    }
}