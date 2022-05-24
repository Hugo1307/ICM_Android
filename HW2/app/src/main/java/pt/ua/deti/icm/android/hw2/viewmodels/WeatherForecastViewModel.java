package pt.ua.deti.icm.android.hw2.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pt.ua.deti.icm.android.hw2.viewmodels.entities.WeatherForecastEntity;
import pt.ua.deti.icm.android.hw2.weather_api.model.CityModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherModel;

public class WeatherForecastViewModel extends ViewModel {

    private final MutableLiveData<CityModel> currentCity = new MutableLiveData<>();
    private final MutableLiveData<WeatherForecastEntity> currentWeatherPrediction = new MutableLiveData<>();
    private final MutableLiveData<List<WeatherModel>> allForecastPredictions = new MutableLiveData<>();

    public MutableLiveData<WeatherForecastEntity> getCurrentWeatherPrediction() {
        return currentWeatherPrediction;
    }

    public MutableLiveData<CityModel> getCurrentCity() {
        return currentCity;
    }

    public MutableLiveData<List<WeatherModel>> getAllForecastPredictions() {
        return allForecastPredictions;
    }

}