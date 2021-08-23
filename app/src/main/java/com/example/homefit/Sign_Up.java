package com.example.homefit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_Up extends AppCompatActivity {
private static final String TAG = "Sign_Up";
    private FirebaseAuth mAuth;
    private Button signUp;
    private TextView goLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        goLogIn = findViewById(R.id.txtAlreadyhaveanaccount);

        goLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_Up.this,LogIn.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();

        signUp = findViewById(R.id.btnSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("클릭","클릭");
                signUp();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    private void signUp() {
        String email = ((EditText) findViewById(R.id.etEmail)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.etPw)).getText().toString().trim();
        String passwordCheck = ((EditText) findViewById(R.id.etPwc)).getText().toString().trim();
       if(email.length() >0 && password.length() > 0&& passwordCheck.length() > 0) {
           if (password.equals(passwordCheck)) {
               mAuth.createUserWithEmailAndPassword(email, password)
                       .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()) {
                                   // Sign in success, update UI with the signed-in user's information
                                   Log.d(TAG, "createUserWithEmail:success");
                                   FirebaseUser user = mAuth.getCurrentUser();
                                   startToast("Thanks for joining HomeFit");
                                   myStartActivity(LogIn.class);
                                   //성공
                               } else {
                                   // If sign in fails, display a message to the user.
                                   Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                   startToast("비밀번호를 제대로 입력했는지 확인해주세요.");
                                   //실패
                               }

                               // ...
                           }
                       });
           } else {
               startToast("Password Does Not Match");
           }
       }else
       {
           startToast("Enter your E-mail Address and Password");
       }
    }
    private void startToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c)
    {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
