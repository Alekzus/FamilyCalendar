package fr.alexandremarcq.familycalendar.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.alexandremarcq.familycalendar.databinding.ItemDateBinding;
import fr.alexandremarcq.familycalendar.date.Date;
import fr.alexandremarcq.familycalendar.utils.NavigateToDate;

class DateAdapter extends ListAdapter<Date, DateAdapter.DateViewHolder> {

    private final CustomClickListener mListener;

    protected DateAdapter(CustomClickListener listener) {
        super(DateDiffCallback.getInstance());
        mListener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DateViewHolder(
                ItemDateBinding.inflate(LayoutInflater.from(parent.getContext()))
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Date date = getItem(position);
        holder.bind(date);
        holder.itemView.setOnClickListener(v -> mListener.onClick(date));
    }

    public static class DateDiffCallback extends DiffUtil.ItemCallback<Date> {

        private DateDiffCallback() {}

        private static DateDiffCallback INSTANCE = new DateDiffCallback();

        public static DateDiffCallback getInstance() {
            return INSTANCE;
        }

        @Override
        public boolean areItemsTheSame(@NonNull Date oldItem, @NonNull Date newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Date oldItem, @NonNull Date newItem) {
            return oldItem.getDay().equals(newItem.getDay())
                    && oldItem.getNumber() == newItem.getNumber();
        }
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        private final ItemDateBinding mBinding;

        public DateViewHolder(ItemDateBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Date date) {
            mBinding.setDate(date);
            mBinding.executePendingBindings();
        }
    }

    public static class CustomClickListener {
        private final NavigateToDate mNavigateToDate;

        public CustomClickListener(NavigateToDate navigateToDate) {
            mNavigateToDate = navigateToDate;
        }

        public void onClick(Date date) {
            mNavigateToDate.navigateTo(date);
        }
    }
}
