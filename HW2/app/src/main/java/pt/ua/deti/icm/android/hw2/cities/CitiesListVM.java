package pt.ua.deti.icm.android.hw2.cities;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CitiesListVM extends ViewModel {

    private final List<City> citiesList = new ArrayList<>();
    private final MutableLiveData<List<City>> liveCitiesList = new MutableLiveData<>();

    public CitiesListVM() {
        liveCitiesList.setValue(citiesList);
    }

    public void addCity(City city) {
        citiesList.add(city);
        liveCitiesList.setValue(citiesList);
    }

    public MutableLiveData<List<City>> getCitiesList() {
        return liveCitiesList;
    }

}
