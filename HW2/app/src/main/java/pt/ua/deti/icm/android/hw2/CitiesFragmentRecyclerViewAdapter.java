package pt.ua.deti.icm.android.hw2;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.ua.deti.icm.android.hw2.cities.City;
import pt.ua.deti.icm.android.hw2.databinding.FragmentCitiesBinding;

import java.util.List;

public class CitiesFragmentRecyclerViewAdapter extends RecyclerView.Adapter<CitiesFragmentRecyclerViewAdapter.ViewHolder> {

    private final List<City> mValues;

    public CitiesFragmentRecyclerViewAdapter(List<City> items) {
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
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        holder.mContentView.setText(mValues.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public City mItem;

        public ViewHolder(FragmentCitiesBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}