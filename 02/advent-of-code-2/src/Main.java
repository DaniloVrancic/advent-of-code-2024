import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int REQUESTED_MIN_DISTANCE = 1;
    private static int REQUESTED_MAX_DISTANCE = 3;

    private final static String fileInputPath= "resource/input.txt";
    public static void main(String[] args) {
        int counter = 0;

        try( BufferedReader reader = new BufferedReader(new FileReader(fileInputPath))){
            String line = reader.readLine();
            while(line != null)
            {
                line = line.trim();
                List<Integer> reportTemp = parseReport(line);
                if(isReportSafe(reportTemp))
                {
                    counter++;
                }
                //after finished, read the next line
                line = reader.readLine();
            }

            System.out.println("NUMBER OF SAFE REPORTS: " + counter);
        }
        catch (IOException ex){
            System.err.println("Couldn't find the file!");
            ex.printStackTrace();
        }
        catch(Exception ex){
            System.err.println("An unexpected error occured.");
            ex.printStackTrace();
        }


        System.out.println("Hello world!");
    }

    public static boolean isReportSafe(List<Integer> reportList){

        if(reportList.size() < 2)
        {
            return false;
        }

        if(reportList.get(0).equals(reportList.get(1))){ //if the first two elements are the same
            return false;
        }

        boolean isIncreasing = reportList.get(0) - reportList.get(1) < 0;

        return (isStrictlyMonotone(reportList, isIncreasing) && areValidRanges(reportList, REQUESTED_MIN_DISTANCE, REQUESTED_MAX_DISTANCE));

    }

    public static boolean isStrictlyMonotone(List<Integer> reportList, boolean isIncreasing){
        boolean failMonotonicity = false;
        for(int i = 0; i < reportList.size() - 1; ++i){
            if((reportList.get(i) < reportList.get(i + 1)) != isIncreasing)
            {
                failMonotonicity = true;
                break;
            }
        }

        return !failMonotonicity; //If monotonicity failed, then the data is not monotone strictly increasing or decreasing
    }

    public static boolean areValidRanges(List<Integer> data, int min, int max){

        boolean valid = true;

        for(int i = 0; i < data.size() - 1; i ++){
            int distance = Math.abs(data.get(i) - data.get(i + 1));

            if(distance >= min && distance <= max){
                continue;
            }
            else{
                valid = false;
                break;
            }
        }

        return valid;
    }

    public static List<Integer> parseReport(String reportString){
        String cleanedReportString = reportString.trim();
        String[] reportTokens = cleanedReportString.split(" ");

        List<Integer> parsedReport = new ArrayList<>();
        for(String token : reportTokens){
            parsedReport.add(Integer.parseInt(token));
        }

        return parsedReport;
    }
}