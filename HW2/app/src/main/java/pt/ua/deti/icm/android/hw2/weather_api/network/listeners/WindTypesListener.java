package pt.ua.deti.icm.android.hw2.weather_api.network.listeners;

import java.util.HashMap;

import pt.ua.deti.icm.android.hw2.weather_api.model.WindModel;

public interface WindTypesListener {

    void receiveWeatherTypesList(HashMap<String, WindModel> descriptorsCollection);
    void onFailure(Throwable cause);

}
