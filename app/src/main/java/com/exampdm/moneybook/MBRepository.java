package com.exampdm.moneybook;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.exampdm.moneybook.db.AppDatabase;
import com.exampdm.moneybook.db.dao.MoneyDAO;
import com.exampdm.moneybook.db.dao.MoneyTagJoinDAO;
import com.exampdm.moneybook.db.dao.TagDAO;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;
import com.exampdm.moneybook.db.entity.TagEntity;

import java.util.List;


public class MBRepository {
    private MoneyDAO mMoneyDAO;
    private TagDAO mTagDAO;
    private MoneyTagJoinDAO mItemTagDao;
    private LiveData<List<MoneyEntity>> mAllMoney;
    private LiveData<List<TagEntity>> mAllTags;
    private LiveData<List<MoneyTagJoin>> mAllMTJ;
    private static List<String> mItemTags;
    private static List<String> tagString;



    public MBRepository(Application application) {

        AppDatabase db = AppDatabase.getInstance(application);
        mMoneyDAO = db.moneyDAO();
        mTagDAO = db.tagDAO();
        mItemTagDao = db.moneyTagJoinDAO();
        mAllMoney = mMoneyDAO.getAllMoney();
        mAllTags = mTagDAO.getAllTag();
        mAllMTJ= mItemTagDao.getItemsTags();


    }


    public LiveData<List<MoneyEntity>> getAllMoney() {
        return mAllMoney;
    }

    public LiveData<List<TagEntity>> getAllTags() {
        return mAllTags;
    }

    public LiveData<List<MoneyTagJoin>> getAllMjT(){return mAllMTJ;}

    public List<String> getTagList() {
        new getTagAsync(mTagDAO).execute();
        return tagString;
    }

    private static class getTagAsync extends AsyncTask<Void, Void, List<String>> {

        private TagDAO mAsyncDao;

        getTagAsync(TagDAO dao) {
            mAsyncDao = dao;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return mAsyncDao.getTagList();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            setStringList(strings);
        }
    }

    public List<String> getItemTags(MoneyEntity item) {
        new getItemTagsAsync(mItemTagDao).execute(item);
        return mItemTags;
    }

    private static class getItemTagsAsync extends AsyncTask<MoneyEntity, Void, List<String>> {

        private MoneyTagJoinDAO mAsyncDao;

        getItemTagsAsync(MoneyTagJoinDAO dao) {
            mAsyncDao = dao;
        }

        @Override
        protected List<String> doInBackground(MoneyEntity... item) {
            return mAsyncDao.getTagForItem(item[0].getId());
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            mItemTags = strings;
        }
    }


    private static void setStringList(List<String> string) {
        tagString = string;
    }

    public void insert(MoneyEntity money) {
        new insertAsyncTask(mMoneyDAO).execute(money);
}

    private static class insertAsyncTask extends AsyncTask<MoneyEntity, Void, Void> {
        private MoneyDAO mAsyncTaskDao;

        insertAsyncTask(MoneyDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MoneyEntity... params) {
            mAsyncTaskDao.insertAnItem(params[0]);
            return null;
        }
    }

    public void delete(MoneyEntity money) {
        new deleteAsyncTask(mMoneyDAO).execute(money);
    }

    private static class deleteAsyncTask extends AsyncTask<MoneyEntity, Void, Void> {
        private MoneyDAO mAsyncTaskDao;

        deleteAsyncTask(MoneyDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MoneyEntity... params) {
            mAsyncTaskDao.deleteAnItem(params[0]);
            return null;
        }
    }


    public void deleteAllMoney() {
        new deleteAllAsyncTask(mMoneyDAO).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<MoneyEntity, Void, Void> {
        private MoneyDAO mAsyncTaskDao;

        deleteAllAsyncTask(MoneyDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MoneyEntity... params) {
            mAsyncTaskDao.deleteAllItem();
            return null;
        }
    }
    public void deleteAllTag() {
        new deleteAllTagAsyncTask(mTagDAO).execute();
    }

    private static class deleteAllTagAsyncTask extends AsyncTask<TagEntity, Void, Void> {
        private TagDAO mAsyncTaskDao;

        deleteAllTagAsyncTask(TagDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TagEntity... params) {
            mAsyncTaskDao.deleteAllTag();
            return null;
        }
    }

    public void deleteAllItemTags() {
        new deleteAllItemTagsAsync(mItemTagDao).execute();
    }

    private static class deleteAllItemTagsAsync extends AsyncTask<MoneyTagJoin, Void, Void> {
        private MoneyTagJoinDAO mAsyncTaskDao;

        deleteAllItemTagsAsync(MoneyTagJoinDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MoneyTagJoin... params) {
            mAsyncTaskDao.deleteAllItemTags();
            return null;
        }
    }

    /*public void insertAllTags(TagEntity[] tags) {
        new insertAllTagsAsync(mTagDAO).execute(tags);
    }

    private static class insertAllTagsAsync extends AsyncTask<TagEntity[], Void, Void> {
        private TagDAO mAsyncTaskDao;

        insertAllTagsAsync(TagDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final TagEntity[]... tagEntities) {
            mAsyncTaskDao.insertAllTags(tagEntities[0]);
            return null;
        }
    }*/

    public void insertTag(TagEntity tag) {
        new insertTagAsyncTask(mTagDAO).execute(tag);
    }

    private static class insertTagAsyncTask extends AsyncTask<TagEntity, Void, Void> {
        private TagDAO mAsyncTaskDao;

        insertTagAsyncTask(TagDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TagEntity... params) {
            mAsyncTaskDao.insertTag(params[0]);
            return null;
        }
    }

    public void insertItemTag(MoneyTagJoin itemTags) {
        new insertItemTagAsync(mItemTagDao).execute(itemTags);
    }

    private static class insertItemTagAsync extends AsyncTask<MoneyTagJoin, Void, Void> {
        private MoneyTagJoinDAO mAsyncDao;

        insertItemTagAsync(MoneyTagJoinDAO dao) {
            mAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final MoneyTagJoin... params) {
            mAsyncDao.insertItemTag(params[0]);
            return null;
        }
    }

    public void clearItemTags() {
        new clearItemTagsAsync(mItemTagDao, mTagDAO).execute();
    }

    private static class clearItemTagsAsync extends AsyncTask<MoneyTagJoin, Void, Void> {
        private MoneyTagJoinDAO mAsyncTaskDao;
        private TagDAO tagDaoAsync;
        clearItemTagsAsync(MoneyTagJoinDAO dao, TagDAO tagDAO) {
            mAsyncTaskDao = dao;
            tagDaoAsync=tagDAO;
        }

        @Override
        protected Void doInBackground(final MoneyTagJoin... params) {
            List<MoneyTagJoin> oldTags= mAsyncTaskDao.getOldItemTags();
            for (MoneyTagJoin currentTag: oldTags
                 ) {
                mAsyncTaskDao.clearItemTags(currentTag.getItemId());
            }
            tagDaoAsync.deleteUnusedTag();
            return null;
        }
    }

}
