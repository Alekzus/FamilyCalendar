package fr.alexandremarcq.familycalendar.database.eventperson;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;

public class EventPersonRepository {

    private MutableLiveData<List<EventPerson>> mEventPersons = new MutableLiveData<>();
    private EventPersonDao mDao;

    public EventPersonRepository(CalendarDatabase database) {
        mDao = database.eventPersonDao();
    }

    public void insertEventPerson(EventPerson event) {
        InsertAsyncTask task = new InsertAsyncTask(mDao);
        task.execute(event);
    }

    public void deleteEventPerson(EventPerson event) {
        DeleteAsyncTask task = new DeleteAsyncTask(mDao);
        task.execute(event);
    }

    public void findEventPerson(String date) {
        QueryAsyncTask task = new QueryAsyncTask(mDao);
        task.mRepository = this;
        task.execute(date);
    }

    public LiveData<List<EventPerson>> getSearchResults() {
        return mEventPersons;
    }

    private void asyncFinished(List<EventPerson> events) {
        mEventPersons.setValue(events);
    }

    private static class QueryAsyncTask extends AsyncTask<String, Void, List<EventPerson>> {

        private EventPersonDao mDao;
        private EventPersonRepository mRepository = null;

        QueryAsyncTask(EventPersonDao dao) {
            mDao = dao;
        }

        @Override
        protected List<EventPerson> doInBackground(String... strings) {
            return mDao.getAll();
        }

        @Override
        protected void onPostExecute(List<EventPerson> events) {
            mRepository.asyncFinished(events);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<EventPerson, Void, Void> {

        private EventPersonDao mDao;

        InsertAsyncTask(EventPersonDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(EventPerson... eventPeople) {
            mDao.insert(eventPeople[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<EventPerson, Void, Void> {

        private EventPersonDao mDao;

        DeleteAsyncTask(EventPersonDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(EventPerson... eventPeople) {
            mDao.delete(eventPeople[0]);
            return null;
        }
    }
}
