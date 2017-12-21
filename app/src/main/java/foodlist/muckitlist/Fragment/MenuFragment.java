package foodlist.muckitlist.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import foodlist.muckitlist.Activity.MemoActivity;
import foodlist.muckitlist.Activity.MemoViewActivity;
import foodlist.muckitlist.Memo;
import foodlist.muckitlist.R;

/**
 * Created by yujeong on 28/11/17.
 */

public class MenuFragment extends Fragment {
    View v;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private ArrayList<ListViewItem> arrayList = new ArrayList<ListViewItem>();
    private ListViewAdapter adapter;
    private Spinner spmenu, spmap, sppin;
    String[] menuItems = {"메뉴전체","한식", "중식", "일식", "양식", "분식", "야식", "디저트", "술", "기타"};
    String[] mapItems = {"지역전체","강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"};
    String[] pinItems = {"핀전체","노란핀","빨간핀"};
    private int menuNum, mapNum, pinNum;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.nav_menu, container, false);

        mAuth = FirebaseAuth.getInstance(); //이미 Auth쪽에서 생성되었기 때문에 인증정보 유지 됨
        mFirebaseUser = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        spmenu = (Spinner) v.findViewById(R.id.spmenu_menu);
        spmap = (Spinner) v.findViewById(R.id.spmenu_map);
        sppin = (Spinner) v.findViewById(R.id.spmenu_pin);

        FloatingActionButton fabNewMemo = (FloatingActionButton) v.findViewById(R.id.new_memo);

        fabNewMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memoIntent = new Intent(getActivity(), MemoActivity.class);
                startActivity(memoIntent);

            }
        });


        ListView memoListview ;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        memoListview = (ListView) v.findViewById(R.id.memoListView);
        memoListview.setAdapter(adapter);
        printMenu();




        /*ListView memoListView = (ListView) v.findViewById(R.id.memoListView);  //리스트뷰 받아 오기

        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, arrayList);
*/

        ArrayAdapter menuAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, menuItems);
        ArrayAdapter<String> mapAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, mapItems);
        ArrayAdapter<String> pinAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, pinItems);
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spmenu.setAdapter(menuAdapter);
        spmap.setAdapter(mapAdapter);
        sppin.setAdapter(pinAdapter);
        spmenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                menuNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                menuNum=0;
            }
        });
        spmap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mapNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mapNum=0;
            }
        });
        sppin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                pinNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pinNum=0;
            }
        });



        memoListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(
                        getActivity(),
                        i + "is selected",
                        Toast.LENGTH_SHORT
                ).show();
                Intent intent = new Intent(getActivity(), MemoViewActivity.class);
                //intent.putExtra("MemoData",memo);
                startActivity(intent);
            }
        });

        return v;
    }
    private void printMenu(){
        mFirebaseDatabase.getReference("memos/" + mFirebaseUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Memo memo = dataSnapshot.getValue(Memo.class);
                if(memo.isPin()){
                    adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.yes_pin),
                            memo.getTitle(), memo.getKey());
                } else{
                    adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.no_pin),
                            memo.getTitle(), memo.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Memo memo = dataSnapshot.getValue(Memo.class);
                //memo.setKey(dataSnapshot.getKey());

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
class ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}

class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.list_image) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_title) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.list_key) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String title, String desc) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }
}
