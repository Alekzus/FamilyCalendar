package fr.alexandremarcq.familycalendar.database.event;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;

public class EventRepository {

    private MutableLiveData<List<Event>> mEvents = new MutableLiveData<>();
    private EventDao mDao;

    public EventRepository(CalendarDatabase database) {
        mDao = database.eventDao();
    }

    public void insertEvent(Event event) {
        InsertAsyncTask task = new InsertAsyncTask(mDao);
        task.execute(event);
    }

    public void deleteEvent(Event event) {
        DeleteAsyncTask task = new DeleteAsyncTask(mDao);
        task.execute(event);
    }

    public void findEvent(String date) {
        QueryAsyncTask task = new QueryAsyncTask(mDao);
        task.mRepository = this;
        task.execute(date);
    }

    public LiveData<List<Event>> getSearchResults() {
        return mEvents;
    }

    private void asyncFinished(List<Event> events) {
        mEvents.setValue(events);
    }

    private static class QueryAsyncTask extends AsyncTask<String, Void, List<Event>> {

        private EventDao mDao;
        private EventRepository mRepository = null;

        QueryAsyncTask(EventDao dao) {
            mDao = dao;
        }

        @Override
        protected List<Event> doInBackground(String... strings) {
            return mDao.getEventsByDate(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            mRepository.asyncFinished(events);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao mDao;

        InsertAsyncTask(EventDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            mDao.insert(events[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao mDao;

        DeleteAsyncTask(EventDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            mDao.delete(events[0]);
            return null;
        }
    }
}
