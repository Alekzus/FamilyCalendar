package fr.alexandremarcq.familycalendar.utils;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.adapters.EventAdapter;
import fr.alexandremarcq.familycalendar.database.event.Event;

public class BindingAdapters {

    @BindingAdapter("eventList")
    public static void bindEvents(RecyclerView recyclerView, List<Event> events) {
        EventAdapter adapter = (EventAdapter) recyclerView.getAdapter();
        adapter.submitList(events);
    }

    @BindingAdapter({"startTime", "endTime"})
    public static void bindTime(TextView textView, String startTime, String endTime) {
        if (startTime == null && endTime == null) {
            textView.setText(R.string.all_day);
        } else {
            textView.setText(textView.getContext().getString(R.string.event_period, startTime, endTime));
        }
    }
}
