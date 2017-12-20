package foodlist.muckitlist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by yujeong on 16/12/17.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FoodItem> items;

    public CustomAdapter(Context context, ArrayList<FoodItem> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        FoodItemView view = (FoodItemView) convertView;

        if(convertView == null)
            view = new FoodItemView(context);

        FoodItem item = items.get(i);
        view.setName(item.getName());
        view.setAddress(item.getAddress());

        return view;
    }


}
