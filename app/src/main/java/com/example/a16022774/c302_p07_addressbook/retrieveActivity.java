package com.example.a16022774.c302_p07_addressbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class retrieveActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Contact> al;
    CustomAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);


        al = new ArrayList<Contact>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        al.clear();
        lv = (ListView) findViewById(R.id.lv);
        aa = new CustomAdapter(retrieveActivity.this, R.layout.row, al);
        lv.setAdapter(aa);


        // Code for step 1 start
        HttpRequest request = new HttpRequest
                ("http://10.0.2.2/C302_CloudAddressBook/getContactDetails.php");
        request.setOnHttpResponseListener(mHttpResponseListener);
        request.setMethod("GET");
        request.execute();
        // Code for step 1 end


    }

    // Code for step 2 start
    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){

                    // process response here
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObj = jsonArray.getJSONObject(i);

                            String firstName = jsonObj.getString("firstname");
                            String lastName = jsonObj.getString("lastname");
                            String mobile = jsonObj.getString("mobile");
                            String id = jsonObj.getString("id");
                            
                            Contact contact = new Contact(id, firstName, lastName, mobile);
                            al.add(contact);


                        }

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int
                                    position, long identity) {

                                Intent i = new Intent(retrieveActivity.this, updateActivity.class);
                                Contact data = al.get(position);
                                i.putExtra("id", data.getId());
                                i.putExtra("firstName", data.getFirstName());
                                i.putExtra("lastName", data.getLastName());
                                i.putExtra("mobile", data.getMobile());
                                startActivityForResult(i, 1);

                            }
                        });
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                    aa.notifyDataSetChanged();

                }
            };
    // Code for step 2 end

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1){
            aa.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.create) {
            Intent i = new Intent(retrieveActivity.this, MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
