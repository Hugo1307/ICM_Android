package pt.ua.deti.icm.android.hw2.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.ua.deti.icm.android.hw2.R;
import pt.ua.deti.icm.android.hw2.viewmodels.WeatherForecastViewModel;
import pt.ua.deti.icm.android.hw2.viewmodels.entities.WeatherForecastEntity;
import pt.ua.deti.icm.android.hw2.weather_api.model.WeatherModel;

public class WeatherForecastFragment extends Fragment {

    private WeatherForecastViewModel mViewModel;

    public static WeatherForecastFragment newInstance() {
        return new WeatherForecastFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(requireActivity()).get(WeatherForecastViewModel.class);

        if (getView() != null) {

            TextView cityNamePlaceholder = getView().findViewById(R.id.cityNamePlaceholder);
            TextView forecastDatePlaceholder = getView().findViewById(R.id.forecastDatePlaceholder);

            TextView maxTempPlaceholder = getView().findViewById(R.id.maxTempPlaceholder);
            TextView minTempPlaceholder = getView().findViewById(R.id.minTempPlaceholder);
            TextView weatherTypePlaceholder = getView().findViewById(R.id.weatherTypeIdPlaceholder);
            TextView windDirectionPlaceholder = getView().findViewById(R.id.windDirectionPlaceholder);
            TextView windSpeedPlaceholder = getView().findViewById(R.id.windSpeedPlaceholder);
            TextView precipitationProbPlaceholder = getView().findViewById(R.id.precipitationProbPlaceholder);

            Observer<WeatherForecastEntity> weatherIdObserver = weatherForecast -> {
                cityNamePlaceholder.setText(weatherForecast.getCityName());
                forecastDatePlaceholder.setText(weatherForecast.getForecastDate());

                maxTempPlaceholder.setText(String.valueOf(weatherForecast.getMaxTemp()));
                minTempPlaceholder.setText(String.valueOf(weatherForecast.getMinTemp()));
                weatherTypePlaceholder.setText(String.valueOf(weatherForecast.getWeatherType()));
                windDirectionPlaceholder.setText(weatherForecast.getWindDirection());
                windSpeedPlaceholder.setText(String.valueOf(weatherForecast.getWindSpeed()));
                precipitationProbPlaceholder.setText(String.format("%s %%", weatherForecast.getPrecipitationProb()));
            };

            mViewModel.getWeatherPrediction().observe(requireActivity(), weatherIdObserver);

        }

    }

}