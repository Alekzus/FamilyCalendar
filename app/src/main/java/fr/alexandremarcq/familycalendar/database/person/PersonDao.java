package fr.alexandremarcq.familycalendar.database.person;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    void insert(Person person);

    @Delete
    void delete(Person person);

    @Query("SELECT * FROM person")
    List<Person> getAll();
}
