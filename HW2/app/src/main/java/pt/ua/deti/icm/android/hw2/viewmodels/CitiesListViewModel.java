package pt.ua.deti.icm.android.hw2.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pt.ua.deti.icm.android.hw2.weather_api.model.CityModel;

public class CitiesListViewModel extends ViewModel {

    private final List<CityModel> citiesList = new ArrayList<>();
    private final MutableLiveData<List<CityModel>> liveCitiesList = new MutableLiveData<>();

    public CitiesListViewModel() {
        liveCitiesList.setValue(citiesList);
    }

    public void addCity(CityModel city) {
        citiesList.add(city);
        liveCitiesList.setValue(citiesList);
    }

    public MutableLiveData<List<CityModel>> getCitiesList() {
        return liveCitiesList;
    }

}
