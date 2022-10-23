package sv.edu.catolica.paradadebuses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class abrirDB extends SQLiteOpenHelper {
    public abrirDB(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table paradabuses(_id integer primary key autoincrement, nombre text, latitud text, longitud text, descripcion text, tipo text, foto text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists paradabuses");
        db.execSQL("create table paradabuses(_id integer primary key autoincrement, nombre text, latitud text, longitud text, descripcion text, tipo text, foto text)");
    }
}
