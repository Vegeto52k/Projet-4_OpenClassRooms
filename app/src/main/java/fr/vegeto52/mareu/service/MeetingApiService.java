package fr.vegeto52.mareu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.vegeto52.mareu.model.Meeting;

/**
 * Created by Vegeto52-PC on 10/03/2022.
 */
public interface MeetingApiService {

    /**
     * Get all Meeting
     *
     * @return
     */
    List<Meeting> getMeeting();

    /**
     * Delete Meeting
     *
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create Meeting
     *
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Filter by date
     *
     * @param date
     */
    ArrayList<Meeting> getMeetingFilteredByDate(Date date);

    /**
     * Filter by hall
     *
     * @param hallLetter
     */
    ArrayList<Meeting> getMeetingFilteredByHall(String hallLetter);

}
