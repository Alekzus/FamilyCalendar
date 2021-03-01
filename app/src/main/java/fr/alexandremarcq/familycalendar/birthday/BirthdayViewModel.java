package fr.alexandremarcq.familycalendar.birthday;

import androidx.lifecycle.ViewModel;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.event.Event;
import fr.alexandremarcq.familycalendar.database.event.EventRepository;

public class BirthdayViewModel extends ViewModel {

    private EventRepository mRepository;

    public BirthdayViewModel(CalendarDatabase database) {
        mRepository = new EventRepository(database);
    }

    public void addBirthday(String title, String date) {
        mRepository.insertEvent(new Event(title, null, "Anniversaire", date, "0:00", "23:59"));
    }
}