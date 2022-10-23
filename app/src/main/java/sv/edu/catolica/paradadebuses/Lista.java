package sv.edu.catolica.paradadebuses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private List<Datos> items;
    private ListaAdapter adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        list=(ListView) findViewById(R.id.lista);
        list.setOnItemClickListener(this);
        llenarDatos();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Datos item=items.get(i);
        Intent m=new Intent(this,Mapa.class);
        m.putExtra("id",Long.toString(item.getId()));
        startActivity(m);
    }

    public void llenarDatos() {
        items=new ArrayList<Datos>();
        abrirDB base=new abrirDB(this,"paradaB",null,1);
        SQLiteDatabase bd=base.getReadableDatabase();
        String columns[]=new String[]{"_id","nombre","descripcion","latitud","longitud","tipo","foto"};
        Cursor c=bd.query("paradabuses",columns,null,null,null,null,null,null);
        if (c.moveToFirst()) {
            do {
                items.add(new Datos(c.getInt(0),c.getString(2),c.getString(5),c.getString(3),c.getString(4),c.getString(1),c.getString(6)));
            } while (c.moveToNext());
        }
        adapter = new ListaAdapter(Lista.this,items);
        list.setAdapter(adapter);
        bd.close();
    }
}