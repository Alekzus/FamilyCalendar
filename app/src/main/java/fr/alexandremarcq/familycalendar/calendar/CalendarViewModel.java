package fr.alexandremarcq.familycalendar.calendar;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Locale;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.event.Event;
import fr.alexandremarcq.familycalendar.database.event.EventRepository;
import fr.alexandremarcq.familycalendar.utils.UtilsFunctions;

public class CalendarViewModel extends ViewModel {

    private final EventRepository mRepository;
    public LiveData<List<Event>> mEvents;

    private MutableLiveData<Event> _navigateToDetails = new MutableLiveData<>();
    public LiveData<Event> mNavigateToDetails = _navigateToDetails;

    public CalendarViewModel(CalendarDatabase database, long time) {
        int[] date = UtilsFunctions.dateConverter(time);
        mRepository = new EventRepository(database);
        mEvents = mRepository.getSearchResults();
        setDate(date[0], date[1], date[2]);
    }

    public void setDate(int day, int month, int year) {
        mRepository.findEvent(
                String.format(Locale.getDefault(), "%02d/%02d/%d", day, month + 1, year)
        );
    }

    public void navigateToDetails(Event event) {
        _navigateToDetails.postValue(event);
    }

    public void onNavigatedToDetails() {
        _navigateToDetails.postValue(null);
    }
}
