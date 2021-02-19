package fr.alexandremarcq.familycalendar.addevent;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.alexandremarcq.familycalendar.MainActivity;
import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.Event;
import fr.alexandremarcq.familycalendar.database.EventDao;
import fr.alexandremarcq.familycalendar.database.Person;
import fr.alexandremarcq.familycalendar.database.PersonDao;

public class AddEventViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> _allDayChecked = new MutableLiveData<>(Boolean.FALSE);
    public LiveData<Boolean> mAllDayChecked = _allDayChecked;

    private final MutableLiveData<Boolean> _timeIsValid = new MutableLiveData<>(Boolean.FALSE);
    public LiveData<Boolean> mTimeIsValid = _timeIsValid;

    public AddEventViewModel(@NonNull Application application) {
        super(application);
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

    public void addEvent(String title, String object, String type, String date, String startTime, String endTime){
        CalendarDatabase db = CalendarDatabase.getInstance(getApplication());

        EventDao e = db.eventDao();

        AsyncTask.execute(new Runnable() {
            @Override
          public void run() {
              e.insert(new Event(title,object,type,date,startTime,endTime));
          }
        });
    }
}