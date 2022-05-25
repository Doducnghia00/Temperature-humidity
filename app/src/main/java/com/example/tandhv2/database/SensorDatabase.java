package com.example.tandhv2.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tandhv2.model.Sensor;

@Database(entities = {Sensor.class}, version = 1)
public abstract class SensorDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "sensor.db";
    private static SensorDatabase instance;

    public static synchronized SensorDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),SensorDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract SensorDAO sensorDAO();
}
