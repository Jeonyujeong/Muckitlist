package foodlist.muckitlist.Activity;

import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import foodlist.muckitlist.Memo;
import foodlist.muckitlist.R;

/**
 * Created by daeyo on 2017-12-21.
 */

public class MemoViewActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private TextView title, address, memo, menu;
    private Rating star;
    private ImageButton btnMuk;
    private boolean pin;
    private ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memo);

        mFirebaseAuth = FirebaseAuth.getInstance(); //이미 Auth쪽에서 생성되었기 때문에 인증정보 유지 됨
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        title = (TextView) findViewById(R.id.tv_title);
        address = (TextView) findViewById(R.id.tv_address);
        memo = (TextView) findViewById(R.id.tv_memo);

        displayMemo();
    }

    private void displayMemo() {
        mFirebaseDatabase.getReference("memos/" + mFirebaseUser.getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Memo memo = dataSnapshot.getValue(Memo.class);
                        memo.setKey(dataSnapshot.getKey());


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Memo memo = dataSnapshot.getValue(Memo.class);
                        memo.setKey(dataSnapshot.getKey());


                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
