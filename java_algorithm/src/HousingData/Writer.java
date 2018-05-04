package HousingData;

import java.io.*;

/**
 * Writes the results to a CSV file
 *
 * EXAMPLE:
 *   -------------------------------------------------
 *  | results                ====>      FILE_PATH.csv |
 *  |  ---------------------------------------------  |
 *  | ["3", "0", "1", ... ]  ====>      3, 0, 1, ...  |
 *   -------------------------------------------------
 */
class Writer {
    private String FILE_PATH;

    Writer(String p) {
        FILE_PATH = p;
    }

    String writeTextFile(long[] results) {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for(long result: results) {
                writer.append(result + "\n");
            }
            writer.close();
            return "The results were successfully saved to a CSV file";
        }
        catch(Exception e) {
            System.out.println( "Unable to open file '" + FILE_PATH + "'" );
            System.out.println( "ERROR: " + e );
            return "There was some trouble writing the results file";
        }
    }
}
