package pt.ua.deti.icm.android.hw2.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pt.ua.deti.icm.android.hw2.CitiesFragmentRecyclerViewAdapter;
import pt.ua.deti.icm.android.hw2.MainActivity;
import pt.ua.deti.icm.android.hw2.R;
import pt.ua.deti.icm.android.hw2.utils.RecyclerItemClickListener;
import pt.ua.deti.icm.android.hw2.utils.WindowSizeClass;
import pt.ua.deti.icm.android.hw2.viewmodels.CitiesListViewModel;
import pt.ua.deti.icm.android.hw2.viewmodels.WeatherForecastViewModel;
import pt.ua.deti.icm.android.hw2.weather_api.WeatherAPIService;
import pt.ua.deti.icm.android.hw2.weather_api.model.CityModel;

public class CitiesFragment extends Fragment {

    public CitiesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);

        if (view instanceof RecyclerView) {

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // Configure onClick behavior for Recycler View

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {

                @Override
                public void onItemClick(View view, int position) {
                    listItemClickHandler(position);
                }

                @Override
                public void onLongItemClick(View view, int position) {
                    listItemClickHandler(position);
                }

            }));

            // Add Cities as elements to Recycler View

            CitiesListViewModel citiesListViewModel = new ViewModelProvider(requireActivity()).get(CitiesListViewModel.class);
            Observer<List<CityModel>> citiesListObserver = cities -> recyclerView.setAdapter(new CitiesFragmentRecyclerViewAdapter(cities));

            citiesListViewModel.getCitiesList().observe(requireActivity(), citiesListObserver);

        }

        return view;

    }

    private void listItemClickHandler(int position) {

        WeatherForecastViewModel weatherForecastViewModel = new ViewModelProvider(requireActivity()).get(WeatherForecastViewModel.class);
        CitiesListViewModel citiesListViewModel = new ViewModelProvider(requireActivity()).get(CitiesListViewModel.class);
        WeatherAPIService weatherAPIService = WeatherAPIService.getInstance();

        if (getActivity() == null) return;

        if (citiesListViewModel.getCitiesList().getValue() == null ||
                citiesListViewModel.getCitiesList().getValue().size() < position)
            return;


        CityModel currentCity = citiesListViewModel.getCitiesList().getValue().get(position);

        weatherAPIService.updateForecastForCity(currentCity, weatherForecastViewModel.getWeatherPrediction());

        MainActivity mainActivity = getActivity() instanceof MainActivity ? (MainActivity) getActivity() : null;

        if (mainActivity != null && mainActivity.computeWindowWidth() == WindowSizeClass.COMPACT) {

            Fragment weatherForecastFragment = new WeatherForecastFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_view, weatherForecastFragment);
            transaction.addToBackStack(null);

            transaction.commit();

        }

    }

}