package sv.edu.catolica.paradadebuses;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {
    private MapFragment mapFragment;
    private GoogleMap mapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        mapFragment=(MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa=googleMap;
        Bundle bundle=getIntent().getExtras();
        mostrarMapa(bundle.getString("id"));
    }
    private void mostrarMapa(String id) {
        abrirDB base = new abrirDB(this,"paradaB",null,1);
        SQLiteDatabase bd = base.getReadableDatabase();
        String columns[] = new String[]{"_id","nombre","latitud","longitud","descripcion"};
        String args[] = new String[] {id};
        Cursor c = bd.query("paradabuses",columns,"_id=?",args,null,null,null,null);
        if (c.moveToFirst()) {
            LatLng centro = new LatLng(c.getFloat(2),c.getFloat(3));
            CameraPosition camPos = new CameraPosition.Builder()
                    .target(centro)
                    .zoom(40)
                    .bearing(45)
                    .tilt(50)
                    .build();

            CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
            mapa.animateCamera(camUpd3);
            Marker mimarker = mapa.addMarker(new MarkerOptions()
                    .position(centro)
                    .title(c.getString(1)));
            mimarker.showInfoWindow();
        }
    }
}

