package hr.fer.zemris.java.tecaj.hw5.db;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class instance for testing the StudentDatabase and all of its functionality
 */
public class StudentDB {

    /**
     * Constant for reading the path provided
     */
    private static final String PATH_TO_FILE = "./database.txt";

    /**
     * Application exit code
     */
    private static final int APPLICATION_EXIT_CODE = -1;

    /**
     * Entry point of the application
     *
     * @param args list of arguments provided( all ignored )
     */
    public static void main(String[] args) {

        List<String> lines = null;

        try {

            lines = Files.readAllLines(
                    Paths.get(PATH_TO_FILE),
                    StandardCharsets.UTF_8
            );

        } catch (IOException e) {
            System.err.println("Error in reading the database file. " +
                    "Path provided: " + PATH_TO_FILE);
            System.exit(APPLICATION_EXIT_CODE);
        }

        //populate the student database with values
        StudentDatabase studentDatabase = null;
        try {

            studentDatabase = new StudentDatabase(lines);
        } catch (DatabaseFileFormatException e) {
            System.err.println("Student database not formatted correctly");
            System.exit(APPLICATION_EXIT_CODE);
        }

        readUsedInputUntilExitIsRead(studentDatabase);

        System.out.println("Goodbye!");
    }

    /*
     * Method used for reading the user input until the 'exit' was entered. Everything
     * after is ignored
     */
    private static void readUsedInputUntilExitIsRead(StudentDatabase studentDatabase) {

        //read the user input
        Scanner scanner = new Scanner(System.in);

        System.out.print("> ");
        String input = scanner.nextLine();

        List<StudentRecord> listOfRecords;
        while (!(input.toLowerCase().startsWith("exit"))) {

            listOfRecords = readSingleUserInputLine(studentDatabase, input);

            DatabaseUtil.printStudentData(listOfRecords);
            listOfRecords.clear();

            System.out.print("\n> ");
            input = scanner.nextLine();
        }
        
        scanner.close();
    }

    /*
     * Method reads a single user input and determines the type of if -- index query or query
     */
    private static List<StudentRecord> readSingleUserInputLine(StudentDatabase studentDatabase, String input) {

        List<StudentRecord> listOfRecords = new ArrayList<>();
        Scanner lineScanner = new Scanner(input);
        String operator = null;
        String afterQueryLine = null;

        try {
            operator = lineScanner.next();
            afterQueryLine = lineScanner.nextLine();

        } catch (NoSuchElementException e) {
            System.err.println("Illegal input format provided!");
        }

        if (operator.equals("indexquery")) {


            try {

                String[] values = input.split("\"");
                listOfRecords.add(studentDatabase.forJMBAG(values[1]));

            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Wrong indexquery data format! provided: " + input);
            }

        } else if (operator.equals("query")) {

            QueryFilter queryFilter;

            try {

                queryFilter = new QueryFilter(afterQueryLine);
                listOfRecords = studentDatabase.filter(queryFilter);

            } catch (QueryFormatException e) {
                System.err.println("Error on reading query line: " + afterQueryLine);
            }

        }
        
        lineScanner.close();
        return listOfRecords;
    }
}
