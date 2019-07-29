package com.exampdm.moneybook.db;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.exampdm.moneybook.db.converter.DateConverter;

import com.exampdm.moneybook.db.dao.MoneyDAO;
import com.exampdm.moneybook.db.dao.MoneyTagJoinDAO;
import com.exampdm.moneybook.db.dao.TagDAO;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;
import com.exampdm.moneybook.db.entity.TagEntity;

@Database(entities = {MoneyEntity.class, TagEntity.class, MoneyTagJoin.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MoneyDAO moneyDAO();
    public  abstract TagDAO tagDAO();
    public abstract MoneyTagJoinDAO moneyTagJoinDAO();
    private static AppDatabase sInstance;

    public static final  String DATABASE_NAME ="money-book-db";


    public static AppDatabase getInstance(final Context context){
        if(sInstance == null){
            synchronized (AppDatabase.class){
                if(sInstance==null){
                    sInstance= Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }
                            }).build();
                }
            }
        }
        return sInstance;
    }


}
