package fr.vegeto52.mareu.DI;

import fr.vegeto52.mareu.service.DummyMeetingApiService;
import fr.vegeto52.mareu.service.MeetingApiService;

/**
 * Created by Vegeto52-PC on 10/03/2022.
 */
public class DI {

    private static MeetingApiService service = new DummyMeetingApiService();


    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}
