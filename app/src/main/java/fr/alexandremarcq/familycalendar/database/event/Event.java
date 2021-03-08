package fr.alexandremarcq.familycalendar.database.event;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Event implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String object;
    public String type;
    public String date;
    public String startTime;
    public String endTime;

    public Event(String title, String object, String type, String date, String startTime, String endTime) {
        this.title = title;
        this.object = object;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
