package com.firebase.ontapckandoid.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.firebase.ontapckandoid.En;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {En.class}, version = 1)
public abstract class EnDB extends RoomDatabase {

    private static final String DB_NAME = "ENDATABASE";
    public static ExecutorService service = Executors.newFixedThreadPool(4);

    private static EnDB enDB;

    public abstract EnDao enDao();

    public static EnDB getInstance(Context context){
        if(enDB == null)
            enDB = Room.databaseBuilder(context,EnDB.class,DB_NAME)
                    .allowMainThreadQueries().build();
        return enDB;
    }

}
