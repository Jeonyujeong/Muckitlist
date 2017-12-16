package foodlist.muckitlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yujeong on 16/12/17.
 */

class FoodItemView extends LinearLayout {

    TextView nameText;
    TextView addressText;

    public FoodItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.food_item, this, true);

        nameText = (TextView) findViewById(R.id.name);
        addressText = (TextView) findViewById(R.id.address);
    }

    void setName (String name) { nameText.setText(name); }

    void setAddress (String address) { addressText.setText(address); }

}