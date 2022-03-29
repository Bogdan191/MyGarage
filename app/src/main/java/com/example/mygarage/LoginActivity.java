package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity  {

    Button loginButton;
    Button registerActivity;
    Button forgotPassword;

    EditText editTextEmail;
    EditText editTextPassword;

    FirebaseAuth mAuth;

    @Override
    protected  void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.buttonLoginActivity);
        registerActivity = findViewById(R.id.buttonGoToRegisterActivity);
        forgotPassword = findViewById(R.id.buttonGoToForgotPassword);

        editTextEmail = findViewById(R.id.textEmailLogin);
        editTextPassword = findViewById(R.id.textPasswordLogin);

        mAuth = FirebaseAuth.getInstance();

        try {
            loginButton.setOnClickListener(view-> Login());
            registerActivity.setOnClickListener(view -> goToRegisterActivity());
            forgotPassword.setOnClickListener(view -> goToForgotPassActivity());
        }catch(Exception e){
            Toast.makeText(this, "Error on clicking buttons event!", Toast.LENGTH_LONG).show();
        }
    }

    private void Login() {

        String email = editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Completeaza email-ul!");
            editTextEmail.requestFocus();
            return;
        }
        if(!email.matches("^(.+)@(.+)$")) {
            editTextEmail.setError("Email invalid!");
            editTextEmail.requestFocus();
            return;
        }
        String insecurePass = "Parola nu este sigura! Aceasta trebuie sa indeplineasca urmatoarele conditii:\n" +
                "1. sa contina cel putin o cifra \n" +
                "2. sa contina cel putin o litera mica\n" +
                "3. sa contina cel putin o litera mare\n" +
                "4. sa contina cel putin un caracter special, de tipu: ! @ # & ( )\n" +
                "5. trebuie sa aiba o lungima de cel putin 8 caractere si cel mult 20";

        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")){
            editTextPassword.setError(insecurePass);
            editTextPassword.requestFocus();
            editTextPassword.setText("");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    goToMainActivity();
                    Toast.makeText(LoginActivity.this, "Logare reusita!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Logare esuata!( " + task.getException().getMessage()+" ).", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    private void goToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    private void goToForgotPassActivity() {
        Toast.makeText(this, "The 'Forgot Password' form is not implemented yet!", Toast.LENGTH_LONG).show();
    }
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
