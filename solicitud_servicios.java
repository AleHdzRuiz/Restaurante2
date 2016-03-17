package restaurante.unotaxi.com.restaurante;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class solicitud_servicios extends AppCompatActivity {
    Button BtnNoDisponible;
    Button BtnTerminarJornada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_servicios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BtnNoDisponible = (Button)findViewById(R.id.BtnNoDisponibleSolicitud);
        BtnNoDisponible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NoDispo = new Intent(solicitud_servicios.this,no_disponible.class);
                startActivity(NoDispo);
                finish();
            }
        });

        BtnTerminarJornada=(Button)findViewById(R.id.BtnTerminarJornadaSolicitud);
        BtnTerminarJornada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Trabajar = new Intent(solicitud_servicios.this,MapsActivity.class);
                startActivity(Trabajar);
                finish();
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
    }

}
