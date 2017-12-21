package foodlist.muckitlist.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

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
    private RatingBar rb;
    private float floatRating;
    String[] items = {"한식", "중식", "일식", "양식", "분식", "야식", "디저트", "술", "기타"};
    private int foodnum;
    private static int PICK_IMAGE_REQUEST = 1;
    private ImageView imgView;
    private ImageButton btnMuk;
    private boolean pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi_memo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance(); //이미 Auth쪽에서 생성되었기 때문에 인증정보 유지 됨
        mFirebaseUser = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        etTitle = (EditText) findViewById(R.id.Edtxt_title);
        etAddress = (EditText) findViewById(R.id.Edtxt_address);
        etMemo = (EditText) findViewById(R.id.Edtxt_memo);
        rb = (RatingBar) findViewById(R.id.ratingBar1);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> foodAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        foodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// 어댑터 설정
        spinner.setAdapter(foodAdapter);
// 아이템 선택 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                foodnum = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                foodnum = 8;
            }
            // 아이템이 선택되었을 때 호출됨

        });
        // 별점주기
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                floatRating = rating;
            }
        });

        //먹킷바꾸기
        btnMuk = (ImageButton) findViewById(R.id.muckBan);
        if(pin){
            btnMuk.setImageResource(R.drawable.yes_eat);
        } else{
            btnMuk.setImageResource(R.drawable.no_eat);
        }
        btnMuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pin) {
                    btnMuk.setImageResource(R.drawable.no_eat);
                    pin = false;
                } else {
                    btnMuk.setImageResource(R.drawable.yes_eat);
                  pin = true;
                }
            }
        });



        //메모저장
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
        if (title.isEmpty()) {
            Toast.makeText(this, "상호명을 적어주세요", Toast.LENGTH_LONG).show();
            return;
        }
        Memo memo = new Memo();

        Log.d("titletest", title + "저장전");
        memo.setTitle(title);
        memo.setAddress(address);
        memo.setTxt(text);
        memo.setCreateDate(new Date().getTime());
        memo.setRating(floatRating);
        memo.setFood_category(foodnum);
        memo.setUsid(mFirebaseUser.getUid());
        memo.setPin(pin);

        mFirebaseDatabase
                .getReference("memos/"+mFirebaseUser.getUid())
                .push()
                .setValue(memo)
                .addOnSuccessListener(MemoActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                });
    }

    public void loadImagefromGallery(View view) {
        //Intent 생성
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
        intent.setType("image/*"); //이미지만 보이게
        //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //이미지 선택작업을 후의 결과 처리
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageBitmap(scaled);

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}
