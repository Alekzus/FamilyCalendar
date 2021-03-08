package fr.alexandremarcq.familycalendar.eventdetails;

import androidx.lifecycle.ViewModel;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.event.Event;
import fr.alexandremarcq.familycalendar.database.event.EventRepository;
import fr.alexandremarcq.familycalendar.database.eventperson.EventPersonRepository;

public class EventDetailsViewModel extends ViewModel {

    private EventRepository mEventRepository;
    private EventPersonRepository mEventPersonRepository;
    private Event mEvent;

    public EventDetailsViewModel(CalendarDatabase database) {
        mEventRepository = new EventRepository(database);
        mEventPersonRepository = new EventPersonRepository(database);
    }

    public Event getEvent() {
        return mEvent;
    }

    public void setEvent(Event event) {
        mEvent = event;
    }

    public void deleteEvent() {
        mEventRepository.deleteEvent(mEvent);
        mEventPersonRepository.deleteByEventId(mEvent.id);
    }
}