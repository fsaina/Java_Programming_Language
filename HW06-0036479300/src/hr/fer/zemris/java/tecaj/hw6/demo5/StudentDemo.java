package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class showcase the reading student data and manipulating data with the java stream.
 */
public class StudentDemo {

    /**
     * Determines the path to the file to be read the student data
     */
    private static final String PATH_TO_READ = "./studenti.txt";

    /**
     * Entry point of the application
     *
     * @param args command line arguments - ignored
     */
    public static void main(String[] args) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(PATH_TO_READ), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error in reading students textual file!");
            System.exit(-1);
        }

        List<StudentRecord> records = convert(lines);

        /*
         * List of all used stream filters and operations
         */

        studentiKojiImajuViseOd25Bodova(records);

        brojStudenataSaOcjenom5(records);

        listaOdlikasa(records);

        listaOdlikasaSortirana(records);

        listaNepolozenihJmbagova(records);

        mapaPoOcjenama(records);

        mapaPoOcjenama2(records);

        mapaProlazaNeProlaza(records);
    }

    private static void mapaProlazaNeProlaza(List<StudentRecord> records) {
        Map<Boolean, List<StudentRecord>> prolazNeprolaz = records
                .stream()
                .collect(Collectors.partitioningBy(studentRecord -> {
                    if (studentRecord.getOcjena() >= 2) return true;
                    return false;
                }));

        System.out.println("\nMapa prolaza/neprolaza: " + prolazNeprolaz);
    }

    private static void mapaPoOcjenama2(List<StudentRecord> records) {
        Map<Integer, Integer> mapaPoOcjenama2 = records
                .stream()
                .collect(Collectors.toMap(s -> s.getOcjena(), s -> 1, (i1, i2) -> i1 + i2));

        System.out.println("\nMapa po ocjenama 2: " + mapaPoOcjenama2);
    }

    private static void mapaPoOcjenama(List<StudentRecord> records) {
        Map<Integer, List<StudentRecord>> mapaPoOcjenama = records
                .stream()
                .collect(Collectors.groupingBy(s -> s.getOcjena()));

        System.out.println("\nMapa po ocjenama: " + mapaPoOcjenama);
    }

    private static void listaNepolozenihJmbagova(List<StudentRecord> records) {
        Comparator<StudentRecord> byStudentJmbag = (s1, s2) -> {
            int compareRez = s1.getJmbag().compareTo(s2.getJmbag());
            return compareRez;
        };

        List<String> nepolozeniJMBAGovi = records
                .stream()
                .filter(studentRecord -> {
                    if (studentRecord.getOcjena() == 1) return true;
                    return false;
                })
                .sorted(byStudentJmbag)
                .map(s -> s.getJmbag())
                .collect(Collectors.toList());

        System.out.println("\nLista nepolozenih jmbag-ova: " + nepolozeniJMBAGovi);
    }

    private static void listaOdlikasaSortirana(List<StudentRecord> records) {
        Comparator<StudentRecord> byStudentGrade = (s1, s2) -> {
            return Double.compare(s2.getStudentPoints(), s1.getStudentPoints());
        };

        List<StudentRecord> odlikasiSortirano = records
                .stream()
                .filter(studentRecord -> {
                    if (studentRecord.getOcjena() == 5) return true;
                    return false;
                })
                .sorted(byStudentGrade)
                .collect(Collectors.toList());

        System.out.println("\nLista odlikasa sortirana: " + odlikasiSortirano);
    }

    private static void listaOdlikasa(List<StudentRecord> records) {
        List<StudentRecord> odlikasi = records
                .stream()
                .filter(studentRecord -> {
                    if (studentRecord.getOcjena() == 5) return true;
                    return false;
                })
                .collect(Collectors.toList());

        System.out.println("\nLista odlikasa: " + odlikasi);
    }

    private static void brojStudenataSaOcjenom5(List<StudentRecord> records) {
        long broj5 = records
                .stream()
                .filter(studentRecord -> {
                    if (studentRecord.getOcjena() == 5) return true;
                    return false;
                })
                .count();

        System.out.println("\nBroj studenata sa ocjenom 5: " + broj5);
    }

    private static void studentiKojiImajuViseOd25Bodova(List<StudentRecord> records) {
        long broj = records
                .stream()
                .filter(studentRecord -> {
                    double suma = studentRecord.getStudentPoints();

                    if (suma > 25.0) return true;
                    return false;
                })
                .count();

        System.out.println("Broj studenata koji imaju vise od 25 bodova: " + broj);
    }


    /*
     * Private method converts all the read user data from the file specified
     * and returns a list of StudentRecord objects
     */
    private static List<StudentRecord> convert(List<String> lines) {
        List<StudentRecord> list = new ArrayList<StudentRecord>();

        for (String line : lines) {
            Scanner lineScanner = new Scanner(line);

            if (lineScanner.hasNext() == false)
                break;

            String jmbag = lineScanner.next();
            String prezime = lineScanner.next();
            String ime = lineScanner.next();
            Double brojBodovaNaMeduispitu = Double.parseDouble(lineScanner.next());
            Double getBrojBodovaNaZavrsnomIspitu = Double.parseDouble(lineScanner.next());
            Double brojBodovaNaLaboratorijskimVjezbama = Double.parseDouble(lineScanner.next());
            Integer ocjena = Integer.parseInt(lineScanner.next());

            list.add(new StudentRecord(
                    jmbag,
                    prezime,
                    ime,
                    brojBodovaNaMeduispitu,
                    getBrojBodovaNaZavrsnomIspitu,
                    brojBodovaNaLaboratorijskimVjezbama,
                    ocjena));

           lineScanner.close();
        }

        return list;

    }

}
