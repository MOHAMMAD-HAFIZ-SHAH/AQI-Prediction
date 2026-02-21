# AQI Prediction — Documentation

## Overview
This repository contains a Python backend that serves an AQI prediction API and an Android/Gradle frontend project. The frontend was developed in Android Studio. The app consumes live AQI data from the IQAir API (you must obtain an IQAir API key to use live data). Core backend files:
- [aqi_backend/app.py](aqi_backend/app.py)
- [aqi_backend/train_model.py](aqi_backend/train_model.py)
- [aqi_backend/AQI.csv](aqi_backend/AQI.csv)

## Prerequisites
- Python 3.8+ (create a virtual environment recommended)
- pip
- For Android frontend: Android Studio or Gradle (see [AQI-Predection frontend/settings.gradle.kts](AQI-Predection frontend/settings.gradle.kts))
 - For Android frontend: Android Studio or Gradle (see [AQI-Predection frontend/settings.gradle.kts](AQI-Predection frontend/settings.gradle.kts))
 - IQAir API key (if using live AQI data in the frontend)

## Backend — setup & run
1. Create and activate a virtual environment:
```bash
python -m venv .venv
source .venv/bin/activate   # macOS/Linux
.venv\Scripts\activate    # Windows
```
2. Install dependencies (if no requirements file, install common libs):
```bash
pip install flask pandas scikit-learn joblib
# or
pip install -r aqi_backend/requirements.txt
```
3. Train or load the model:
- To train: run `aqi_backend/train_model.py`
```bash
# AQI Prediction API — Technical Documentation

A compact system for predicting and serving Air Quality Index (AQI) forecasts. This document describes how to set up, run, and troubleshoot the backend API and the Android frontend.

## Project Overview
This repository contains a Python backend that exposes an AQI prediction API and an Android frontend built with Android Studio. The frontend can consume live AQI data from the IQAir API — an IQAir API key is required to enable live data. Core backend files:
- `aqi_backend/app.py`
- `aqi_backend/train_model.py`
- `aqi_backend/AQI.csv`

## System Requirements
- Python 3.8+ (create a virtual environment recommended)
- pip
- For the Android frontend: Android Studio or Gradle (see `AQI-Predection frontend/settings.gradle.kts`)
- IQAir API key (if using live AQI data in the frontend)

## Backend — Setup and Execution
1. Create and activate a virtual environment:
```bash
python -m venv .venv
source .venv/bin/activate   # macOS/Linux
.venv\Scripts\activate    # Windows
```
2. Install dependencies (if no `requirements.txt` is present, install common libraries):
```bash
pip install flask pandas scikit-learn joblib
# or
pip install -r aqi_backend/requirements.txt
```
3. Train or load the model:

- To train:
```bash
python aqi_backend/train_model.py
```

- Check `aqi_backend/train_model.py` for the model output path.

4. Start the API:

- If `app.py` contains a runnable Flask application:
```bash
python aqi_backend/app.py
```

- Or via the Flask CLI (adjust module name if necessary):
```bash
set FLASK_APP=aqi_backend.app   # Windows PowerShell/CMD
export FLASK_APP=aqi_backend.app  # macOS/Linux
flask run --host=0.0.0.0 --port=5000
```

5. Verify the service: open http://localhost:5000/ or inspect the routes in `aqi_backend/app.py` to determine endpoints and payload formats.

## Backend — Common Commands
- Lint and format:
```bash
pip install black flake8
black .
flake8
```
- Run tests (if present):
```bash
pytest
```

## Frontend — Build & Run
- Open the `AQI-Predection frontend` project in Android Studio.
- Or use the Gradle wrapper:
```bash
cd "AQI-Predection frontend"
./gradlew assembleDebug   # macOS/Linux
gradlew.bat assembleDebug  # Windows
```

Adjust the API base URL in the frontend to point to your backend (use the server IP or `localhost` with proper emulator/device routing).

- The frontend can fetch AQI data from the IQAir API; configure the IQAir API key and base URL inside the app (look for `API_BASE_URL`, `IQAIR_API_KEY`, or network configuration files).

## Repository Structure
- `aqi_backend/` — Python API and model training scripts
  - `aqi_backend/app.py`
  - `aqi_backend/train_model.py`
  - `aqi_backend/AQI.csv`
- `AQI-Predection frontend/` — Android (Gradle) project

## Troubleshooting & Support
- Port conflict: change the port used when running Flask or stop the conflicting service.
- Missing dependencies: inspect imports in `aqi_backend/app.py` and install the required packages.
- API payloads or endpoints unclear: open `aqi_backend/app.py` to read route implementations and expected request/response schemas.

## References
- Source files: `aqi_backend/app.py`, `aqi_backend/train_model.py`
- IQAir API: https://www.iqair.com/ (obtain an API key to enable live data)
