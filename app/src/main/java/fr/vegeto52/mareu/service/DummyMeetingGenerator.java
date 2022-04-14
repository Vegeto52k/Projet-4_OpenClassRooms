package fr.vegeto52.mareu.service;

import android.icu.util.Calendar;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import fr.vegeto52.mareu.model.Meeting;

/**
 * Created by Vegeto52-PC on 09/03/2022.
 */
public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting("B", "Conseil Administratif", "bill@gmail.com, eva@gmail.com", new Date(1647216000000L), new Date(50400000), new Date(55800000)),
            new Meeting("A", "Marketing", "james@gmail.com, kevin@gmail.com, bernard@gmail.com", new Date(1647302400000L), new Date(55800000), new Date(59400000)),
            new Meeting("D", "Problème client", "julien@gmail.com, marie@gmail.com", new Date(1647216000000L), new Date(55800000), new Date(63000000)),
            new Meeting("F", "Formation", "liam@gmail.com, emma@gmail.com, olivia@gmail.com, noah@gmail.com", new Date(1647388800000L), new Date(51300000), new Date(55800000)),
            new Meeting("H", "Entraide", "william@gmail.com, alice@gmail.com, thomas@gmail.com", new Date(1647216000000L), new Date(36900000), new Date(2700000)),
            new Meeting("J", "Projet Maréu", "mia@gmail.com, jacob@gmail.com, nathan@gmail.com, charlie@gmail.com", new Date(1647302400000L), new Date(41400000), new Date(55800000)),
            new Meeting("I", "Entretien", "logan@gmail.com, edouard@gmail.com", new Date(1647475200000L), new Date(48600000), new Date(53100000)),
            new Meeting("C", "Service RH", "amelia@gmail.com, leo@gmail.com, florence@gmail.com", new Date(1647388800000L), new Date(41400000), new Date(55800000)),
            new Meeting("E", "Projet Entrevoisins", "felix@gmail.com, charlotte@gmail.com, zoe@gmail.com, lucas@gmail.com", new Date(1647216000000L), new Date(59400000), new Date(63000000)),
            new Meeting("G", "Annualisation", "emily@gmail.com, raphael@gmail.com", new Date(1647388800000L), new Date(41400000), new Date(59400000)),
            new Meeting("A", "Formation", "simone@gmail.com, alexis@gmail.com, sofia@gmail.com", new Date(1647561600000L), new Date(30600000), new Date(41400000)),
            new Meeting("F", "Nouveau Projet ", "charles@gmail.com, matthew@gmail.com, livia@gmail.com, victor@gmail.com, adam@gmail.com", new Date(1647475200000L), new Date(52200000), new Date(55800000))
    );

    static List<Meeting> generateMeeting(){
        return new ArrayList<>(DUMMY_MEETING);
    }

}

