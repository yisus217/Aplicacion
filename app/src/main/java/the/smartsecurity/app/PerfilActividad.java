package the.smartsecurity.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class PerfilActividad extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth autFireBase;

    private TextView textViewUsuarioCorreo;
    private Button botonLogout, botonGuardar;
    private EditText nombreEditText;
    private EditText direccionEditText;
    private DatabaseReference databaseReference;
    private FirebaseDatabase baseDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_actividad);

        autFireBase = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        baseDatos = FirebaseDatabase.getInstance();


        if(autFireBase.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, PrimeraActividad.class));
        }

        //inicializando views
        direccionEditText = (EditText)findViewById(R.id.direccionEditText);
        nombreEditText = (EditText)findViewById(R.id.nombreEditText);
        botonGuardar = (Button)findViewById(R.id.botonGuardar);

        FirebaseUser usuario = autFireBase.getCurrentUser();


        textViewUsuarioCorreo = (TextView)findViewById(R.id.textViewUsuarioCorreo);
        textViewUsuarioCorreo.setText("Bienvenido "+usuario.getEmail());


        botonLogout = (Button)findViewById(R.id.botonLogout);
        //agregando listener a los botones
        botonLogout.setOnClickListener(this);
        botonGuardar.setOnClickListener(this);
    }

    private void guardarInformacionUsuario(){
        String nombre = nombreEditText.getText().toString().trim();
        String direccion = direccionEditText.getText().toString().trim();

        InformacionUsuario informacionUsuario = new InformacionUsuario(direccion, nombre);
        FirebaseUser usuario = autFireBase.getCurrentUser();

        Toast.makeText(this, "Información Guardada", Toast.LENGTH_SHORT).show();
        databaseReference.child("clientes").child(usuario.getUid()).setValue(informacionUsuario);
    }

    @Override
    public void onClick(View view) {
        if(view == botonLogout){
            autFireBase.signOut();
            finish();
            startActivity(new Intent(this,PrimeraActividad.class));
        }

        if(view == botonGuardar){
            guardarInformacionUsuario();
        }
    }


}



