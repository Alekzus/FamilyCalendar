package fr.alexandremarcq.familycalendar.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class, EventPerson.class, Person.class}, version = 1)
public abstract class CalendarDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
    public abstract PersonDao personDao();
    public abstract EventPersonDao eventPersonDao();
    private static final String DB_NAME = "calendarDatabase.db";
    private static volatile CalendarDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 1;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static synchronized CalendarDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CalendarDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = create(context);
                }
            }
        }
        return INSTANCE;
    }

    private static CalendarDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                CalendarDatabase.class,
                DB_NAME).build();
    }
}



