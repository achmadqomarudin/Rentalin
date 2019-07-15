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
import android.widget.TextView;

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

    private RecyclerView rcCarCodeTYT, rcCarCodeDHS, rcCarCodeSZK;
    private FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder> adapterTYT;
    private FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder2> adapterDHS;
    private FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder3> adapterSZK;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCarCodeTYT();
        getCarCodeDHS();
        getCarCodeSZK();
    }

    private void getCarCodeTYT() {
        rcCarCodeTYT = findViewById(R.id.rc_code_car_tyt);
        rcCarCodeTYT.setHasFixedSize(true);
        rcCarCodeTYT.setLayoutManager( new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("admin").document("TYT").collection("data")
                .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PojoDataCar> options = new FirestoreRecyclerOptions.Builder<PojoDataCar>()
                .setQuery(query, PojoDataCar.class)
                .build();

        adapterTYT = new FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder>(options) {
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
                        .error(R.drawable.place_holder_error)
                        .placeholder(R.drawable.load_place_holder)
                        .into(holder.ivDataCar);
            }

            @NonNull
            @Override
            public DataCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_car, parent, false);
                return new DataCarViewHolder(view);
            }
        };
        rcCarCodeTYT.setAdapter(adapterTYT);
    }

    private void getCarCodeDHS() {
        rcCarCodeDHS = findViewById(R.id.rc_code_car_dhs);
        rcCarCodeDHS.setNestedScrollingEnabled(false);
        rcCarCodeDHS.setLayoutManager( new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("admin").document("DHS").collection("data")
                .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PojoDataCar> options = new FirestoreRecyclerOptions.Builder<PojoDataCar>()
                .setQuery(query, PojoDataCar.class)
                .build();

        adapterDHS = new FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DataCarViewHolder2 holder, int position, @NonNull final PojoDataCar model) {

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
                        .error(R.drawable.place_holder_error)
                        .placeholder(R.drawable.load_place_holder)
                        .into(holder.ivDataCar);
            }

            @NonNull
            @Override
            public DataCarViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_car2, parent, false);
                return new DataCarViewHolder2(view);
            }
        };
        rcCarCodeDHS.setAdapter(adapterDHS);
    }

    private void getCarCodeSZK() {
        rcCarCodeSZK = findViewById(R.id.rc_code_car_szk);
        rcCarCodeSZK.setHasFixedSize(true);
        rcCarCodeSZK.setLayoutManager( new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("admin").document("SZK").collection("data")
                .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PojoDataCar> options = new FirestoreRecyclerOptions.Builder<PojoDataCar>()
                .setQuery(query, PojoDataCar.class)
                .build();

        adapterSZK = new FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DataCarViewHolder3 holder, int position, @NonNull final PojoDataCar model) {

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
                        .error(R.drawable.place_holder_error)
                        .placeholder(R.drawable.load_place_holder)
                        .into(holder.ivDataCar);
            }

            @NonNull
            @Override
            public DataCarViewHolder3 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_data_car3, viewGroup, false);
                return new DataCarViewHolder3(view);
            }
        };
        rcCarCodeSZK.setAdapter(adapterSZK);
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
//            case R.id.profile:
//
//                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
//                break;
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

    /**ViewHolder Car TYT*/
    private class DataCarViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView ivDataCar;
        private Button btnBooking;

        DataCarViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivDataCar = view.findViewById(R.id.iv_car);
            btnBooking = view.findViewById(R.id.btn_booking);
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

    /**ViewHolder Car DHS*/
    private class DataCarViewHolder2 extends RecyclerView.ViewHolder {
        private View view;
        private ImageView ivDataCar;
        private Button btnBooking;

        DataCarViewHolder2(View itemView) {
            super(itemView);
            view = itemView;
            ivDataCar = view.findViewById(R.id.iv_car);
            btnBooking = view.findViewById(R.id.btn_booking);
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

    /**ViewHolder Car SZK*/
    private class DataCarViewHolder3 extends RecyclerView.ViewHolder {
        private View view;
        private ImageView ivDataCar;
        private Button btnBooking;

        DataCarViewHolder3(View itemView) {
            super(itemView);
            view = itemView;
            ivDataCar = view.findViewById(R.id.iv_car);
            btnBooking = view.findViewById(R.id.btn_booking);
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
        adapterTYT.startListening();
        adapterDHS.startListening();
        adapterSZK.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapterTYT != null || adapterDHS != null || adapterSZK != null) {
            adapterTYT.stopListening();
            adapterDHS.stopListening();
            adapterSZK.stopListening();
        }
    }
}
