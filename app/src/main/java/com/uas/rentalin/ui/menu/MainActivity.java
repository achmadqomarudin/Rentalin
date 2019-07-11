package com.uas.rentalin.ui.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.uas.rentalin.R;
import com.uas.rentalin.ui.BookingActivity;
import com.uas.rentalin.ui.login.LoginActivity;
import com.uas.rentalin.ui.my_book.MyBookActivity;
import com.uas.rentalin.util.PrefManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView, rcCarCodeDHS;
    private FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder> adapter;
    private FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder2> adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCarCodeDHS();
        getCarCodeTYT();
//        getCarCodeSZK();
    }

    private void getCarCodeTYT() {
        mRecyclerView = findViewById(R.id.rc_code_car_tyt);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("admin").document("TYT").collection("data")
                .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PojoDataCar> options = new FirestoreRecyclerOptions.Builder<PojoDataCar>()
                .setQuery(query, PojoDataCar.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DataCarViewHolder holder, int position, @NonNull final PojoDataCar model) {

                holder.setNameCar(model.getType_car());
                holder.setSeats(model.getType_seats());
                holder.setEnginee(model.getType_enginee());
                holder.setPriceDay(model.getPrice_day());
                holder.btnBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), BookingActivity.class);
                        i.putExtra("price_day", model.getPrice_day());
                        i.putExtra("type_seats", model.getType_seats());
                        i.putExtra("type_enginee", model.getType_enginee());
                        i.putExtra("url_data", model.getUrl_data());
                        startActivity(i);
                    }
                });


                Glide.with(MainActivity.this)
                        .load(model.getUrl_data())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(holder.ivDataCar);
            }

            @NonNull
            @Override
            public DataCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_car, parent, false);
                return new DataCarViewHolder(view);
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    private void getCarCodeDHS() {
        rcCarCodeDHS = findViewById(R.id.rc_code_car_dhs);
        rcCarCodeDHS.setNestedScrollingEnabled(false);
        rcCarCodeDHS.setLayoutManager( new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("admin")/*.document("DHS").collection("data")*/
                .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PojoDataCar> options = new FirestoreRecyclerOptions.Builder<PojoDataCar>()
                .setQuery(query, PojoDataCar.class)
                .build();

        adapter2 = new FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DataCarViewHolder2 holder, int position, @NonNull final PojoDataCar model) {

                Toast.makeText(MainActivity.this, "Data DHS : "+ position, Toast.LENGTH_SHORT).show();

//                holder.setNameCar(model.getType_car());
//                holder.setSeats(model.getType_seats());
//                holder.setEnginee(model.getType_enginee());
//                holder.setPriceDay(model.getPrice_day());
//
//                Glide.with(MainActivity.this)
//                        .load(model.getUrl_data())
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
//                        .into(holder.ivDataCar);
            }

            @NonNull
            @Override
            public DataCarViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_car2, parent, false);
                return new DataCarViewHolder2(view);
            }
        };
        rcCarCodeDHS.setAdapter(adapter2);
    }

    private void getCarCodeSZK() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_booking:
                Intent i = new Intent(getApplicationContext(), BookingActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_book:

                startActivity(new Intent(getApplicationContext(), MyBookActivity.class));
                break;
            case R.id.profile:

                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:

                new AlertDialog.Builder(this)
                        .setMessage("Are you sure want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new PrefManager(getApplicationContext()).setEmail("");
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
        return true;
    }

    //njajal
    private class DataCarViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private LinearLayout container;
        private LinearLayout.LayoutParams params;
        private ImageView ivDataCar;
        private Button btnBooking;

        DataCarViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivDataCar = view.findViewById(R.id.iv_car);
            container = view.findViewById(R.id.container);
            btnBooking = view.findViewById(R.id.btn_booking);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        /**Hidden row item*/
        private void Layout_hide() {
            params.height = 0;
            //itemView.setLayoutParams(params); //This One.
            container.setLayoutParams(params);   //Or This one.
        }

        void setNameCar(String name_car) {
            TextView tvNameCar = view.findViewById(R.id.tv_type_car);
            tvNameCar.setText(name_car);
        }

        void setSeats(String seats) {
            TextView tvSeats = view.findViewById(R.id.tv_seats);
            tvSeats.setText(seats);
        }

        void setEnginee(String enginee) {
            TextView tvSeats = view.findViewById(R.id.tv_engine);
            tvSeats.setText(enginee);
        }

        void setPriceDay(String itemPrice) {
            TextView tvItemPrice = view.findViewById(R.id.price_day);
            tvItemPrice.setText(itemPrice);
        }
    }

    //njajal2
    private class DataCarViewHolder2 extends RecyclerView.ViewHolder {
        private View view;
        private LinearLayout container;
        private LinearLayout.LayoutParams params;
        private ImageView ivDataCar;

        DataCarViewHolder2(View itemView) {
            super(itemView);
            view = itemView;
            ivDataCar = view.findViewById(R.id.iv_car);
            container = view.findViewById(R.id.container);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        /**Hidden row item*/
        private void Layout_hide() {
            params.height = 0;
            //itemView.setLayoutParams(params); //This One.
            container.setLayoutParams(params);   //Or This one.
        }

        void setNameCar(String name_car) {
            TextView tvNameCar = view.findViewById(R.id.tv_type_car);
            tvNameCar.setText(name_car);
        }

        void setSeats(String seats) {
            TextView tvSeats = view.findViewById(R.id.tv_seats);
            tvSeats.setText(seats);
        }

        void setEnginee(String enginee) {
            TextView tvSeats = view.findViewById(R.id.tv_engine);
            tvSeats.setText(enginee);
        }

        void setPriceDay(String itemPrice) {
            TextView tvItemPrice = view.findViewById(R.id.price_day);
            tvItemPrice.setText(itemPrice);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
