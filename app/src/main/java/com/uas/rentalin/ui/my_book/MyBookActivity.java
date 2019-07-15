package com.uas.rentalin.ui.my_book;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.uas.rentalin.R;
import com.uas.rentalin.ui.menu.MainActivity;
import com.uas.rentalin.util.PrefManager;

public class MyBookActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirestoreRecyclerAdapter<PojoMyBook, MyBookViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book);
        setTitle("My Book");

        mRecyclerView = findViewById(R.id.rc_my_book);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(MyBookActivity.this));

        /** Get Data Firestore **/
        String email = new PrefManager(getApplicationContext()).getEmail();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("user").document(email).collection("data")
                .orderBy(FieldPath.documentId(), Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PojoMyBook> options = new FirestoreRecyclerOptions.Builder<PojoMyBook>()
                .setQuery(query, PojoMyBook.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<PojoMyBook, MyBookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyBookViewHolder holder, int position, @NonNull final PojoMyBook model) {

                holder.setNameCar(model.getType_car());
                holder.setSeats(model.getType_seats());
                holder.setEnginee(model.getType_enginee());
                holder.setItemPrice(model.getTotal_price());

                Glide.with(MyBookActivity.this)
                        .load(model.getUrl_data())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.load_place_holder)
                        .error(R.drawable.place_holder_error)
                        .centerCrop()
                        .into(holder.ivMyBook);
            }

            @NonNull
            @Override
            public MyBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_book, parent, false);
                return new MyBookViewHolder(view);
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    private class MyBookViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView ivMyBook;

        MyBookViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivMyBook = view.findViewById(R.id.iv_my_book);
        }

        void setNameCar(String name_car) {
            TextView tvNameCar = view.findViewById(R.id.tv_name_car);
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

        void setItemPrice(String itemPrice) {
            TextView tvItemPrice = view.findViewById(R.id.total_price);
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
