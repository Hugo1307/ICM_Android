package pt.ua.deti.icm.android.hw2.weather_api.network.listeners;

import java.util.HashMap;

import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherTypeModel;

public interface WeatherTypesListener {

    void receiveWeatherTypesList(HashMap<Integer, WeatherTypeModel> descriptorsCollection);
    void onFailure(Throwable cause);

}
