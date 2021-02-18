package fr.alexandremarcq.familycalendar.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String object;
    public String date;
    public String startTime;
    public String endTime;
}
