package foodlist.muckitlist;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by yujeong on 28/11/17.
 */

public class MenuFragment extends Fragment {
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.nav_menu, container, false);

        Button buttonImsi = (Button) v.findViewById(R.id.imsibutton);
        buttonImsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "버튼이 눌렸습니다.", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MemoActivity.class);
                startActivity(intent);
            }
        });
        return v;

    }
}
