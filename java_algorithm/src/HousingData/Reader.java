package HousingData;

import java.io.*;

/**
 * Reads a text file and returns its contents as a matrix of strings.
 *
 * EXAMPLE:
 *  --------------------------------------------
 * | Text file     ====>    Return Matrix       |
 * |  ----------------------------------------  |
 * | 5 3           ====>    [ ["5", "3"],       |
 * | 1 2 3 4 5     ====>      [172, 2, 9736"] ] |
 *  --------------------------------------------
 */
class Reader {
    private String FILE_PATH;
    private String DELIMITER;

    Reader(String n, String d) {
        FILE_PATH = n;
        DELIMITER = d;
    }

    String[][] readTextFile() {
        String[][] text = new String[2][];
        String line;
        try {
            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                text[i] = line.split(DELIMITER);
                i += 1;
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException e) {
            System.out.println( "Unable to open file '" + FILE_PATH + "'" );
            System.out.println( "ERROR: " + e );
        }
        catch(IOException e) {
            System.out.println( "Error reading file '" + FILE_PATH + "'" );
            System.out.println( "ERROR: " + e );
        }
        return text;
    }
}
