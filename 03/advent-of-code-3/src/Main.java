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
    public static void main(String[] args) {

        long finalResult = 0;

        final String myPattern = "mul\\((\\d{1,3}),(\\d{1,3})\\)";

        Pattern pattern = Pattern.compile(myPattern);
        Matcher matcher = null;
        List<Long> allMatchProducts = new ArrayList<>();

        try( BufferedReader reader = new BufferedReader(new FileReader(fileInputPath))){
            String line = reader.readLine();

                line = line.trim();
                while(line != null){


                    matcher = pattern.matcher(line);


                    while (matcher.find()) {
                        allMatchProducts.add(Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2)));
                    }
                    line = reader.readLine();
                }

                System.out.println(allMatchProducts.stream().mapToLong(Long::longValue).sum());

        }
        catch (IOException ex){
            System.err.println("Couldn't find the file!");
            ex.printStackTrace();
        }
        catch(Exception ex){
            System.err.println("An unexpected error occured.");
            ex.printStackTrace();
        }
    }
}