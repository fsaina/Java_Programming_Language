package hr.fer.zemris.web.glasanje;

import hr.fer.zemris.java.tecaj_13.model.PollOptionsEntry;
import java.util.Comparator;

/**
 * Controller class for the RecordModel object. This class contains all the logic necessary for reading,
 * updating and manipulating the definition and result files that contain data in the format of the
 * RecordModel object.
 *
 * @author Filip Saina
 */
public class ComparatorUtil {

    /**
     * Public Comparator object used for id compaction of records (lower is better)
     */
    public static final Comparator<PollOptionsEntry> byIdComparator = new Comparator<PollOptionsEntry>() {
        @Override
        public int compare(PollOptionsEntry o1, PollOptionsEntry o2) {
            long n1 = o1.getId();
            long n2 = o2.getId();

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
    public static final Comparator<PollOptionsEntry> byVotesComparator = new Comparator<PollOptionsEntry>() {
        @Override
        public int compare(PollOptionsEntry o1, PollOptionsEntry o2) {
            if (o1.getVotesCount() > o2.getVotesCount())
                return -1;
            else if (o1.getVotesCount() == o2.getVotesCount())
                return 0;
            else
                return 1;
        }
    };
}
