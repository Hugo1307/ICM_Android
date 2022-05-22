package pt.ua.deti.icm.android.hw2.weather_api.network;

import pt.ua.deti.icm.android.hw2.weather_api.model.CityGroupModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherGroupModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherTypeGroupModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPMAApiEndpoints {

    @GET("open-data/distrits-islands.json")
    Call<CityGroupModel> getCityParent();

    @GET("open-data/forecast/meteorology/cities/daily/{localId}.json")
    Call<WeatherGroupModel> getWeatherParent(@Path("localId") int localId);

    @GET("open-data/weather-type-classe.json")
    Call<WeatherTypeGroupModel> getWeatherTypes();


}
