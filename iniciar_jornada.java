package restaurante.unotaxi.com.restaurante;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.io.IOException;

import restaurante.unotaxi.com.restaurante.utils.ConectorServices;

public class iniciar_jornada extends AppCompatActivity {
    EditText contraJor;
    Button BtnAceptar;
    Button BtnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_jornada);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contraJor=(EditText)findViewById(R.id.txtContrasenaJornada);
        BtnAceptar=(Button)findViewById(R.id.BtnAceptarJornada);
        BtnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iniciarJonada(contraJor.getText().toString());
                } catch (IOException e) {
                    Log.e("Ops!... BtnAceptar ", e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.e("Ops!... BtnAceptar ", e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        BtnCancelar= (Button)findViewById(R.id.BtnCancelarJornada);
        BtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Su GPS parece estar deshabilitado, ¿Desea Activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        startActivity(new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void iniciarJonada(String password) throws IOException, JSONException {

        ConectorServices c = new ConectorServices(getApplicationContext());

        if (password.length()>0) {


            int respuesta = c.insertJornada(1, password, 1, "lat", "lang", getVersionName());
            if (respuesta > 0) {
                Intent Jornada = new Intent(iniciar_jornada.this, solicitud_servicios.class);
                startActivity(Jornada);
                finish();

            } else {
                imprime("Verifique sus datos", "");
            }
        }else{
            imprime("Inserte una contraseña", "");
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

    //Dialog para saber si llega el código
    private void imprime(String mensaje, String titulo) {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);

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

}
