package com.exampdm.moneybook;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.exampdm.moneybook.db.AppDatabase;
import com.exampdm.moneybook.db.dao.MoneyDAO;
import com.exampdm.moneybook.db.dao.TagDAO;
import com.exampdm.moneybook.db.entity.MoneyEntity;

import java.util.List;

public class MBRepository {
    private MoneyDAO mMoneyDAO;
    private TagDAO mTagDAO;
    private LiveData<List<MoneyEntity>> mAllMoney;

    public MBRepository(Application application) {

        AppDatabase db = AppDatabase.getInstance(application);
        mMoneyDAO = db.moneyDAO();
        mTagDAO = db.tagDAO();
        mAllMoney = mMoneyDAO.getAllMoney();
    }

    public LiveData<List<MoneyEntity>> getAllMoney(){
        return mAllMoney;
    }

    public void insert(MoneyEntity money){
        new insertAsyncTask(mMoneyDAO).execute(money);
    }

    private static class insertAsyncTask extends AsyncTask<MoneyEntity, Void, Void>{
        private MoneyDAO mAsyncTaskDao;
        insertAsyncTask(MoneyDAO dao){
            mAsyncTaskDao= dao;
        }

        @Override
        protected Void doInBackground(final MoneyEntity... params){
            mAsyncTaskDao.insertAnItem(params[0]);
            return null;
        }
    }

    public void delete(MoneyEntity money){
        new deleteAsyncTask(mMoneyDAO).execute(money);
    }

    private static class deleteAsyncTask extends AsyncTask<MoneyEntity, Void, Void>{
        private MoneyDAO mAsyncTaskDao;
        deleteAsyncTask(MoneyDAO dao){
            mAsyncTaskDao= dao;
        }

        @Override
        protected Void doInBackground(final MoneyEntity... params){
            mAsyncTaskDao.deleteAnItem(params[0]);
            return null;
        }
    }


    public void deleteAllMoney(){
        new deleteAllAsyncTask(mMoneyDAO).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<MoneyEntity, Void, Void>{
        private MoneyDAO mAsyncTaskDao;
        deleteAllAsyncTask(MoneyDAO dao){
            mAsyncTaskDao= dao;
        }

        @Override
        protected Void doInBackground(final MoneyEntity... params){
            mAsyncTaskDao.deleteAllItem();
            return null;
        }
    }
}
