package pt.ua.deti.icm.android.hw2.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pt.ua.deti.icm.android.hw2.viewmodels.entities.WeatherForecastEntity;
import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherModel;

public class WeatherForecastViewModel extends ViewModel {

    private final MutableLiveData<WeatherForecastEntity> weatherPrediction = new MutableLiveData<>();

    public MutableLiveData<WeatherForecastEntity> getWeatherPrediction() {
        return weatherPrediction;
    }

}