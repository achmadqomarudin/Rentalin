package com.uas.rentalin.ui.register;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uas.rentalin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLogin;
    private TextInputEditText etName, etUsername, etPassword, etEmail;
    private Button btnSignUp;
    private Spinner spinner;
    private ProgressDialog mRegProgress;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String USER_ID = "username", USER_NAME = "name", USER_EMAIL = "email", USER_PHONE = "phone", USER_TYPE = "type", USER_TOKEN = "token", KEY_PHOTO = "photo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mRegProgress = new ProgressDialog(this);

        setView();
        setOnClick();
    }

    private void setView() {
        btnSignUp = findViewById(R.id.btn_signup);
        etName = findViewById(R.id.edit_text_fullname);
        etUsername = findViewById(R.id.edit_text_username);
        etEmail = findViewById(R.id.edit_text_email);
        etPassword = findViewById(R.id.edit_text_password);
        tvLogin = findViewById(R.id.tv_login);
    }

    private void registerAs() {
        List<String> list = new ArrayList<>();
        list.add("Register As:");
        list.add("Register Admin");
        list.add("Super User");
        list.add("Manager");
        list.add("Staff");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void setOnClick() {
        tvLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(username)) {
                    initNewUserInfo(email, name, username, password);

                } else {
                    Toast.makeText(this, "Sorry, all form must be filled", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_login:

                onBackPressed();
                break;
        }
    }

    void initNewUserInfo(final String email, final String name, final String username, final String password/*,
            final String role*/) {
        mRegProgress.setMessage("Register, please wait...");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                final FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {

                    /* Add HashMap User */
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put(USER_NAME, name);
                    userMap.put(USER_EMAIL, email);
                    userMap.put(USER_ID, username);

                    /* Input to Firebase */
                    db.collection("user").document(email).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mRegProgress.dismiss();
                            Toast.makeText(getApplicationContext(), "Register success", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mRegProgress.dismiss();
                            Toast.makeText(getApplicationContext(), "Sorry, please try again later...", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                } else {
                    // User is signed out
                    mRegProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "User Logout", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}