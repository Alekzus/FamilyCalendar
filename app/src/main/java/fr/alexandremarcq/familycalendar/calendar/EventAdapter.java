package fr.alexandremarcq.familycalendar.calendar;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.alexandremarcq.familycalendar.database.event.Event;
import fr.alexandremarcq.familycalendar.databinding.RecyclerItemEventBinding;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {

    public EventAdapter() {
        super(new DiffCallBack());
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(
                RecyclerItemEventBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = getItem(position);
        holder.bind(event);
    }

    @Override
    protected Event getItem(int position) {
        return super.getItem(position);
    }

    public static class DiffCallBack extends DiffUtil.ItemCallback<Event> {

        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.id == newItem.id;
        }
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerItemEventBinding mBinding;

        public EventViewHolder(RecyclerItemEventBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Event event) {
            mBinding.setEvent(event);
            mBinding.executePendingBindings();
        }
    }
}
