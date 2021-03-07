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
}
