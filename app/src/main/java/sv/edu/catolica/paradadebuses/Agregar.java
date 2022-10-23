package sv.edu.catolica.paradadebuses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agregar extends AppCompatActivity {

    private final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private EditText txtNombre, txtLatitud, txtLongitud, txtDescripcion;
    private ImageView img;
    private Spinner spinner;
    private String attachFileName="";
    private ContentValues values;
    private Uri imageUri;
    private static final int PICTURE_RESULT=122;
    private Bitmap thumbnail;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtLatitud = (EditText) findViewById(R.id.txtLatitud);
        txtLongitud = (EditText) findViewById(R.id.txtLongitud);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        img = (ImageView) findViewById(R.id.foto);
        spinner = (Spinner) findViewById(R.id.spinner);


    List<String> categorias = new ArrayList<String>();
        categorias.add("Parada Particular ");
        categorias.add("Parada Informal");
        categorias.add("Parada Escolar");
        categorias.add("Terminal");
    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        if (Build.VERSION.SDK_INT >= 23) {
        System.out.println("Solicitamos Permiso" + Build.VERSION.SDK_INT);
        checkPermission();

        }

    locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    private void checkPermission() {

        List<String> permission = new ArrayList<>();
        // PERMISO UBICACION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        //PERMISO A CAMARA
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.CAMERA);
        }
        // PERMISOS DE ESCRUTRA EXTERNA
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permission.isEmpty()) {
            String [] params = permission.toArray(new String[permission.size()]);
            requestPermissions(params,REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }

    }

    //Codigo de respuesta cuando los permisos son otorgados o rechazados.

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                Boolean location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if (location && storage) {
                    System.out.println("All permissions granted");

                } else if (location) {
                    System.out.println("Storage permission is required to store map tiles to reduce data usage and for offline usage.");
                } else if (storage) {
                    System.out.println("Location permission is required to show the user's location on map.");
                } else {
                    System.out.println("Storage permission is required to store map tiles to reduce data usage and for offline usage." +
                            "\nLocation permission is required to show the user's location on map.");
                    System.exit(0);

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void capturarLatLng (View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc==null) {
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (loc!=null) {
            txtLatitud.setText(String.valueOf(loc.getLatitude()));
            txtLongitud.setText(String.valueOf(loc.getLongitude()));
        }
    }

    //Codigo para capturar foto desde la camara

    public void anexarFoto (View view) throws IOException {
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Foto de Para de Bus");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Foto Capturada en " + System.currentTimeMillis());
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PICTURE_RESULT);
    }

    @Override
    public void onActivityResult (int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_RESULT) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                img.setImageBitmap(thumbnail);
                attachFileName = getRealPathFromURI(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void guardarDatos (View view) {
        abrirDB base= new abrirDB(this,"paradaB",null,1);
        SQLiteDatabase bd= base.getWritableDatabase();
        String nombre=txtNombre.getText().toString();
        String latitud=txtLatitud.getText().toString();
        String longitud=txtLongitud.getText().toString();
        String descrip=txtDescripcion.getText().toString();
        String tipo=spinner.getSelectedItem().toString();
        ContentValues registro=new ContentValues();
        registro.put("nombre", nombre);
        registro.put("latitud", latitud);
        registro.put("longitud", longitud);
        registro.put("descripcion", descrip);
        registro.put("tipo", tipo);
        registro.put("foto", attachFileName);
        bd.insert("paradabuses", null, registro);
        txtNombre.setText("");
        txtLongitud.setText("");
        txtLatitud.setText("");
        txtDescripcion.setText("");
        img.setImageDrawable(null);
        bd.close();
        Toast.makeText(this, "Se cargaron los datos correctamente",
                Toast.LENGTH_SHORT).show();
    }




}