package the.smartsecurity.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class PrimeraActividad extends AppCompatActivity implements View.OnClickListener{

    private Button botonLogin;
    private EditText textoUsuario;
    private EditText textoContrasena;
    private TextView textoRegistrado;
    private Firebase baseDatos;

    private ProgressDialog dialogoProceso;

    private FirebaseAuth autFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_actividad);

        autFireBase = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        baseDatos = new Firebase("https://smartsecurityapp.firebaseio.com/");

        if(autFireBase.getCurrentUser() != null){
            //actividad de perfil aqui
            finish();
            startActivity(new Intent(getApplicationContext(),PerfilActividad.class));
        }


        dialogoProceso = new ProgressDialog(this);

        textoUsuario = (EditText)findViewById(R.id.textoUsuario);
        textoContrasena = (EditText)findViewById(R.id.textoContrasena);
        botonLogin = (Button)findViewById(R.id.botonLogin);
        textoRegistrado = (TextView)findViewById(R.id.textoRegistrado);


        botonLogin.setOnClickListener(this);
        textoRegistrado.setOnClickListener(this);
    }

    private void usuarioLogin(){
        String usuario = textoUsuario.getText().toString().trim();
        String contrasena = textoContrasena.getText().toString().trim();

        if(/*TextUtils.isEmpty(correo) ||*/ TextUtils.isEmpty(contrasena) || TextUtils.isEmpty(usuario)){
            Toast.makeText(this, "Te faltan datos por capturar, revisa que esté completa tu información.", Toast.LENGTH_SHORT).show();
            //Parar la funcion objeto
            return;
        }

        dialogoProceso.setMessage("Iniciando Sesión...");
        dialogoProceso.show();

        autFireBase.signInWithEmailAndPassword(usuario, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialogoProceso.dismiss();
                        if(task.isSuccessful()){
                            //empezar el login activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),PerfilActividad.class));
                        } else{
                            Toast.makeText(PrimeraActividad.this, "Datos de inicio de sesión incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == botonLogin){
            usuarioLogin();
        }

        if(view == textoRegistrado){
            //irse al registroactividad
            finish();
            startActivity(new Intent(this, RegistroActividad.class));
        }
    }
}
