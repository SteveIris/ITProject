package com.example.asus.PerfectCircleITProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText passwordEditText;
    private EditText loginEditText;
    private EditText emailEditText;
    private Button signInButton;
    private TextView iAlreadyHaveAccountText;
    private boolean isRegistering=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        passwordEditText = findViewById(R.id.passwordEditText);
        loginEditText = findViewById(R.id.loginEditText);
        emailEditText = findViewById(R.id.emailEditText);
        signInButton = findViewById(R.id.signInButton);
        iAlreadyHaveAccountText = findViewById(R.id.iAlreadyHaveAnAccount);
        if(auth.getCurrentUser()!=null){
            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainActivity);
        };
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userSignIn(emailEditText.getText().toString().trim(), passwordEditText.getText().toString());
            }
        });
        iAlreadyHaveAccountText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isRegistering){
                    isRegistering=false;
                    signInButton.setText("Войти");
                    iAlreadyHaveAccountText.setText("У меня ещё нет аккаунта");
                    loginEditText.setText("");
                    loginEditText.setVisibility(View.INVISIBLE);
                    emailEditText.setText("");
                    passwordEditText.setText("");
                } else {
                    isRegistering=true;
                    signInButton.setText("Зарегистрироваться");
                    iAlreadyHaveAccountText.setText("У меня уже есть аккаунт");
                    loginEditText.setText("");
                    loginEditText.setVisibility(View.VISIBLE);
                    emailEditText.setText("");
                    passwordEditText.setText("");
                };
            }
        });
    }

    private void userSignIn(String email, String password) {
        if (isRegistering) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("loginACC", "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainActivity);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("loginACC", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        } else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("loginACC", "signInWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainActivity);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("loginACC", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Неправильная почта или пароль!",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        };
    };

}
