package foodlist.muckitlist.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    private TextView title, address, vmemo, menu;
    private RatingBar star;
    private ImageButton btnMuk;
    private boolean pin;
    private ImageView imgView;
    private long crDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memo);

        mFirebaseAuth = FirebaseAuth.getInstance(); //이미 Auth쪽에서 생성되었기 때문에 인증정보 유지 됨
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        Intent gintent = getIntent();
        crDay = gintent.getExtras().getLong("memo");


        title = (TextView) findViewById(R.id.tv_title);
        address = (TextView) findViewById(R.id.tv_address);
        vmemo = (TextView) findViewById(R.id.tv_memo);
        star = (RatingBar) findViewById(R.id.v_ratingBar) ;
        menu = (TextView)findViewById(R.id.v_food_category) ;

        FloatingActionButton fabNewMemo = (FloatingActionButton) findViewById(R.id.edit_memo);

        fabNewMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memoIntent = new Intent(MemoViewActivity.this, MemoActivity.class);
                memoIntent.putExtra("memo",crDay);
                Log.d("ㅇㅇㅇ","수정하러가기 전 성공"+crDay);
                startActivity(memoIntent);

            }
        });

        displayMemo();

    }

    private void displayMemo() {
        mFirebaseDatabase.getReference("memos/" + mFirebaseUser.getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Memo memo = dataSnapshot.getValue(Memo.class);
                        memo.setKey(dataSnapshot.getKey());
                        Log.d("ㅇㅇㅇ", memo.getKey());

                        if (crDay == memo.getCreateDate()) {
                            showMemo(memo);
                        } else {
                            // Toast.makeText(MemoViewActivity.this, "잘못 된 접근입니다.", Toast.LENGTH_LONG).show();
                        }

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

    void showMemo(Memo memo) {
        title.setText(memo.getTitle());
        address.setText(memo.getAddress());
        vmemo.setText(memo.getTxt());

        pin=memo.isPin();
        btnMuk = (ImageButton) findViewById(R.id.v_muckBan);
        if(pin){
            btnMuk.setImageResource(R.drawable.yes_eat);
        } else{
            btnMuk.setImageResource(R.drawable.no_eat);
        }
        star.setRating(memo.getRating());
        switch(memo.getFood_category()){
            case 0:
                menu.setText("한식");
                break;
            case 1:
                menu.setText("중식");
                break;
            case 2:
                menu.setText("일식");
                break;
            case 3:
                menu.setText("양식");
                break;
            case 4:
                menu.setText("분식");
                break;
            case 5:
                menu.setText("야식");
                break;
            case 6:
                menu.setText("디저트");
                break;
            case 7:
                menu.setText("술");
                break;
            case 8:
                menu.setText("기타");
                break;
        }

    }

}
