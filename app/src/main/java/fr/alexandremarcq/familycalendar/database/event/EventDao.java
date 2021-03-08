package fr.alexandremarcq.familycalendar.database.event;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {
    @Insert
    long insert(Event event);

    @Delete
    void delete(Event event);

    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Query("SELECT * FROM event WHERE date LIKE :date")
    List<Event> getEventsByDate(String date);

    @Query("SELECT * FROM event, eventperson WHERE event.id=eventperson.idEvent AND date=:date AND idPerson=:personId AND ((startTime<=:startTime AND endTime>=:endTime) OR (startTime<=:startTime AND endTime>=:startTime) OR (startTime<=:endTime AND endTime>=:endTime) OR (startTime>=:startTime AND endTime<=:endTime) OR (startTime IS NULL AND endTime IS NULL))")
    List<Event> getEventsConflicts(String date, String startTime, String endTime, int personId);
}
