package fr.alexandremarcq.familycalendar.contact;

import androidx.lifecycle.ViewModel;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.person.Person;
import fr.alexandremarcq.familycalendar.database.person.PersonRepository;

public class ContactViewModel extends ViewModel {

    private PersonRepository mRepository;

    public ContactViewModel(CalendarDatabase database) {
        mRepository = new PersonRepository(database);
    }

    public void addPerson(String lastName, String firstName, String phoneNumber) {
        mRepository.insertPerson(new Person(firstName, lastName, phoneNumber));
    }
}