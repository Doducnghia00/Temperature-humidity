package com.example.tandhv2.database;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tandhv2.model.Sensor;

import java.util.List;

@Dao
public interface SensorDAO {

    //Select
    @Query("SELECT * FROM sensor WHERE objectId = :objectId")
    Sensor getSensorByObjectId(String objectId);

    @Query("SELECT *FROM sensor")
    List<Sensor> getAll();



    //Insert
    @Insert
    void insertAll(Sensor... sensors);

    @Insert
    void insertSensor(Sensor sensor);

    //Delete
    @Delete
    void delete(Sensor sensor);

    @Delete
    public void deleteSensors(Sensor... sensor);

    @Query("DELETE FROM sensor")
    public void deleteAll();
}
