package fr.alexandremarcq.familycalendar.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String firstName;
    public String lastName;
    public String phoneNumber;
}
