package pt.ua.deti.icm.android.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import pt.ua.deti.icm.android.hw2.cities.CitiesListVM;
import pt.ua.deti.icm.android.hw2.cities.City;
import pt.ua.deti.icm.android.hw2.weather_api.model.CityModel;
import pt.ua.deti.icm.android.hw2.weather_api.network.IPMAWeatherClient;
import pt.ua.deti.icm.android.hw2.weather_api.network.listeners.CityResultsListener;

public class MainActivity extends AppCompatActivity {

    private final IPMAWeatherClient client = new IPMAWeatherClient();
    private CitiesListVM citiesListViewModel;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        citiesListViewModel = new ViewModelProvider(this).get(CitiesListVM.class);

        if (citiesListViewModel.getCitiesList().getValue() == null || citiesListViewModel.getCitiesList().getValue().size() == 0) {
            getAllCities();
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, CitiesFragment.class, null)
                    .commit();
        }

    }

    private void getAllCities() {

        client.retrieveCitiesList(new CityResultsListener() {

            @Override
            public void receiveCitiesList(HashMap<String, CityModel> citiesCollection) {
                int count = 0;
                for (Map.Entry<String, CityModel> city : citiesCollection.entrySet())
                    citiesListViewModel.addCity(new City(++count, city.getKey()));
            }

            @Override
            public void onFailure(Throwable cause) {
                Log.w("HW2", "Failed to get cities list!");
            }

        });


    }



}