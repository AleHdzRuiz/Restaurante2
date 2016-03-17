package restaurante.unotaxi.com.restaurante.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Ale on 09/03/2016.
 */
public class ConexionBD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "conductoresRestaurante";
    //Se realiza la conexión
    public ConexionBD(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Creamos la tabla conductor
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CAR_TABLE = "CREATE TABLE conductor (idConductor INTEGER PRIMARY KEY,telefono TEXT)";
        db.execSQL(CAR_TABLE);

        String BD_TABLE = "CREATE TABLE tipoBd (Bd TEXT)";
        db.execSQL(BD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXISTS ");
        this.onCreate(db);
    }
    //Registrar
    public boolean registrar(String nombreTabla, ContentValues datos) throws SQLException {
        this.getWritableDatabase().insert(nombreTabla, null, datos);
        return true;

    }
    //Seleccionar Conductores
    public Cursor selectConductores(){
        String Query = "SELECT * FROM CAR ORDER BY idConductor";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        return cursor;
    }

    public Cursor extraerDatosGeneral(String nombreTabla, String[] parametros) {
        Cursor datos = this.getWritableDatabase().query(nombreTabla,
                parametros, null, null, null, null, null);
        return datos;
    }

    public String getCountTipoBd() {
        Cursor c = null;
        try {
            String query = "SELECT bd from TipoBDChofer";
            c = this.getWritableDatabase().rawQuery(query, null, null);
            if (c != null && c.moveToFirst()) {
                String s = c.getString(0);
                c.close();
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.close();
        return "";

    }
}

