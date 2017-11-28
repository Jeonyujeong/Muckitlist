package foodlist.muckitlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yujeong on 28/11/17.
 */

public class FriendFragment extends android.app.Fragment{
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.nav_friend, container, false);
        return v;
    }
}
