package com.uas.rentalin.ui.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uas.rentalin.ui.AdminActivity;
import com.uas.rentalin.ui.menu.MainActivity;
import com.uas.rentalin.R;
import com.uas.rentalin.model.User;
import com.uas.rentalin.ui.register.SignUpActivity;
import com.uas.rentalin.util.ClassHelper;
import com.uas.rentalin.util.PrefManager;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSignUp, tvForgotPassword;
    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private Dialog dialog;
    PrefManager prefManager;
    private ProgressDialog mRegProgress;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefManager = new PrefManager(this);
        if (!prefManager.getEmail().isEmpty()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        mRegProgress = new ProgressDialog(this);

        setView();
        setOnClick();
    }

    private void setView() {
        etEmail = findViewById(R.id.edit_text_email);
        etPassword = findViewById(R.id.edit_text_password);
        btnLogin = findViewById(R.id.btn_login);
        tvSignUp = findViewById(R.id.tv_signup);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
    }

    private void setOnClick() {
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    signIn(email, password);
                } else {
                    new ClassHelper().setToast(getApplicationContext(),"Email or password cannot be empty!");
                }
                break;
            case R.id.tv_signup:
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                break;
            case R.id.tv_forgot_password:
                dialogResetPassword();
                break;
        }
    }

    void signIn(final String email, final String password) {
        mRegProgress.setMessage("Login, please wait..");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mRegProgress.dismiss();
                        if (!task.isSuccessful()) {
                            new ClassHelper().setToast(getApplicationContext(),"Login failed! Please check your email and password!");
                        } else {
                            loadData(email);
                        }
                    }
                });
    }

    private void loadData(String email) {
        if (email.contentEquals("admin@gmail.com")) {

            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
        } else {

            db.collection("user")
                    .document(email)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.e("loadData: onSuccess ", documentSnapshot.toString());
                            User user = documentSnapshot.toObject(User.class);
                            if (user != null) {

                                new PrefManager(getApplicationContext()).setEmail(user.getEmail());
                                new PrefManager(getApplicationContext()).setName(user.getName());
                                new PrefManager(getApplicationContext()).setRole(user.getType());
                                new PrefManager(getApplicationContext()).setPhoto(user.getPhoto());
                                new ClassHelper().setToast(getApplicationContext(),"Login success!");
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                new ClassHelper().setToast(getApplicationContext(),"Login failed! Your account doesn't exist!");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new ClassHelper().setToast(getApplicationContext(),"Login failed! Please try again!");
                        }
                    });
        }
    }

    void dialogResetPassword() {
        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_reset_password);
        dialog.setCanceledOnTouchOutside(false);
        ImageButton mExitDialog = dialog.findViewById(R.id.exit_dialog);
        Button mBtnReset = dialog.findViewById(R.id.btn_reset_password);
        final TextInputEditText tvEmail = dialog.findViewById(R.id.edit_text_email);
        tvEmail.setText(etEmail.getText().toString());

        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = tvEmail.getText().toString();

                if (!TextUtils.isEmpty(email)) {

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Check email to reset your password!",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Fail to send reset password email!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "field cannot be empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mExitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}
