package restaurante.unotaxi.com.restaurante;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.IllegalFormatCodePointException;

import restaurante.unotaxi.com.restaurante.utils.ConectorServices;
import restaurante.unotaxi.com.restaurante.utils.ConexionBD;

public class inserta_codigo_verificacion extends AppCompatActivity {

    public static Context context;
    EditText codigo;
    Button btnAceptarcodigo;
    String codigoActiva="R48MW";
    String telefono ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserta_codigo_verificacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=getApplicationContext();
        codigo=(EditText)findViewById(R.id.txtIdVerificacion);
        btnAceptarcodigo=(Button)findViewById(R.id.BtnAceptarCodigo);

        telefono= getIntent().getExtras().getString("telefono");

        btnAceptarcodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Método toString para obtención del código
                try {
                    activaAplicacion(codigo.getText().toString());
                } catch (SQLException e) {
                    Log.e("Ops!... btnAceptarcodigo ", e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.e("Ops!... btnAceptarcodigo ", e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("Ops!... btnAceptarcodigo ", e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        IntentFilter filter = new IntentFilter("mensaje_sms");
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice,
                filter);

    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {

            if (i.hasExtra("action")) {



                try {
                    int j = Integer.parseInt(i.getStringExtra("action"));
                    if (j == 1) {
                        activaAplicacion(i.getStringExtra("action_code"));
                    }
                } catch (Exception e) {
                    Log.i("BroadcastReceiver   ", e.getMessage());
                }
            }
        }
    };
    private void activaAplicacion(String code) throws SQLException, IOException, JSONException {
        ConectorServices c= new ConectorServices(getApplicationContext());
        int respuesta = c.insertaCodigo(telefono, code);
        if (respuesta==0 ){
            imprime("El código no es correcto", "");
        }else if(respuesta>0){
            if (insertConductor(respuesta,telefono)){
                Intent codigoActiva = new Intent(inserta_codigo_verificacion.this,iniciar_jornada.class);
                startActivity(codigoActiva);
                this.finish();
            }else{
                Toast.makeText(this,"Error al insertar",Toast.LENGTH_LONG).show();
            }
        }

        if (code.equals(codigoActiva)){


        }else{
            Toast.makeText(this,"Error el código de aplicación no coincide",Toast.LENGTH_LONG).show();
        }
    }
    //Dialog para saber si llega el código
    private void imprime(String mensaje, String titulo) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setMessage(mensaje).setTitle(titulo);
        dialog.setCancelable(false);
        dialog.setPositiveButton("", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private boolean insertConductor(int  id , String telefono) throws SQLException {
        ConexionBD c= new ConexionBD(getApplicationContext());
        ContentValues datos= new ContentValues();
        datos.put("idConductor", id);
        datos.put("telefono", telefono);
        if (c.registrar("conductor", datos)){
            c.close();
            return true;
        }else{
            c.close();
            return  false;
        }
    }
}

