package sv.edu.catolica.paradadebuses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ejecutarSalir(View view) {
        System.exit(0);
    }

    public void ejecutarAgregar (View view) {
        Intent obj = new Intent(this,Agregar.class);
        this.startActivity(obj);
    }

    public void ejecutarLista (View view) {
        Intent obj = new Intent(this,Lista.class);
        this.startActivity(obj);
    }

    public void ejecutarAcercaDe (View view) {
        Intent obj = new Intent(this,AcercaDe.class);
        this.startActivity(obj);
    }

}