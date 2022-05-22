package pt.ua.deti.icm.android.hw2.weather_api.network.listeners;

import java.util.List;

import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherModel;

public interface CityForecastListener {

    void receiveForecastList(List<WeatherModel> forecast);
    void onFailure(Throwable cause);

}
