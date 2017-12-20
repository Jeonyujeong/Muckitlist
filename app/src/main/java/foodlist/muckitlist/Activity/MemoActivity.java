package foodlist.muckitlist.Activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import foodlist.muckitlist.Memo;
import foodlist.muckitlist.R;

/**
 * Created by daeyo on 2017-12-01.
 * memo 등록을 위한 activity
 */

public class MemoActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private EditText etMemo, etTitle, etAddress;

    private FirebaseDatabase mFirebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi_memo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance(); //이미 Auth쪽에서 생성되었기 때문에 인증정보 유지 됨
        mFirebaseUser = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        etMemo = (EditText) findViewById(R.id.Edtxt_memo);
        etTitle = (EditText) findViewById(R.id.Edtxt_title);
        etAddress = (EditText) findViewById(R.id.Edtxt_address);

        /*if (mFirebaseUser == null) {  //인증정보가 제대로 전달 안되면 창을 닫고 다시 AuthActivity를 보여줌
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
            finish();
            return;
        }*/


        //FloatingActionButton fabNewMemo = (FloatingActionButton) findViewById(R.id.new_memo);
        //FloatingActionButton fabSaveMemo = (FloatingActionButton) findViewById(R.id.save_memo);
        ImageButton buttonSaveMemo = (ImageButton) findViewById(R.id.save_memo);
        buttonSaveMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMemo();
            }

        });

    }

    private void initMemo() {
        etTitle.setText("");
        etAddress.setText("");
        etMemo.setText("");
    }

    private void saveMemo() {
        String title = etTitle.getText().toString();
        String address = etAddress.getText().toString();
        String text = etMemo.getText().toString();
        if (text.isEmpty()||title.isEmpty()||address.isEmpty()) {
            return;
        }
        Memo memo = new Memo();
        memo.setTitle(etTitle.getText().toString());
        memo.setAddress(etAddress.getText().toString());
        memo.setTxt(etMemo.getText().toString());
        memo.setCreateDate(new Date().getTime());

        mFirebaseDatabase
                .getReference("memos/" + mFirebaseUser.getUid())
                .push()
                .setValue(memo)
                .addOnSuccessListener(MemoActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(etMemo, "메모가 저장되었습니다.", Snackbar.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}
