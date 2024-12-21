import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    private final static String fileInputPath= "resource/input.txt";
    private final static String TARGET_WORD = "XMAS";
    public static void main(String[] args) {

        List<String> allLines = new ArrayList<>();
        try( BufferedReader reader = new BufferedReader(new FileReader(fileInputPath))){
            String line = reader.readLine();
            while(line != null){
                allLines.add(line);
                line = reader.readLine();
            }



            Character[][] matrix = new Character[allLines.size()][];

// Populate the matrix
            for (int i = 0; i < allLines.size(); i++) {
                String lineT = allLines.get(i);
                if (lineT == null || lineT.isEmpty()) {
                    continue; // Skip empty lines if any
                }
                // Create a row with the same length as the line
                matrix[i] = new Character[lineT.length()];

                // Fill the row character by character
                for (int j = 0; j < lineT.length(); j++) {
                    matrix[i][j] = lineT.charAt(j);
                }
            }

            for (Character[] row : matrix) {
                if (row != null) {
                    for (Character c : row) {
                        System.out.print(c);
                    }
                    System.out.println();
                }
            }


            int count = 0;
            count = scanMatrixForWord(matrix,TARGET_WORD);
            System.out.println("ALL MATCHES: " + count);
        }
        catch(IOException ex){
            System.err.println("Couldn't find the file!");
            ex.printStackTrace();
        }
        catch(Exception ex){
            System.err.println("An unexpected error occured.");
            ex.printStackTrace();
        }

    }
    public static int scanMatrixForWord(Character[][] matrix, String word){
        if(word == null || word.isEmpty())
        {
            return 0;
        }

        Character startCharacter = word.charAt(0);
        int count = 0;

        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if(matrix[i][j].equals(startCharacter))
                {
                    count += matchUp(i,j,matrix,TARGET_WORD) ? 1 : 0;
                    count += matchRight(i,j,matrix,TARGET_WORD) ? 1 : 0;
                    count += matchDown(i,j,matrix,TARGET_WORD) ? 1 : 0;
                    count += matchLeft(i,j,matrix,TARGET_WORD) ? 1 : 0;

                    count += matchUpRight(i,j,matrix,TARGET_WORD) ? 1 : 0;
                    count += matchDownRight(i,j,matrix,TARGET_WORD) ? 1 : 0;
                    count += matchDownLeft(i,j,matrix,TARGET_WORD) ? 1 : 0;
                    count += matchUpLeft(i,j,matrix,TARGET_WORD) ? 1 : 0;

                }
                else{
                    continue;
                }
            }
        }
        return count;
    }

    private static boolean matchRight(int x, int y, Character[][] matrix, String word){


        if(y + word.length() > matrix[x].length)
        {
            return false;
        }

        for(int i = 0; i < word.length(); i++)
        {

                if(!matrix[x][y + i].equals(word.charAt(i)))
                {
                    return false;
                }
        }
        return true;
    }
    private static boolean matchLeft(int x, int y, Character[][] matrix, String word){


        if(y - (word.length() - 1) < 0)
        {
            return false;
        }

        for(int i = 0; i < word.length(); i++)
        {

            if(!matrix[x][y - i].equals(word.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
    private static boolean matchUp(int x, int y, Character[][] matrix, String word){


        if(x - (word.length() - 1) < 0)
        {
            return false;
        }

        for(int i = 0; i < word.length(); i++)
        {

            if(!matrix[x - i][y].equals(word.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean matchDown(int x, int y, Character[][] matrix, String word){


        if(x + word.length() > matrix.length)
        {
            return false;
        }

        for(int i = 0; i < word.length(); i++)
        {

            if(!matrix[x + i][y].equals(word.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean matchUpRight(int x, int y, Character[][] matrix, String word){


        if(x - (word.length() - 1) < 0 || y + word.length() > matrix[x].length)
        {
            return false;
        }

        for(int i = 0; i < word.length(); i++)
        {

            if(!matrix[x - i][y + i].equals(word.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean matchDownRight(int x, int y, Character[][] matrix, String word) {
        // Check if the word fits within the bounds for the Down-Right diagonal
        if (x + word.length() > matrix.length || y + word.length() > matrix[x].length) {
            return false;
        }

        // Check each character in the Down-Right diagonal
        for (int i = 0; i < word.length(); i++) {
            if (!matrix[x + i][y + i].equals(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean matchDownLeft(int x, int y, Character[][] matrix, String word) {
        // Check if the word fits within the bounds for the Down-Left diagonal
        if (x + word.length() > matrix.length || y - (word.length() - 1) < 0) {
            return false;
        }

        // Check each character in the Down-Left diagonal
        for (int i = 0; i < word.length(); i++) {
            if (!matrix[x + i][y - i].equals(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean matchUpLeft(int x, int y, Character[][] matrix, String word) {
        // Check if the word fits within the bounds for the Up-Left diagonal
        if (x - (word.length() - 1) < 0 || y - (word.length() - 1) < 0) {
            return false;
        }

        // Check each character in the Up-Left diagonal
        for (int i = 0; i < word.length(); i++) {
            if (!matrix[x - i][y - i].equals(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}