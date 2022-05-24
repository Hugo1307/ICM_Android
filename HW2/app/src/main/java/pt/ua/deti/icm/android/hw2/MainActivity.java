package pt.ua.deti.icm.android.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import pt.ua.deti.icm.android.hw2.fragments.CitiesFragment;
import pt.ua.deti.icm.android.hw2.utils.WindowSizeClass;
import pt.ua.deti.icm.android.hw2.viewmodels.CitiesListViewModel;
import pt.ua.deti.icm.android.hw2.weather_api.WeatherAPIService;
import pt.ua.deti.icm.android.hw2.weather_api.model.CityModel;
import pt.ua.deti.icm.android.hw2.weather_api.network.IPMAWeatherClient;
import pt.ua.deti.icm.android.hw2.weather_api.network.listeners.CityResultsListener;

public class MainActivity extends AppCompatActivity {

    private final IPMAWeatherClient client = new IPMAWeatherClient();
    private CitiesListViewModel citiesListViewModel;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        citiesListViewModel = new ViewModelProvider(this).get(CitiesListViewModel.class);

        if (citiesListViewModel.getCitiesList().getValue() == null ||
                citiesListViewModel.getCitiesList().getValue().size() == 0) {
            WeatherAPIService.getInstance().updateAllCities(citiesListViewModel.getCitiesList());
        }

        if (savedInstanceState == null && computeWindowWidth() == WindowSizeClass.COMPACT) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, CitiesFragment.class, null)
                    .commit();
        }

    }

    public WindowSizeClass computeWindowWidth() {

        WindowMetrics metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this);

        float widthDp = metrics.getBounds().width() / getResources().getDisplayMetrics().density;
        WindowSizeClass widthWindowSizeClass;

        if (widthDp < 600f) {
            widthWindowSizeClass = WindowSizeClass.COMPACT;
        } else if (widthDp < 840f) {
            widthWindowSizeClass = WindowSizeClass.MEDIUM;
        } else {
            widthWindowSizeClass = WindowSizeClass.EXPANDED;
        }

        return widthWindowSizeClass;

    }

}