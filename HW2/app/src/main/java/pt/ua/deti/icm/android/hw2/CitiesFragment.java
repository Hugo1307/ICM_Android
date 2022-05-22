package pt.ua.deti.icm.android.hw2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pt.ua.deti.icm.android.hw2.cities.CitiesListVM;
import pt.ua.deti.icm.android.hw2.cities.City;

/**
 * A fragment representing a list of Items.
 */
public class CitiesFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CitiesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CitiesFragment newInstance(int columnCount) {
        CitiesFragment fragment = new CitiesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);
        CitiesListVM citiesListViewModel = new ViewModelProvider(requireActivity()).get(CitiesListVM.class);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            final Observer<List<City>> citiesListObserver = new Observer<List<City>>() {
                @Override
                public void onChanged(List<City> cities) {
                    recyclerView.setAdapter(new CitiesFragmentRecyclerViewAdapter(cities));
                }
            };

            citiesListViewModel.getCitiesList().observe(requireActivity(), citiesListObserver);

        }

        return view;

    }

}