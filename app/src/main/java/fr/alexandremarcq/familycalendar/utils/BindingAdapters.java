package fr.alexandremarcq.familycalendar.utils;

import android.content.Context;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.calendar.EventAdapter;
import fr.alexandremarcq.familycalendar.database.event.Event;

public class BindingAdapters {

    @BindingAdapter("eventList")
    public static void bindEvents(RecyclerView recyclerView, List<Event> events) {
        EventAdapter adapter = (EventAdapter) recyclerView.getAdapter();
        adapter.submitList(events);
    }
}
