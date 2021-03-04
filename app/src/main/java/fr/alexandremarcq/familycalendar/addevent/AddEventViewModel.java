package fr.alexandremarcq.familycalendar.addevent;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.event.Event;
import fr.alexandremarcq.familycalendar.database.event.EventRepository;

public class AddEventViewModel extends ViewModel {

    private EventRepository mRepository;
    private SharedPreferences mPreferences;

    private final MutableLiveData<Boolean> _allDayChecked = new MutableLiveData<>(Boolean.FALSE);
    public LiveData<Boolean> mAllDayChecked = _allDayChecked;

    private final MutableLiveData<Boolean> _timeIsValid = new MutableLiveData<>(Boolean.FALSE);
    public LiveData<Boolean> mTimeIsValid = _timeIsValid;

    private final MutableLiveData<List<String>> _eventTypes = new MutableLiveData<>();
    public LiveData<List<String>> mEventTypes = _eventTypes;

    public AddEventViewModel(CalendarDatabase database, SharedPreferences preferences) {
        mRepository = new EventRepository(database);
        mPreferences = preferences;
        _eventTypes.postValue(getTypes());
    }

    private List<String> getTypes() {
        List<String> values = new ArrayList<>();
        Map<String, ?> types = mPreferences.getAll();
        types.forEach((key, value) -> values.add((String) value));
        return values;
    }

    public void checkOnAllDay() {
        _allDayChecked.postValue(!_allDayChecked.getValue());
    }

    public void checkTime(int fromHour, int fromMinute, int toHour, int toMinute) {
        _timeIsValid.postValue(
                toHour > fromHour
                        || fromHour == toHour && toMinute > fromMinute
        );
    }

    public void addEvent(String title, String object, String type, String date, String startTime, String endTime) {
        mRepository.insertEvent(new Event(title, object, type, date, startTime, endTime));
    }
}