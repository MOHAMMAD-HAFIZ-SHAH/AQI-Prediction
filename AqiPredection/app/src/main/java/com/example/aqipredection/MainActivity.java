package com.example.aqipredection;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner citySpinner;
    private DatePicker datePicker;
    private Button predictButton, realTimeButton;
    private TextView predictedResultText, realTimeResultText;
    private RequestQueue requestQueue;

    private final List<String> cities = Arrays.asList(
            "Ahmedabad", "Aizawl", "Amaravati", "Amritsar", "Bengaluru",
            "Bhopal", "Brajrajnagar", "Chandigarh", "Chennai", "Coimbatore",
            "Delhi", "Ernakulam", "Gurugram", "Guwahati", "Hyderabad",
            "Jaipur", "Jorapokhar", "Kochi", "Kolkata", "Lucknow",
            "Mumbai", "Patna", "Shillong", "Talcher", "Thiruvananthapuram", "Visakhapatnam"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citySpinner = findViewById(R.id.city_spinner);
        datePicker = findViewById(R.id.date_picker);
        predictButton = findViewById(R.id.predict_button);
        realTimeButton = findViewById(R.id.real_time_button);
        predictedResultText = findViewById(R.id.predicted_result_text);
        realTimeResultText = findViewById(R.id.real_time_result_text);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        predictButton.setOnClickListener(v -> predictAQI());
        realTimeButton.setOnClickListener(v -> fetchRealTimeAQI());
    }

    // Predict AQI based on selected date
    private void predictAQI() {
        String city = citySpinner.getSelectedItem().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String date = year + "-" + month + "-" + day;

        String url = "http://10.7.0.160:5000/predict"; // Replace with your Flask backend IP

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("city", city);
            jsonBody.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    try {
                        double predictedAqi = response.getDouble("predicted_aqi");
                        predictedResultText.setText("Predicted AQI: " + predictedAqi);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        predictedResultText.setText("Error parsing prediction.");
                    }
                },
                error -> {
                    error.printStackTrace();
                    predictedResultText.setText("Request error: " + (error.getMessage() != null ? error.getMessage() : "Unknown error"));
                }
        );

        requestQueue.add(request);
    }

    // Fetch real-time AQI using AirVisual API
    private void fetchRealTimeAQI() {
        String city = citySpinner.getSelectedItem().toString();
        String state = getStateForCity(city);
        String apiKey = "7ddbd362-f448-468a-99de-70ca8f89ad0c";

        String url = "https://api.airvisual.com/v2/city?city=" + city +
                "&state=" + state +
                "&country=India&key=" + apiKey;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        JSONObject current = data.getJSONObject("current");
                        JSONObject pollution = current.getJSONObject("pollution");
                        double aqi = pollution.getDouble("aqius");

                        realTimeResultText.setText("Real-Time AQI: " + aqi);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        realTimeResultText.setText("Error parsing response.");
                    }
                },
                error -> {
                    error.printStackTrace();
                    realTimeResultText.setText("Request error: " + (error.getMessage() != null ? error.getMessage() : "Unknown error"));
                }
        );

        requestQueue.add(request);
    }

    // Map each city to its state
    private String getStateForCity(String city) {
        switch (city) {
            case "Ahmedabad": return "Gujarat";
            case "Aizawl": return "Mizoram";
            case "Amaravati": return "Andhra Pradesh";
            case "Amritsar": return "Punjab";
            case "Bengaluru": return "Karnataka";
            case "Bhopal": return "Madhya Pradesh";
            case "Brajrajnagar": return "Odisha";
            case "Chandigarh": return "Chandigarh";
            case "Chennai": return "Tamil Nadu";
            case "Coimbatore": return "Tamil Nadu";
            case "Delhi": return "Delhi";
            case "Ernakulam": return "Kerala";
            case "Gurugram": return "Haryana";
            case "Guwahati": return "Assam";
            case "Hyderabad": return "Telangana";
            case "Jaipur": return "Rajasthan";
            case "Jorapokhar": return "Jharkhand";
            case "Kochi": return "Kerala";
            case "Kolkata": return "West Bengal";
            case "Lucknow": return "Uttar Pradesh";
            case "Mumbai": return "Maharashtra";
            case "Patna": return "Bihar";
            case "Shillong": return "Meghalaya";
            case "Talcher": return "Odisha";
            case "Thiruvananthapuram": return "Kerala";
            case "Visakhapatnam": return "Andhra Pradesh";
            default: return "Delhi";
        }
    }
}
