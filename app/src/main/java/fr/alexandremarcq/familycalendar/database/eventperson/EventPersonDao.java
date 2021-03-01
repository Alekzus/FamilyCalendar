package fr.alexandremarcq.familycalendar.database.eventperson;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventPersonDao {
    @Insert
    void insert(EventPerson eventperson);

    @Delete
    void delete(EventPerson eventPerson);

    @Query("SELECT * FROM eventperson")
    List<EventPerson> getAll();
}
