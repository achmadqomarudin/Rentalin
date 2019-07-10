package com.uas.rentalin.ui.my_book;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.uas.rentalin.R;
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

                /*role*/
                String role = new PrefManager(getApplicationContext()).getRole();

                holder.setNameCar(model.getType_car());
                holder.setSeats(model.getType_seats());
                holder.setEnginee(model.getType_enginee());
                holder.setItemPrice(model.getPrice());

                /**Cek Role*/
//                if (role.contentEquals("Super User") || role.contentEquals("Manager")) {
//
//                    holder.container.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent i = new Intent(getApplicationContext(), MyClaimActivity.class);
//                            i.putExtra("date", model.getDate());
//                            i.putExtra("invoice_no", model.getInvoice_no());
//                            i.putExtra("description", model.getDescription());
//                            i.putExtra("type_of_claim", model.getType_of_claim());
//                            i.putExtra("amount", model.getAmount());
//                            i.putExtra("bank_name", model.getBank_name());
//                            i.putExtra("bank_account", model.getBank_account());
//                            i.putExtra("attachment", model.getAttachment());
//                            i.putExtra("url_data", model.getUrl_data());
//                            i.putExtra("update", model.getUpdate());
//                            i.putExtra("data_type", model.getData_type());
//                            i.putExtra("key_project", getIntent().getStringExtra("key_project"));
//                            i.putExtra("id", model.getId());
//                            startActivity(i);
//                        }
//                    });
//                } else {
//                    holder.Layout_hide();
//                    Toast.makeText(getApplicationContext(), "Sorry, you don't have access", Toast.LENGTH_SHORT).show();
//                }
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
        private LinearLayout container;
        private LinearLayout.LayoutParams params;

        MyBookViewHolder(View itemView) {
            super(itemView);
            view = itemView;
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
