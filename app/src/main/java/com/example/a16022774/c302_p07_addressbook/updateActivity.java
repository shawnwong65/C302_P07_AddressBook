package com.example.a16022774.c302_p07_addressbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class updateActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);

        Intent i = getIntent();
        etFirstName.setText(i.getStringExtra("firstName"));
        etLastName.setText(i.getStringExtra("lastName"));
        etMobile.setText(i.getStringExtra("mobile"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for step 1 start
                String url = "http://10.0.2.2/C302_CloudAddressBook/updateContact.php";

                HttpRequest request = new HttpRequest(url);

                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("POST");
                request.addData("firstName", etFirstName.getText().toString());
                request.addData("lastName", etLastName.getText().toString());
                request.addData("mobile", etMobile.getText().toString());
                Intent i = getIntent();
                request.addData("id", i.getStringExtra("id"));


                request.execute();
                // Code for step 1 end

                setResult(RESULT_OK);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for step 1 start
                String url = "http://10.0.2.2/C302_CloudAddressBook/deleteContact.php";

                HttpRequest request = new HttpRequest(url);

                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("POST");
                Intent i = getIntent();
                request.addData("id", i.getStringExtra("id"));

                request.execute();
                // Code for step 1 end

                setResult(RESULT_OK);
                finish();
            }
        });
    }

    // Code for step 2 start
    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){

                    // process response here
                    try {
                        Log.i("JSON Results: ", response);

                        JSONObject jsonObj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                }
            };
// Code for step 2 end
}
