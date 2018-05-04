package HousingData;


/**
 * Given an array of house prices (size: N) and a window size k (where k < N):
 *
 * For each window of size k find the number of increasing subranges and subtract the number of decreasing subranges.
 *
 * Ranges that are not increasing or decreasing count as zero.
 *
 * This project has been optimized for time.
 */

public class Main {

    /**
     * **************** ********* ***** ********* **************** *
     * ****************         Change me!!       **************** *
     */
    private static final String DATA_PATH = "/Users/pbezuhov/git/misc/java_algorithm/testData/random_200kN_20kK.txt";
    private static final String DELIMITER = " ";

    private static final String RESULTS_PATH = "/Users/pbezuhov/Desktop/results.csv";
    /**
     * **************** ********* ***** ********* **************** *
     */

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        long[] dataSetOutput = analyzeHomeData(DATA_PATH, DELIMITER).output;

        writeResults(RESULTS_PATH, dataSetOutput);
        displayTime(startTime);
    }

    static HomeDataSetAnalyzer analyzeHomeData(String filepath, String delimiter) {
        String[][] housingData = (  new Reader( filepath, delimiter )  ).readTextFile();
        return new HomeDataSetAnalyzer( housingData );
    }

    private static void writeResults(String path, long[] text) {
        Writer writer = new Writer( path );
        String status = writer.writeTextFile( text );
        System.out.println("\n" + status);
    }

    private static void displayTime(long startTime) {
        long endTime = System.nanoTime();
        String secondsElapsed = String.valueOf(  (endTime - startTime) / 1000000000  );
        String remainderMilliseconds = String.valueOf(  (endTime - startTime) );
        int length = remainderMilliseconds.length();
        remainderMilliseconds = remainderMilliseconds.substring(length - 9, length - 6);
        System.out.println("Process finished in: " + secondsElapsed + "." + remainderMilliseconds + "s");
    }
}