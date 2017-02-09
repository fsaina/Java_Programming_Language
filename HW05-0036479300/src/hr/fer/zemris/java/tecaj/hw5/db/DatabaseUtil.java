package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.List;


/**
 * Database util class that contains all Database code redundacies and helping methods
 */
public class DatabaseUtil {


    /**
     * Method used for printing out the upper and lower part of the frame
     *
     * @param distance array of int values to be used
     */
    public static void printFrameString(int... distance) {
        StringBuilder sb = new StringBuilder();
        for (int dist : distance) {
            sb.append("+" + repearCharacterNTimes('=', dist));
        }
        sb.append("+");
        System.out.println(sb.toString());
    }

    /**
     * Method repeats the given character c n times
     *
     * @param c character to be repeated
     * @param n number of times to repeat the character
     * @return String with the number of times c character was repeated
     */
    private static String repearCharacterNTimes(Character c, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n + 2; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Method used for printing out and formatting the output String
     *
     * @param studentList list of students to be printed out
     */
    public static void printStudentData(List<StudentRecord> studentList) {

        if(studentList == null)
            throw new IllegalArgumentException("Illegal argument provided was null");

        if (studentList.isEmpty() == true) {
            System.out.println("Records selected: 0");
            return;
        }

        int jmbagLenght = 0;
        int lastNameLenght = 0;
        int firstNameLenght = 0;
        int finalGradeLenght = 0;

        //find out the largest sizes per every element
        for (StudentRecord std : studentList) {
            jmbagLenght = std.getJmbag().length();
            if (std.getLastName().length() > lastNameLenght) lastNameLenght = std.getLastName().length();
            if (std.getFirstName().length() > firstNameLenght) firstNameLenght = std.getFirstName().length();
            finalGradeLenght = std.getFinalGrade().length();

        }

        //based upon the longest strings in out whole database create a aproppriete format
        String format = "| %" + jmbagLenght
                + "s | %" + lastNameLenght
                + "s | %" + firstNameLenght
                + "s | %" + finalGradeLenght
                + "s |";

        //upper frame
        DatabaseUtil.printFrameString(jmbagLenght, lastNameLenght, firstNameLenght, finalGradeLenght);

        //everything in between
        for (StudentRecord std : studentList) {
            System.out.println(String.format(format, std.getJmbag(), std.getLastName(), std.getFirstName(), std.getFinalGrade()));
        }

        //lower frame
        DatabaseUtil.printFrameString(jmbagLenght, lastNameLenght, firstNameLenght, finalGradeLenght);
        System.out.println(String.format("Records selected: %d", studentList.size()));
    }
}
