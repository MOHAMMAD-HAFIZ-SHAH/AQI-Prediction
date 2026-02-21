import pandas as pd
import numpy as np
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
import joblib

df = pd.read_csv("AQI.csv")
df = df.dropna(subset=["City", "Date", "AQI"])
df["Date"] = pd.to_datetime(df["Date"], errors='coerce')
df = df.dropna(subset=["Date"])

df["Day"] = df["Date"].dt.day
df["Month"] = df["Date"].dt.month
df["Year"] = df["Date"].dt.year

city_encoder = LabelEncoder()
df["City_Code"] = city_encoder.fit_transform(df["City"])

features = df[["City_Code", "Day", "Month", "Year"]]
target = df["AQI"]

X_train, X_test, y_train, y_test = train_test_split(features, target, test_size=0.2, random_state=42)

model = RandomForestRegressor(n_estimators=100)
model.fit(X_train, y_train)

joblib.dump(model, "aqi_predictor.pkl")
joblib.dump(city_encoder, "city_encoder.pkl")
print("Model and encoder saved.")
