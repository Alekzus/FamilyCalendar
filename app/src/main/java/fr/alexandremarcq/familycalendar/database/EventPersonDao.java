package fr.alexandremarcq.familycalendar.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventPersonDao {
    @Insert
    void insertAll(EventPerson... eventpersons);

    @Delete
    void delete(EventPerson eventPerson);

    @Query("SELECT * FROM eventperson")
    List<EventPerson> getAll();
}
