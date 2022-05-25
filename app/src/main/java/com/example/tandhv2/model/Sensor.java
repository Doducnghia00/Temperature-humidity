package com.example.tandhv2.model;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sensor")
public class Sensor {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String objectId;
    private String date;
    private String temperature;
    private String humidity;
    private String time;




    public Sensor(){}



    public Sensor(@NonNull String objectId, String date, String temperature, String humidity, String time) {
        this.objectId = objectId;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }


    @Override
    public String toString() {
        return "Sensor{" +
                "objectId='" + objectId + '\'' +
                ", date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

