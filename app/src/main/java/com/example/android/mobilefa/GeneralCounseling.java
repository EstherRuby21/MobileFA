package com.example.android.mobilefa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GeneralCounseling extends AppCompatActivity {
    EditText improvement, issue, suggestion;
    Button counseling_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_counseling);
        improvement = findViewById(R.id.activity_general_counselling_improvement_edittext);
        issue = findViewById(R.id.activity_general_counselling_issue_edittext);
        suggestion = findViewById(R.id.activity_general_counselling_suggestion_edittext);
        counseling_submit= findViewById(R.id.activity_general_counselling_submit_button);

        final String improvements = improvement.getText().toString();
        final String issues = issue.getText().toString();
        final String suggestions = suggestion.getText().toString();

        counseling_submit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                boolean flag_counselling = false;

                if(TextUtils.isEmpty(improvements) || TextUtils.isEmpty(issues) || TextUtils.isEmpty(suggestions)) {
                    flag_counselling = true;
                    Toast.makeText(getApplicationContext(), "Please enter atleast one field!", Toast.LENGTH_LONG).show();
                }


                if(!flag_counselling) {
                    StringRequest request = new StringRequest(Request.Method.POST, Constants.GENERAL_COUNSELING_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("General Counselling Data Filled")) {
                                Toast.makeText(getApplicationContext(), "General counselling Done", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("General Counselling", error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("reg_no", String.valueOf(Constants.REG_NO));
                            params.put("improvements", improvements);
                            params.put("issues", issues);
                            params.put("suggestions", suggestions);
                            return params;
                        }
                    };
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),ChoosingActivity.class));
        finish();
    }
}
