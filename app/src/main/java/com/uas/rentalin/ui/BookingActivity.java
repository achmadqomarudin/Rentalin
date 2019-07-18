package com.uas.rentalin.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uas.rentalin.R;
import com.uas.rentalin.util.ClassHelper;
import com.uas.rentalin.util.PrefManager;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNameTenant, etAdress, etNoKtp, etNoHp, etDays;
    public static final String NAME_TENANT = "name_tenant", ADDRESS = "address", NO_KTP = "no_ktp",
            NO_HP = "no_hp", CODE_CAR = "code_car", TYPE_CAR = "type_car", DAYS = "days", ID = "id",
            TOTAL_PRICE = "total_price", TYPE_SEATS = "type_seats", TYPE_ENGINEE = "type_enginee",
            URL_DATA = "url_data";
    private TextView tvTotalPrice;
    private Spinner spCarCode, spTypeCar;
    private ProgressDialog mRegProgress;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    int total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setTitle("Registration for booking");
        setView();
        codeCar();
        typeCar();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mRegProgress = new ProgressDialog(this);

        findViewById(R.id.btn_booking_registration).setOnClickListener(this);
    }

    private void setView() {
        etNameTenant = findViewById(R.id.et_name_tenant);
        etAdress = findViewById(R.id.et_address);
        etNoKtp = findViewById(R.id.et_no_ktp);
        etNoHp = findViewById(R.id.et_no_hp);
        etDays = findViewById(R.id.et_days);
        spCarCode = findViewById(R.id.sp_car_code);
        spTypeCar = findViewById(R.id.sp_type_car);
        tvTotalPrice = findViewById(R.id.tv_total_price);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_booking_registration:
                loadData();
                break;
        }
    }

    private void codeCar() {
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

    private void typeCar() {
        List<String> list = new ArrayList<>();
        list.add("Insert Type Car :");
        list.add("Inova");
        list.add("Avanza");
        list.add("Ferrari");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypeCar.setAdapter(dataAdapter);
    }

    private void loadData() {

        final String email = new PrefManager(getApplicationContext()).getEmail();
        final String id = getDatetime("yyMMddHHmmssSSS");
        String name_tenant = etNameTenant.getText().toString();
        String address = etAdress.getText().toString();
        String no_ktp = etNoKtp.getText().toString();
        String no_hp = etNoHp.getText().toString();
        String code_car = String.valueOf(spCarCode.getSelectedItem());
        String type_car = String.valueOf(spTypeCar.getSelectedItem());
        String days = etDays.getText().toString();
        String price_day = getIntent().getStringExtra("price_day");
        String type_seats = getIntent().getStringExtra("type_seats");
        String type_enginee = getIntent().getStringExtra("type_enginee");
        String url_data = getIntent().getStringExtra("url_data");

        if (days.length() <= 0) {
            Toast.makeText(this, "Sorry, all form must be filled", Toast.LENGTH_SHORT).show();

        } else if (days.length() >= 5) {
            total_price = ((Integer.parseInt(price_day) * Integer.parseInt(days)) / 100) * 5;
            tvTotalPrice.setText(String.valueOf(total_price));

            String total = tvTotalPrice.getText().toString();

            final Map<String, Object> userMap = new HashMap<>();
            userMap.put(NAME_TENANT, name_tenant);
            userMap.put(ADDRESS, address);
            userMap.put(NO_KTP, no_ktp);
            userMap.put(NO_HP, no_hp);
            userMap.put(CODE_CAR, code_car);
            userMap.put(TYPE_CAR, type_car);
            userMap.put(DAYS, days);
            userMap.put(TOTAL_PRICE, total);
            userMap.put(TYPE_SEATS, type_seats);
            userMap.put(TYPE_ENGINEE, type_enginee);
            userMap.put(URL_DATA, url_data);
            userMap.put(ID, id);

            if (!TextUtils.isEmpty(name_tenant) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(no_ktp) && !TextUtils.isEmpty(no_hp)
                    && !TextUtils.isEmpty(code_car) && !TextUtils.isEmpty(type_car) && !TextUtils.isEmpty(days)) {
                if (code_car.contentEquals("Insert Car Code :")) {

                    new ClassHelper().setToast(getApplicationContext(), "Sorry, you must choose code car");
                } else if (type_car.contentEquals("Insert Type Car :")) {

                    new ClassHelper().setToast(getApplicationContext(), "Sorry, you must choose type car");
                } else {

                    new AlertDialog.Builder(this.getApplicationContext())
                            .setMessage("Are you sure ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    mRegProgress.setMessage("Loading, please wait...");
                                    mRegProgress.setCanceledOnTouchOutside(false);
                                    mRegProgress.show();

                                    db.collection("user").document(email).collection("data").document(id).set(userMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mRegProgress.dismiss();
                                                    new ClassHelper().setToast(getApplicationContext(), "Booking successfully");
                                                    onBackPressed();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    mRegProgress.dismiss();
                                                    new ClassHelper().setToast(getApplicationContext(), "Sorry, please try again later...");
                                                }
                                            });
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mRegProgress.dismiss();
                            dialog.dismiss();
                        }
                    }).show();
                }
            } else {
                Toast.makeText(this, "Sorry, all form must be filled", Toast.LENGTH_SHORT).show();
            }

        } else {
            total_price = Integer.parseInt(days) * Integer.parseInt(price_day);
            tvTotalPrice.setText(String.valueOf(total_price));

            String total = tvTotalPrice.getText().toString();

            final Map<String, Object> userMap = new HashMap<>();
            userMap.put(NAME_TENANT, name_tenant);
            userMap.put(ADDRESS, address);
            userMap.put(NO_KTP, no_ktp);
            userMap.put(NO_HP, no_hp);
            userMap.put(CODE_CAR, code_car);
            userMap.put(TYPE_CAR, type_car);
            userMap.put(DAYS, days);
            userMap.put(TOTAL_PRICE, total);
            userMap.put(TYPE_SEATS, type_seats);
            userMap.put(TYPE_ENGINEE, type_enginee);
            userMap.put(URL_DATA, url_data);
            userMap.put(ID, id);

            if (!TextUtils.isEmpty(name_tenant) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(no_ktp) && !TextUtils.isEmpty(no_hp)
                    && !TextUtils.isEmpty(code_car) && !TextUtils.isEmpty(type_car) && !TextUtils.isEmpty(days)) {
                if (code_car.contentEquals("Insert Car Code :")) {

                    new ClassHelper().setToast(getApplicationContext(), "Sorry, you must choose code car");
                } else if (type_car.contentEquals("Insert Type Car :")) {

                    new ClassHelper().setToast(getApplicationContext(), "Sorry, you must choose type car");
                } else {

                    new AlertDialog.Builder(BookingActivity.this)
                            .setMessage("Are you sure ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    mRegProgress.setMessage("Loading, please wait...");
                                    mRegProgress.setCanceledOnTouchOutside(false);
                                    mRegProgress.show();

                                    db.collection("user").document(email).collection("data").document(id).set(userMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mRegProgress.dismiss();
                                                    new ClassHelper().setToast(getApplicationContext(), "Booking successfully");
                                                    onBackPressed();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    mRegProgress.dismiss();
                                                    new ClassHelper().setToast(getApplicationContext(), "Sorry, please try again later...");
                                                }
                                            });
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mRegProgress.dismiss();
                            dialog.dismiss();
                        }
                    }).show();
                }
            } else {
                Toast.makeText(this, "Sorry, all form must be filled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**id for data booking*/
    @SuppressLint("SimpleDateFormat")
    public static String getDatetime(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }
}
