package hr.fer.zemris.web.glasanje;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Controller class for the RecordModel object. This class contains all the logic necessary for reading,
 * updating and manipulating the definition and result files that contain data in the format of the
 * RecordModel object.
 *
 * @author Filip Saina
 */
public class RecordController {

    /**
     * Public Comparator object used for id compaction of records (lower is better)
     */
    public static final Comparator<RecordModel> byIdComparator = new Comparator<RecordModel>() {
        @Override
        public int compare(RecordModel o1, RecordModel o2) {
            int n1 = Integer.parseInt(o1.getId().substring(0, 1));
            int n2 = Integer.parseInt(o2.getId().substring(0, 1));

            if (n1 < n2)
                return -1;
            else if (n1 == n2)
                return 0;

            return 1;
        }
    };
    /**
     * Public Comparator object used for number of votes comparation of records (higher is better)
     */
    public static final Comparator<RecordModel> byVotesComparator = new Comparator<RecordModel>() {
        @Override
        public int compare(RecordModel o1, RecordModel o2) {
            if (o1.getVotes() > o2.getVotes())
                return -1;
            else if (o1.getVotes() == o2.getVotes())
                return 0;
            else
                return 1;
        }
    };
    /**
     * Constant containing the relative path of the voting decision file
     */
    private static final String DEFINITION_PATH = "/WEB-INF/glasanje-definicija.txt";
    /**
     * Constant containing the relative path of the voting results file
     */
    private static final String RESULT_PATH = "/WEB-INF/glasanje-rezultati.txt";
    /**
     * List containing all the bands currently active
     */
    private List<RecordModel> bands = new ArrayList<>();
    /**
     * Path to the opened definition file
     */
    @SuppressWarnings("unused")
	private Path definition;
    /**
     * Path to the opened result file
     */
    private Path result;

    /**
     * Default class constructor
     *
     * @param context ServletContext class object required for manipulation the files relative to a path
     * @throws IOException            when the file can not be read
     * @throws IllegalAccessException when a operation on a file is not permitted
     */
    public RecordController(ServletContext context) throws IOException, IllegalAccessException {
        readDefinitionFile(context);
        readResultFile(context);
    }

    /**
     * Used for reading the result file location defined as a constant in this class. If a record file is not
     * found it is created with voting values set to 0.
     *
     * @param context ServletContext used for file manipulation
     * @throws IOException thrown when the file being read is not accessible
     */
    private void readResultFile(ServletContext context) throws IOException {
        String fileName = context.getRealPath(RESULT_PATH);
        result = Paths.get(fileName);

        if (!result.toFile().exists()) {
            //create new file

            if (bands == null) {
                throw new IOException("Error reading the bands file!");
            }

            createFileWithInitialVotingResults(bands, result);
        }

        Map<String, Integer> votes = readVotingResultsFromFile(result);

        //update bands list with votes
        for (RecordModel band : bands) {
            band.setVotes(votes.get(band.getId()));
        }
    }

    /**
     * Used for creating a new voting results file with all the values set to 0
     *
     * @param bands List of all bands and their names
     * @param file  Path object reffereing to the location of the file to be read
     * @throws IOException when the file location is not permitted to be written
     */
    private void createFileWithInitialVotingResults(List<RecordModel> bands, Path file) throws IOException {

        StringBuilder votingResults = new StringBuilder();

        for (RecordModel record : bands) {
            record.setVotes(0);
            String line = record.getId() + "\t" + record.getVotes() + "\n";
            votingResults.append(line);
        }

        writeStringToFile(file, votingResults.toString());
    }

    /**
     * Method used for writing a given string to a file location
     *
     * @param file Path object to that file
     * @param s    String to be written inside
     * @throws IOException thrown when writing to a file is not accessible
     */
    private void writeStringToFile(Path file, String s) throws IOException {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file.toFile(), "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new IOException("Error reading the file: " + file.toFile().getName());
        }

        writer.write(s);
        writer.flush();
        writer.close();
    }

    /**
     * Method reads and parses the voting results from a given file path
     *
     * @param file Path object representing the location to the file beefing read
     * @return Map of String, Integer values -- id of a band and its number of votes assigned
     *
     * @throws IOException
     */
    private Map<String, Integer> readVotingResultsFromFile(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);

        Map<String, Integer> results = new HashMap<>();
        for (String line : lines) {

            String[] linesElem = line.split("\\t");

            results.put(linesElem[0], Integer.parseInt(linesElem[1]));
        }

        return results;
    }

    /**
     * Used for writing the results to a file path
     *
     * @param file path to the file to be written
     * @throws IOException thrown if the file is not acessable
     */
    private void writeVotingResultsToFile(Path file) throws IOException {
        StringBuilder votingFile = new StringBuilder();

        for (RecordModel band : bands) {
            votingFile.append(band.getId() + "\t" + band.getVotes() + "\n");
        }

        writeStringToFile(file, votingFile.toString());
    }

    /**
     * Used for reading the result file location defined as a constant in this class. If a record file is not
     * found an error is thrown as it is required
     *
     * @param context ServletContext used for file manipulation
     * @throws IOException thrown when the file being read is not accessible or not well formatted
     */
    private void readDefinitionFile(ServletContext context) throws IOException, IllegalAccessException {
        String fileName = context.getRealPath(DEFINITION_PATH);
        definition = Paths.get(fileName);
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new IOException("Definition file does not exist! expected path: " + e.getMessage());
        }


        for (String line : lines) {
            String[] lineSplit = line.split("\\t");

            if (lineSplit.length != 3) {
                throw new IllegalAccessException("File format of the definition file located at:" +
                        DEFINITION_PATH + " is not formatted correctly");
            }

            bands.add(new RecordModel(lineSplit[0], lineSplit[1], lineSplit[2]));
        }
    }


    /**
     * Getter method for obtaining the bands list
     *
     * @return List of all the loaded bands
     */
    public List<RecordModel> getBands() {
        return bands;
    }

    /**
     * Used for a single incrementation of the vote data for a record with the given id. By every call of this
     * method the file on the filesystem is updated to the new state.
     *
     * @param id String of the id object used for incrementing the record
     * @throws IOException thrown if the file to the voting results is not acessable
     */
    public void incrementVoteForId(String id) throws IOException {

        boolean changed = false;

        for (RecordModel record : bands) {
            if (record.getId().equals(id)) {
                record.setVotes(record.getVotes() + 1);
                changed = true;
                break;
            }
        }

        if (changed == false)
            throw new RuntimeException("voting ID value of:" + id + " is out of range, " +
                    "please provide a valid value index from 1 to " +
                    (bands.size() == 0 ? 1 : bands.size()));

        writeVotingResultsToFile(result);

    }
}
