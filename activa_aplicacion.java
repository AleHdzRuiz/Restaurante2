package restaurante.unotaxi.com.restaurante;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

import restaurante.unotaxi.com.restaurante.utils.ConectorServices;
import restaurante.unotaxi.com.restaurante.utils.ConexionBD;
import restaurante.unotaxi.com.restaurante.utils.Tools;

public class activa_aplicacion extends AppCompatActivity {
    String operatorName="";
    String sO="";
    String device="";
    String modelo="";
    EditText numero;
    Button btnAceptarActiva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activa_aplicacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        numero=(EditText)findViewById(R.id.txtTelefono);
        btnAceptarActiva=(Button)findViewById(R.id.BttnAceptarActiva);

        //Crear evento
        btnAceptarActiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambiar de activity para ver código de verificación
                try {
                    activaAplicacion();
                } catch (IOException e) {
                    Log.e("Ops!...btnAceptarActia ", e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.e("Ops!...btnAceptarActva ", e.getMessage());
                    e.printStackTrace();
                }
            }
});

        this.verificarAplicacionActiva();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activa_aplicacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void verificarAplicacionActiva(){
        ConexionBD c= new ConexionBD(getApplicationContext());
        String usuario="";
        Cursor cursor = c.extraerDatosGeneral("conductor", new String[]{"idConductor", "telefono"});
        while(cursor.moveToNext()){
            usuario=cursor.getString(0);
        }
        c.close();
        cursor.close();
        this.enviarJornada(usuario);
    }

    private void verficarBaseDatos() throws SQLException {
        ConexionBD c= new ConexionBD(getApplicationContext());
        String usuario="";
        Cursor cursor = c.extraerDatosGeneral("tipoBd", new String[]{"Bd"});
        while(cursor.moveToNext()){
            usuario=cursor.getString(0);
        }
        c.close();
        cursor.close();
        this.registraBd(usuario);
    }

    private void registraBd(String datobd) throws SQLException {
        if (datobd.equals("Pro")){

        }else if(datobd.equals("Demo")){

        }else{
            insertBd("Demo");
        }
    }
    private boolean insertBd(String  Bd ) throws SQLException {
        ConexionBD c= new ConexionBD(getApplicationContext());
        ContentValues datos= new ContentValues();
        datos.put("Bd", Bd);
        if (c.registrar("tipoBd", datos)){
            c.close();
            return true;
        }else{
            c.close();
            return  false;
        }
    }

    private void enviarJornada(String dato){
        if (dato.equals("")){

        }else if (dato.length()>0){
            Intent enviaJorna = new Intent(activa_aplicacion.this,iniciar_jornada.class);
            startActivity(enviaJorna);
            this.finish();
        }
    }

    private void activaAplicacion() throws IOException, JSONException {
        String telefono=numero.getText().toString();

        ConectorServices con = new ConectorServices(getApplicationContext());
        if (telefono.equals("")){
            imprime("Inserta un número de teléfono","");
        }else  if (telefono.length()==10){
            boolean respuesta = con.activaConector(telefono,sO,"GCM", device, modelo, operatorName, getVersionName()," storeProcedure");
            if (respuesta == false){
                imprime("Su número no está registrado","");
               // Tools.alertGeneric(dialogo.getContext(), "Información", "Su número no está registrado", "Aceptar");
            }else{

                Intent enviaJorna = new Intent(activa_aplicacion.this,inserta_codigo_verificacion.class);
                enviaJorna.putExtra("telefono", telefono);
                startActivity(enviaJorna);
                this.finish();
            }
        }else  if (numero.length()!=10) {
            imprime("Inserte un número de 10 dígitos", "");
        }
    }

    //Dialog para saber si llega el código
    private void imprime(String mensaje, String titulo) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setMessage(mensaje).setTitle(titulo);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    public void info() {
        try {
            TelephonyManager mTelephonyManager;
            mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            // device += System.getProperty("os.version"); // OS version

            operatorName = mTelephonyManager.getSimOperatorName();// proveedor
            sO += System.getProperty("os.version"); // OS version
            device += android.os.Build.DEVICE; // Device
            modelo = android.os.Build.MODEL; // Model
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getVersionName() {
        String in = "";
        PackageManager manager = getApplicationContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getApplicationContext()
                    .getPackageName(), 0);

            in = info.versionName;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return in;
    }

}
