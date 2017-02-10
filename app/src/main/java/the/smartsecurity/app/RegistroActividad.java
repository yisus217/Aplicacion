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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActividad extends AppCompatActivity implements View.OnClickListener{

    private Button botonRegister;
    private EditText textoUsuario;
    private EditText textoContrasena;
    private TextView textoLogeado;

    private ProgressDialog dialogoProceso;

    private FirebaseAuth autFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_actividad);

        autFireBase = FirebaseAuth.getInstance();

        dialogoProceso = new ProgressDialog(this);

        botonRegister = (Button)findViewById(R.id.botonLogin); //boton para mandar a registrar
        textoUsuario = (EditText)findViewById(R.id.textoUsuario); //campo usuario
        textoContrasena = (EditText)findViewById(R.id.textoContrasena); //campo contraseña
        textoLogeado = (TextView)findViewById(R.id.textoLogeado); //boton/text: ya tiene cuenta??

        botonRegister.setOnClickListener(this);
        textoLogeado.setOnClickListener(this);
    }

    private void usuarioRegistro(){
        String usuario = textoUsuario.getText().toString().trim();
        String contrasena = textoContrasena.getText().toString().trim();
        //String correo = textoCorreo.getText().toString().trim();

        if(/*TextUtils.isEmpty(correo) ||*/ TextUtils.isEmpty(contrasena) || TextUtils.isEmpty(usuario)){
            Toast.makeText(this, "Te faltan datos por capturar, revisa que esté completa tu información.", Toast.LENGTH_SHORT).show();
            //Parar la funcion objeto
            return;
        }

        dialogoProceso.setMessage("Registrando cuenta...");
        dialogoProceso.show();

        autFireBase.createUserWithEmailAndPassword(usuario,contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Cuenta registrada correctamente y se ha logeado
                            finish();
                            startActivity(new Intent(getApplicationContext(),PerfilActividad.class));
                            Toast.makeText(RegistroActividad.this, "Cuenta registrada correctamente", Toast.LENGTH_SHORT).show();
                        }else{
                            dialogoProceso.dismiss();
                            Toast.makeText(RegistroActividad.this, "Registro fallido. Por favor intente nuevamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onClick(View view){
        if(view == botonRegister){
            //registrar la cuenta en firebase
            usuarioRegistro();
        }

        if(view == textoLogeado){
            //irse a la actividad login
            startActivity(new Intent(this, PrimeraActividad.class));
        }

    }

}
