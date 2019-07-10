package com.uas.rentalin.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.uas.rentalin.R;
import com.uas.rentalin.model.Item;
import com.uas.rentalin.util.App;

import java.util.Arrays;
import java.util.List;

public class Shop {

    private static final String STORAGE = "shop";

    public static Shop get() {
        return new Shop();
    }

    private SharedPreferences storage;

    private Shop() {
//        storage = App.getInstance().getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public List<Item> getData() {



        return Arrays.asList(
                new Item(1, "Everyday Candle", "$12.00 USD", "https://article.images.consumerreports.org/prod/content/dam/CRO%20Images%202019/Cars/January/CR-Cars-InlineHero-2019-Acura-ILX-A-Spec-f-1-19"),
                new Item(2, "Small Porcelain Bowl", "$50.00 USD", "https://amp.businessinsider.com/images/592f4169b74af41b008b5977-750-563.jpg"),
                new Item(3, "Favourite Board", "$265.00 USD", "https://www.enterprise.com/content/dam/global-vehicle-images/cars/TOYO_CAMR_2018.png"));
    }

//    public boolean isRated(int itemId) {
//        return storage.getBoolean(String.valueOf(itemId), false);
//    }
//
//    public void setRated(int itemId, boolean isRated) {
//        storage.edit().putBoolean(String.valueOf(itemId), isRated).apply();
//    }
}
