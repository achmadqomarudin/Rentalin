package com.uas.rentalin.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.rentalin.R;
import com.uas.rentalin.adapter.ShopAdapter;
import com.uas.rentalin.model.Item;
import com.uas.rentalin.ui.BookingActivity;
import com.uas.rentalin.ui.Shop;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentItemName = findViewById(R.id.item_name);
        currentItemPrice = findViewById(R.id.item_price);
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
}
