package com.example.jsonexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.jsonexample.utils.NetworkUtils.generateURL;
import static com.example.jsonexample.utils.NetworkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {
    private EditText search_field;
    private Button btn_search;
    private TextView result_text;
    private TextView error_message;
    private ProgressBar loading_indicator;

    private void showResultTextView(){
        result_text.setVisibility(View.VISIBLE);
        error_message.setVisibility(View.INVISIBLE);
    }
    private void showErrorTextView(){
        result_text.setVisibility(View.INVISIBLE);
        error_message.setVisibility(View.VISIBLE);
    }


    class VKQueryTask extends AsyncTask < URL, Void, String > {
        @Override
        protected void onPreExecute(){
            loading_indicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void  onPostExecute (String response){
            String firstName = null;
            String lastName = null;
            if(response!=null && !response.equals("")) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("response");
                    JSONObject userInfo = jsonArray.getJSONObject(0);

                    firstName = userInfo.getString("first_name");
                    lastName = userInfo.getString("last_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String resultingString = "Иня : " +firstName + "\n" + "Фамилия : " + lastName;
                result_text.setText(resultingString);
                showResultTextView();
            }else {
                showErrorTextView();
            }
            loading_indicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search_field = findViewById(R.id.et_serch_field);
        btn_search = findViewById(R.id.btn_search_VK);
        result_text = findViewById(R.id.textViewResult);
        error_message = findViewById(R.id.textView_error_message);
        loading_indicator = findViewById(R.id.progress_bar_loading_indicator) ;


    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            URL generatedURL = generateURL(search_field.getText().toString());

            new  VKQueryTask().execute(generatedURL);
        }
    };
    btn_search.setOnClickListener(mOnClickListener);
}
}







