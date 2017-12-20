package foodlist.muckitlist;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by yujeong on 28/11/17.
 */

public class MenuFragment extends Fragment {
    View v;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.nav_menu, container, false);

        mAuth = FirebaseAuth.getInstance(); //이미 Auth쪽에서 생성되었기 때문에 인증정보 유지 됨
        mFirebaseUser = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        FloatingActionButton fabNewMemo = (FloatingActionButton) v.findViewById(R.id.new_memo);

        fabNewMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memoIntent = new Intent(getActivity(), MemoActivity.class);
                startActivity(memoIntent);
            }
        });


        ListView memoListView = (ListView) v.findViewById(R.id.memoListView);  //리스트뷰 받아 오기

        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, arrayList);

        memoListView.setAdapter(adapter);

        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(
                        getActivity(),
                        i+"is selected",
                        Toast.LENGTH_SHORT
                ).show();


            }
        });

        mFirebaseDatabase.getReference("memos/" + mFirebaseUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Memo memo = dataSnapshot.getValue(Memo.class);
                arrayList.add(memo.getTitle());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Memo memo = dataSnapshot.getValue(Memo.class);
                memo.setKey(dataSnapshot.getKey());

                /**for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
                 MenuItem menuItem = mNavigationView.getMenu().getItem(i);
                 if (memo.getKey().equals(((Memo) menuItem.getActionView().getTag()).getKey())) {
                 menuItem.getActionView().setTag(memo);
                 menuItem.setTitle(memo.getTitle());
                 //memo.setTxt(memo.getTxt());
                 break;**/
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
        return v;
    }
}