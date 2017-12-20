package foodlist.muckitlist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import foodlist.muckitlist.Activity.NavActivity;

public class SearchActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private ListView lv;

    ArrayList<FoodItem> itemList;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        itemList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listView);

        new GetItems(searchTitle()).execute();

        adapter = new CustomAdapter(this, itemList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("googlemap", "onItemClick");
                FoodItem item = (FoodItem) adapter.getItem(i);
                GeoPoint oKA = new GeoPoint(item.getMapx(), item.getMapy());
                GeoPoint oGeo = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, oKA);
                Double lat = oGeo.getY() ;
                Double lng = oGeo.getX() ;
                GeoPoint oLatLng = new GeoPoint(lat.doubleValue(), lng.doubleValue());
           //   Toast.makeText(SearchActivity.this,lat+", "+lng, Toast.LENGTH_SHORT).show();
            //    Toast.makeText(SearchActivity.this, oLatLng.x+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this, NavActivity.class);
                intent.putExtra("lat", oLatLng.getX());
                intent.putExtra("lng", oLatLng.getY());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);



                finish();
            }
        });
    }

    public String searchTitle(){
        Log.d("json", "searchTitle");
        String str = getIntent().getStringExtra("search");
        Toast.makeText(SearchActivity.this, str, Toast.LENGTH_SHORT).show();
        return str;
    }

    private class GetItems extends AsyncTask<Void, Void, Void> {
        private String str;

        public GetItems(String str){
            this.str = str;
        }

        @Override
        protected void onPreExecute() {
            Log.d("json", "onpre");
            super.onPreExecute();

            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("json", "doInback");
            try {
                JSON json = new JSON(str);
              //  String search = searchTitle();
              //  json.getText(str);

                String jsonStr = json.readUrl();

                JSONObject jsonObject = new JSONObject(jsonStr);

                JSONArray items = jsonObject.getJSONArray("items");

                for(int i=0; i<items.length(); i++) {
                    JSONObject c = items.getJSONObject(i);

                    String title = c.getString("title");
                    title = title.replace("<b>", " ");
                    title = title.replace("</b>", " ");
                    String address = c.getString("address");
                    Double mapx = c.getDouble("mapx");
                    Double mapy = c.getDouble("mapy");

                    FoodItem foodItem = new FoodItem(title, address, mapx, mapy);
                    itemList.add(foodItem);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("json", "onpost");
            super.onPostExecute(aVoid);

            if(pDialog.isShowing()) {
                pDialog.dismiss();
            }

        }
    }

}
