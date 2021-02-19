package fr.alexandremarcq.familycalendar.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class, EventPerson.class, Person.class}, version = 1)
public abstract class CalendarDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
    public abstract PersonDao personDao();
    public abstract EventPersonDao eventPersonDao();
    private static final String DB_NAME = "calendarDatabase.db";
    private static volatile CalendarDatabase instance;

    public static synchronized CalendarDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static CalendarDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                CalendarDatabase.class,
                DB_NAME).build();
    }
}



