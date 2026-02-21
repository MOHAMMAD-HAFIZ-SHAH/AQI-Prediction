print("Starting Flask app...")

from flask import Flask, request, jsonify
import joblib
import datetime

app = Flask(__name__)

# Load the trained model and encoder
model = joblib.load("aqi_predictor.pkl")
city_encoder = joblib.load("city_encoder.pkl")

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json()
    city = data.get("city")
    date_str = data.get("date")

    try:
        date = datetime.datetime.strptime(date_str, "%Y-%m-%d")
        city_code = city_encoder.transform([city])[0]
        input_data = [[city_code, date.day, date.month, date.year]]
        prediction = model.predict(input_data)[0]
        return jsonify({"predicted_aqi": round(prediction, 2)})
    except Exception as e:
        return jsonify({"error": str(e)}), 400

# Only run the app directly if this file is executed as the main script
if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")
