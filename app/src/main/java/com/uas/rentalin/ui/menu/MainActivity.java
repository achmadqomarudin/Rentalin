package com.uas.rentalin.ui.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.uas.rentalin.adapter.ShopAdapter;
import com.uas.rentalin.model.Item;
import com.uas.rentalin.ui.BookingActivity;
import com.uas.rentalin.ui.Shop;
import com.uas.rentalin.ui.login.LoginActivity;
import com.uas.rentalin.ui.my_book.MyBookActivity;
import com.uas.rentalin.util.PrefManager;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener,
        View.OnClickListener {

    private List<Item> data;
    private Shop shop;

    private TextView currentItemName;
    private TextView currentItemPrice;
    private ImageView rateItemButton;
    private DiscreteScrollView itemPicker;
    private InfiniteScrollAdapter infiniteAdapter;

    //njajal
    private RecyclerView mRecyclerView;
    private FirestoreRecyclerAdapter<PojoDataCar, DataCarViewHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentItemName = findViewById(R.id.tv_type_car);
        currentItemPrice = findViewById(R.id.price_day);
//        rateItemButton = findViewById(R.id.item_btn_rate);

        shop = Shop.get();
        data = shop.getData();



        itemPicker = findViewById(R.id.item_picker);
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
        itemPicker.addOnItemChangedListener(this);
        infiniteAdapter = InfiniteScrollAdapter.wrap(new ShopAdapter(data));
        itemPicker.setAdapter(infiniteAdapter);
        itemPicker.setItemTransitionTimeMillis(500);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        onItemChanged(data.get(0));

        findViewById(R.id.btn_booking).setOnClickListener(this);
//        findViewById(R.id.item_btn_buy).setOnClickListener(this);
//        findViewById(R.id.item_btn_comment).setOnClickListener(this);

//        findViewById(R.id.home).setOnClickListener(this);
//        findViewById(R.id.btn_smooth_scroll).setOnClickListener(this);
//        findViewById(R.id.btn_transition_time).setOnClickListener(this);


        //njajal
        mRecyclerView = findViewById(R.id.rc_my_book);
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

                /*role*/
                String role = new PrefManager(getApplicationContext()).getRole();

                holder.setNameCar(model.getType_car());
                holder.setSeats(model.getType_seats());
                holder.setEnginee(model.getType_enginee());
                holder.setPriceDay(model.getPrice_day());

                Glide.with(MainActivity.this)
                        .load(model.getUrl_data())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(holder.ivDataCar);

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
            public DataCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_car, parent, false);
                return new DataCarViewHolder(view);
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.item_btn_rate:
//                int realPosition = infiniteAdapter.getRealPosition(itemPicker.getCurrentItem());
//                Item current = data.get(realPosition);
//                shop.setRated(current.getId(), !shop.isRated(current.getId()));
//                changeRateButtonState(current);
//                break;
//            case R.id.home:
//                finish();
//                break;
//            case R.id.btn_transition_time:
//                DiscreteScrollViewOptions.configureTransitionTime(itemPicker);
//                break;
            case R.id.btn_booking:
                Intent i = new Intent(getApplicationContext(), BookingActivity.class);
                startActivity(i);
                break;
            default:
                showUnsupportedSnackBar();
                break;
        }
    }

    private void onItemChanged(Item item) {
        currentItemName.setText(item.getName());
        currentItemPrice.setText(item.getPrice());
//        changeRateButtonState(item);
    }

//    private void changeRateButtonState(Item item) {
//        if (shop.isRated(item.getId())) {
//            rateItemButton.setImageResource(R.drawable.ic_star_black_24dp);
//            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopRatedStar));
//        } else {
//            rateItemButton.setImageResource(R.drawable.ic_star_border_black_24dp);
//            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopSecondary));
//        }
//    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {
        int positionInDataSet = infiniteAdapter.getRealPosition(position);
        onItemChanged(data.get(positionInDataSet));
    }

    private void showUnsupportedSnackBar() {
        Snackbar.make(itemPicker, "Button Clicked", Snackbar.LENGTH_SHORT).show();
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

        DataCarViewHolder(View itemView) {
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
