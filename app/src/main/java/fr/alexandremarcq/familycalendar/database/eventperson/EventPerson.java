package fr.alexandremarcq.familycalendar.database.eventperson;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EventPerson {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int idEvent;
    public int idPerson;
    public boolean accepted;
}
