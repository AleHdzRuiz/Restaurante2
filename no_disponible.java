package restaurante.unotaxi.com.restaurante;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class no_disponible extends AppCompatActivity {
    Button btnTrabajar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_disponible);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnTrabajar=(Button)findViewById(R.id.BtnTrabajar);
        btnTrabajar.setOnClickListener(new View.OnClickListener(){

                                           @Override
                                           public void onClick(View v) {
                                               Intent Trabajar = new Intent(no_disponible.this,solicitud_servicios.class);
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
