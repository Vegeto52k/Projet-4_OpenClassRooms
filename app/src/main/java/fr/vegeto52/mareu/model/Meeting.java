package fr.vegeto52.mareu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.material.timepicker.MaterialTimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Vegeto52-PC on 09/03/2022.
 */
public class Meeting implements Parcelable {

    private String hallLetter;
    private String infos;
    private String people;
    private Date date;
    private Date startTime;
    private Date endTime;

    public Meeting(String hallLetter, String infos, String people, Date date, Date startTime, Date endTime) {
        this.hallLetter = hallLetter;
        this.infos = infos;
        this.people = people;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hallLetter);
        parcel.writeString(infos);
        parcel.writeString(people);
        parcel.writeLong(date.getTime());
        //   parcel.writeSerializable(date);
        parcel.writeLong(startTime.getTime());
        parcel.writeLong(endTime.getTime());
    }

    protected Meeting(Parcel in) {
        hallLetter = in.readString();
        infos = in.readString();
        people = in.readString();
        date = new Date(in.readLong());
        //  date = (java.util.Date) in.readSerializable();
        startTime = new Date(in.readLong());
        endTime = new Date(in.readLong());
    }


    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    public String getHallLetter() {
        return hallLetter;
    }

    public void setHallLetter(String hallLetter) {
        this.hallLetter = hallLetter;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
