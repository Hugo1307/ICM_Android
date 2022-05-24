package pt.ua.deti.icm.android.hw2.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import pt.ua.deti.icm.android.hw2.R;
import pt.ua.deti.icm.android.hw2.viewmodels.WeatherForecastViewModel;
import pt.ua.deti.icm.android.hw2.viewmodels.entities.WeatherForecastEntity;
import pt.ua.deti.icm.android.hw2.weather_api.WeatherAPIService;
import pt.ua.deti.icm.android.hw2.weather_api.model.CityModel;
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

                maxTempPlaceholder.setText(String.format("%s ºC", weatherForecast.getMaxTemp()));
                minTempPlaceholder.setText(String.format("%s ºC", weatherForecast.getMinTemp()));
                weatherTypePlaceholder.setText(String.valueOf(weatherForecast.getWeatherType()));
                windDirectionPlaceholder.setText(weatherForecast.getWindDirection());
                windSpeedPlaceholder.setText(String.valueOf(weatherForecast.getWindSpeed()));
                precipitationProbPlaceholder.setText(String.format("%s %%", weatherForecast.getPrecipitationProb()));
            };

            mViewModel.getCurrentWeatherPrediction().observe(requireActivity(), weatherIdObserver);

            // Forecast selector buttons

            Button[] forecastButtons = {getView().findViewById(R.id.btnForecast1), getView().findViewById(R.id.btnForecast2),
                    getView().findViewById(R.id.btnForecast3), getView().findViewById(R.id.btnForecast4), getView().findViewById(R.id.btnForecast5)};

            Observer<List<WeatherModel>> weatherForecastListObserver = weatherModelList -> {
                for (int i = 0; i < forecastButtons.length; i++) {

                    forecastButtons[i].setText(getWeekDayName(weatherModelList.get(i).getForecastDate()));

                    CityModel currentCity = mViewModel.getCurrentCity().getValue();

                    if (currentCity == null) continue;

                    int finalI = i;
                    forecastButtons[i].setOnClickListener(v -> WeatherAPIService.getInstance().updateForecastForCity(currentCity, finalI, mViewModel.getCurrentWeatherPrediction()));

                }
            };

            mViewModel.getAllForecastPredictions().observe(requireActivity(), weatherForecastListObserver);

        }

    }

    private String getWeekDayName(String s) {
        DateTimeFormatter dtfInput = DateTimeFormatter.ofPattern("u-M-d", Locale.ENGLISH);
        DateTimeFormatter dtfOutput = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);
        return LocalDate.parse(s, dtfInput).format(dtfOutput).substring(0, 3);
    }

}