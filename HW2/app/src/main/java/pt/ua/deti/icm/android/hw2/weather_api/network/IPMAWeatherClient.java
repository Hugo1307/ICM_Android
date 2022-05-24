package pt.ua.deti.icm.android.hw2.weather_api.network;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ua.deti.icm.android.hw2.weather_api.model.CityGroupModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.CityModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherGroupModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherTypeGroupModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherTypeModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.WindGroupModel;
import pt.ua.deti.icm.android.hw2.weather_api.model.WindModel;
import pt.ua.deti.icm.android.hw2.weather_api.network.listeners.CityForecastListener;
import pt.ua.deti.icm.android.hw2.weather_api.network.listeners.CityResultsListener;
import pt.ua.deti.icm.android.hw2.weather_api.network.listeners.WeatherTypesListener;
import pt.ua.deti.icm.android.hw2.weather_api.network.listeners.WindTypesListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class IPMAWeatherClient {

    private IPMAApiEndpoints apiService;

    public IPMAWeatherClient() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        this.apiService = retrofitInstance.create(IPMAApiEndpoints.class);
    }

    /**
     * get a list of cities (districts)
     * @param listener a listener object to callback with the results
     */
    public void retrieveCitiesList(CityResultsListener listener) {
        HashMap<String, CityModel> cities = new HashMap<>();

        Call<CityGroupModel> call = apiService.getCityParent();
        call.enqueue(new Callback<CityGroupModel>() {
            @Override
            public void onResponse(Call<CityGroupModel> call, Response<CityGroupModel> response) {
                int statusCode = response.code();
                CityGroupModel citiesGroup = response.body();
                for (CityModel city : citiesGroup.getCities()) {
                    cities.put(city.getLocal(), city);
                }
                listener.receiveCitiesList(cities);
            }

            @Override
            public void onFailure(Call<CityGroupModel> call, Throwable t) {
                Log.e("main", "errog calling remote api: " + t.getLocalizedMessage());
                listener.onFailure( t);
            }
        });
    }

    /**
     * get the dictionary for the weather states
     * @param listener a listener object to callback with the results
     */
    public void retrieveWeatherConditionsDescriptions(WeatherTypesListener listener) {
        HashMap<Integer, WeatherTypeModel> weatherDescriptions = new HashMap<>();

        Call<WeatherTypeGroupModel> call = apiService.getWeatherTypes();
        call.enqueue(new Callback<WeatherTypeGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherTypeGroupModel> call, Response<WeatherTypeGroupModel> response) {

                int statusCode = response.code();
                WeatherTypeGroupModel weatherTypesGroup = response.body();
                for ( WeatherTypeModel weather: weatherTypesGroup.getTypes() ) {
                    Log.i("HW2", String.valueOf(weather));
                    weatherDescriptions.put(weather.getIdWeatherType(), weather);
                }
                listener.receiveWeatherTypesList(weatherDescriptions);

            }

            @Override
            public void onFailure(Call<WeatherTypeGroupModel> call, Throwable t) {
                Log.e( "main", "errog calling remote api: " + t.getLocalizedMessage());
                listener.onFailure( t);
            }
        });
    }

    /**
     * get the dictionary for the wind states
     * @param listener a listener object to callback with the results
     */
    public void retrieveWindConditionsDescriptions(WindTypesListener listener) {

        HashMap<String, WindModel> windDescriptions = new HashMap<>();

        Call<WindGroupModel> call = apiService.getWindSpeedTypes();
        call.enqueue(new Callback<WindGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<WindGroupModel> call, Response<WindGroupModel> response) {

                WindGroupModel windGroupModel = response.body();
                for (WindModel windModel : windGroupModel.getTypes()) {
                    Log.i("HW2", String.valueOf(windModel));
                    windDescriptions.put(windModel.getWindSpeedClass(), windModel);
                }
                listener.receiveWeatherTypesList(windDescriptions);
            }

            @Override
            public void onFailure(@NonNull Call<WindGroupModel> call, @NonNull Throwable t) {
                Log.e( "main", "errog calling remote api: " + t.getLocalizedMessage());
                listener.onFailure( t);
            }

        });

    }

    /**
     * get the 5-day forecast for a city
     * @param  localId the global identifier of the location
     * @param listener a listener object to callback with the results
     */
    public void retrieveForecastForCity(int localId, CityForecastListener listener) {
        List<WeatherModel> forecast = new ArrayList<>();

        Call<WeatherGroupModel> call = apiService.getWeatherParent(localId);
        call.enqueue(new Callback<WeatherGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherGroupModel> call, @NonNull Response<WeatherGroupModel> response) {
                int statusCode = response.code();
                WeatherGroupModel weatherTypesGroup = response.body();
                forecast.addAll(weatherTypesGroup.getForecasts());
                listener.receiveForecastList(forecast);
            }

            @Override
            public void onFailure(@NonNull Call<WeatherGroupModel> call, @NonNull Throwable t) {
                Log.e( "main", "errog calling remote api: " + t.getLocalizedMessage());
                listener.onFailure( t);
            }

        });

    }



}
