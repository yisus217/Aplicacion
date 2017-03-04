package the.smartsecurity.app;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;




public class PrimeraActividad extends AppCompatActivity implements View.OnClickListener{

    private TextView textoIrLogin;
    private Button botonIrRegistroCliente;
    private Button botonIrRegistroServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_actividad);

        textoIrLogin = (TextView)findViewById(R.id.textoIrLogin);
        botonIrRegistroCliente = (Button)findViewById(R.id.botonIrRegistroCliente);
        botonIrRegistroServicio = (Button)findViewById(R.id.botonIrRegistroServicio);


        textoIrLogin.setOnClickListener(this);
        botonIrRegistroCliente.setOnClickListener(this);
        botonIrRegistroServicio.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {
        if(view == textoIrLogin){
            finish();
            startActivity(new Intent(this, LoginActividad.class));
        }

        if(view == botonIrRegistroCliente || view == botonIrRegistroServicio ){
            //irse al registroactividad
            finish();
            startActivity(new Intent(this, RegistroActividad.class));
        }
    }
}
