package fr.alexandremarcq.familycalendar.birthday;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.event.Event;
import fr.alexandremarcq.familycalendar.database.event.EventRepository;
import fr.alexandremarcq.familycalendar.database.person.Person;
import fr.alexandremarcq.familycalendar.database.person.PersonRepository;

public class BirthdayViewModel extends ViewModel {

    private EventRepository mEventRepository;
    private PersonRepository mPersonRepository;
    public LiveData<List<Person>> mPeople;

    public BirthdayViewModel(CalendarDatabase database) {
        mEventRepository = new EventRepository(database);
        mPersonRepository = new PersonRepository(database);
        mPeople = mPersonRepository.getResults();
        mPersonRepository.getPersons();
    }

    public void addBirthday(String title, String date, String type) {
        mEventRepository.insertEvent(new Event(title, null, type, date, null, null));
    }
}