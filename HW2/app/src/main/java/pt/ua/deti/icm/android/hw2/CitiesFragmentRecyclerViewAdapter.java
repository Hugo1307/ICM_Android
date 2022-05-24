package pt.ua.deti.icm.android.hw2;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.ua.deti.icm.android.hw2.databinding.FragmentCitiesBinding;
import pt.ua.deti.icm.android.hw2.weather_api.model.CityModel;

import java.util.List;

public class CitiesFragmentRecyclerViewAdapter extends RecyclerView.Adapter<CitiesFragmentRecyclerViewAdapter.ViewHolder> {

    private final List<CityModel> mValues;

    public CitiesFragmentRecyclerViewAdapter(List<CityModel> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentCitiesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getLocal());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public CityModel mItem;

        public ViewHolder(FragmentCitiesBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}