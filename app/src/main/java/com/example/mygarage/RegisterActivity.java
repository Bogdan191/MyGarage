package com.example.mygarage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfirmPassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextNameRegister);
        editTextEmail = findViewById(R.id.editTextEmailRegister);
        editTextPassword = findViewById(R.id.editTextPasswordRegister);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPasswordRegister);

        registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(view -> registerUser());

        mAuth = FirebaseAuth.getInstance();


    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if(name.isEmpty()) {
            editTextName.setError("Completeaza numele si prenumele!");
            editTextName.requestFocus();
            return;
        }
        if(name.length() < 6) {
            editTextName.setError("Numele este prea scurt pentru a fi un nume valid!");
            editTextName.requestFocus();
            return;
        }

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

        if(!password.equals(confirmPassword)) {
            editTextPassword.setError("Parolele nu se pottrivesc");
            editTextPassword.requestFocus();
            editTextPassword.setText("");
            editTextConfirmPassword.setText("");
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
            editTextConfirmPassword.setText("");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //we will store the additional field in firebase db
                            User user = new User(
                                    name,
                                    email
                            );
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Felicitari! Acum va puteti loga in aplicatie", Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Inregistrarea noului cont a esuat!\n Eroarea: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(RegisterActivity.this, "Inregistrarea noului cont a esuat!\n Eroarea: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

        startActivity(new Intent(this, LoginActivity.class));
    }
}
