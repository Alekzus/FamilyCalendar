package fr.alexandremarcq.familycalendar.database.person;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;

public class PersonRepository {

    private MutableLiveData<List<Person>> mPersons = new MutableLiveData<>();
    private PersonDao mDao;

    public PersonRepository(CalendarDatabase database) {
        mDao = database.personDao();
    }

    public void insertPerson(Person person) {
        InsertAsyncTask task = new InsertAsyncTask(mDao);
        task.execute(person);
    }

    public void deletePerson(Person person) {
        DeleteAsyncTask task = new DeleteAsyncTask(mDao);
        task.execute(person);
    }

    public void getPersons() {
        GetAllAsyncTask task = new GetAllAsyncTask(mDao);
        task.mRepository = this;
        task.execute();
    }

    public LiveData<List<Person>> getResults() {
        return mPersons;
    }

    private void asyncFinished(List<Person> persons) {
        mPersons.setValue(persons);
    }

    private static class GetAllAsyncTask extends AsyncTask<Void, Void, List<Person>> {

        private PersonDao mDao;
        private PersonRepository mRepository = null;

        GetAllAsyncTask(PersonDao dao) {
            mDao = dao;
        }

        @Override
        protected List<Person> doInBackground(Void... voids) {
            return mDao.getAll();
        }

        @Override
        protected void onPostExecute(List<Person> persons) {
            mRepository.asyncFinished(persons);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDao mDao;

        InsertAsyncTask(PersonDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Person... persons) {
            mDao.insert(persons[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDao mDao;

        DeleteAsyncTask(PersonDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Person... persons) {
            mDao.delete(persons[0]);
            return null;
        }
    }
}
