package com.uas.rentalin.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uas.rentalin.R;
import com.uas.rentalin.util.ClassHelper;
import com.uas.rentalin.util.PrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNameCar, etPriceDay, etSeats;
    private Spinner spTypeEngineeCar, spCarCode;
    private ImageButton btnAdd;
    private Button btnAddData;
    TextView tvNotification;
    Uri pdfUri;
    ProgressDialog progressDialog;
    public static final String TYPE_CAR = "type_car", PRICE_DAY = "price_day", TYPE_SEATS = "type_seats",
            ATTACHMENT = "attachment", ID = "id", URL_DATA = "url_data", TYPE_ENGINEE = "type_enginee",
            CODE_CAR = "code_car";
    FirebaseStorage firebaseStorage;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("admin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setView();
        setOnCLick();
        insertEngineeCar();
        insertCarCode();

        firebaseStorage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance().collection("admin").getFirestore();
    }

    private void setView() {
        btnAdd = findViewById(R.id.btn_add_image);
        tvNotification = findViewById(R.id.tv_notification);
        etNameCar = findViewById(R.id.et_name_car);
        etPriceDay = findViewById(R.id.et_price_per_day);
        etSeats = findViewById(R.id.et_seats);
        btnAddData = findViewById(R.id.btn_add_data);
        spTypeEngineeCar = findViewById(R.id.sp_enginee_car);
        spCarCode = findViewById(R.id.sp_code_car);
    }

    private void setOnCLick() {
        btnAddData.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    private void insertEngineeCar() {
        List<String> list = new ArrayList<>();
        list.add("Insert Type Enginee Car :");
        list.add("Automatic");
        list.add("Manual");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypeEngineeCar.setAdapter(dataAdapter);
    }

    private void insertCarCode() {
        List<String> list = new ArrayList<>();
        list.add("Insert Car Code :");
        list.add("TYT");
        list.add("DHS");
        list.add("SZK");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCarCode.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_image:
                addDataImage();
                break;
            case R.id.btn_add_data:
                postData();
                break;
        }
    }

    private void addDataImage() {
        if (ContextCompat.checkSelfPermission(AdminActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            selectImage();
        } else {
            ActivityCompat.requestPermissions(AdminActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else {
            Toast.makeText(this, "please provide permission...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 86 && resultCode == RESULT_OK && data!= null) {
            pdfUri = data.getData();
            tvNotification.setText(data.getData().getLastPathSegment());
        } else {
            Toast.makeText(this, "Please select a file...", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * post data
     **/
    private void postData() {

        String name_car = etNameCar.getText().toString();
        String price_day = etPriceDay.getText().toString();
        String attachment = tvNotification.getText().toString();
        String seats = etSeats.getText().toString();
        String sp_type_enginee_car = String.valueOf(spTypeEngineeCar.getSelectedItem());
        String sp_code_car = String.valueOf(spCarCode.getSelectedItem());

        if (!TextUtils.isEmpty(name_car) && !TextUtils.isEmpty(price_day) && !TextUtils.isEmpty(attachment)
                && !TextUtils.isEmpty(seats) && !TextUtils.isEmpty(sp_type_enginee_car)
                && !TextUtils.isEmpty(sp_code_car)) {

            final String id = getDatetime("yyMMddHHmmssSSS");

            if (pdfUri!=null)
                uploadFile(pdfUri, id);
            else
                Toast.makeText(this, "Select a file...", Toast.LENGTH_SHORT).show();

            final Map<String, Object> userMap = new HashMap<>();
            userMap.put(TYPE_CAR, name_car);
            userMap.put(PRICE_DAY, "Rp. "+price_day);
            userMap.put(ATTACHMENT, attachment);
            userMap.put(TYPE_SEATS, seats+" seats");
            userMap.put(TYPE_ENGINEE, sp_type_enginee_car);
            userMap.put(CODE_CAR, sp_code_car);
            userMap.put(ID, id);

            collectionReference.document(sp_code_car).collection("data").document(id).set(userMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            etNameCar.setText(null);
                            etPriceDay.setText(null);
                            tvNotification.setText(null);
                            etSeats.setText(null);
                            new ClassHelper().setToast(getApplicationContext(), "Success uploading data");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    new ClassHelper().setToast(getApplicationContext(), "Failed uploading data");
                }
            });
        } else {
            new ClassHelper().setToast(getApplicationContext(), "Sorry, all form must be filled");
        }
    }

    private void uploadFile(Uri pdfUri, final String id) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis()+"";

        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Uploads").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.child("Uploads").child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                final String url = uri.toString();
                                final String uuiid = id;
                                final String code_car = String.valueOf(spCarCode.getSelectedItem());

                                collectionReference.document(code_car).collection("data").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {

                                            String uid = documentSnapshot.getId();

                                            if (uid.contentEquals(uuiid)) {

                                                String url_data = url;

                                                    final Map<String, Object> userMap = new HashMap<>();
                                                    userMap.put(URL_DATA, url_data);

                                                    db.collection("admin").document(code_car).collection("data").document(uid).update(userMap);

                                            }
                                        }
                                    }
                                });
                                progressDialog.dismiss();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("loadData: onFailure", e.getMessage());
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDatetime(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }
}
