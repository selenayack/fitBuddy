package com.example.fitbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
public class MainActivity extends AppCompatActivity {

    private EditText yeniKullaniciMail, yeniKullanicisifre, yeniKullaniciİsim;
    private String txtEmail, txtSifre, txtİsim;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private HashMap<String, Object> mData;
    private FirebaseFirestore mFirestore;
    private DocumentReference docRef; //tek bir kullanıcı verisi okumak için

    TextView forgotPassword, createAccount;

    private DbAdapter dbAdapter;

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyPrefs";
    private static final String USER_INFO_OPENED = "UserInfoOpened";


    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });



        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });







        forgotPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot, null);
            EditText emailBox = dialogView.findViewById(R.id.emailBox);

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            dialogView.findViewById(R.id.btnSifirla).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userEmail = emailBox.getText().toString();
                    if (!TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                        Toast.makeText(MainActivity.this, "Email id giriniz", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "E mailinizi kontrol edin", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(MainActivity.this, "Gönderim yapılamadı,failed", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });


                }
            });
            dialogView.findViewById(R.id.btnIptal).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
            }
            dialog.show();
        }
    });


    // Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();


}



    private void Login() {
        txtEmail = yeniKullaniciMail.getText().toString();
        txtSifre = yeniKullanicisifre.getText().toString();

        if (TextUtils.isEmpty(txtEmail)) {
            yeniKullaniciMail.setError("E-posta adresiniz boş olamaz!");
            return;
        } else if (TextUtils.isEmpty(txtSifre)) {
            yeniKullanicisifre.setError("Parolanız boş olamaz");
            return;
        } else {
            mAuth.signInWithEmailAndPassword(txtEmail, txtSifre).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        checkUserDataAndRedirect(user.getUid());
                    }
                }


            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
    private void checkUserDataAndRedirect(String userId) {
        dbAdapter.open();
        Cursor cursor = dbAdapter.select("USER", new String[]{"user_cinsiyet", "user_boy", "user_kilo", "user_yas"}, "user_id", userId);
        boolean isDataComplete = true;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    if (cursor.isNull(i)) {
                        isDataComplete = false;
                        break;
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            isDataComplete = false;
        }

        dbAdapter.close();

        if (isDataComplete) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(MainActivity.this, user_infos.class);
            startActivity(intent);
            finish();
        }
    }




    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            checkUserDataAndRedirect(currentUser.getUid());


        }
    }


    public void init() {
        createAccount = findViewById(R.id.createAccount);
        forgotPassword = findViewById(R.id.sifremiUnuttum);
        yeniKullaniciMail = findViewById(R.id.giris_yap_editmail);
        yeniKullanicisifre = findViewById(R.id.giris_yap_editSifre);
        yeniKullaniciİsim = findViewById(R.id.kayit_ol_editİsim);
        loginButton = findViewById(R.id.giris_btn);



        dbAdapter = new DbAdapter(this) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };



        // Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

    }
}