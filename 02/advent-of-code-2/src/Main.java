import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private final static int REQUESTED_MIN_DISTANCE = 1;
    private final static int REQUESTED_MAX_DISTANCE = 3;

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
    }

    public static boolean isReportSafe(List<Integer> reportList){


        boolean isIncreasing = reportList.get(0) - reportList.get(1) < 0;

        if (isStrictlyMonotone(reportList, isIncreasing) && areValidRanges(reportList, REQUESTED_MIN_DISTANCE, REQUESTED_MAX_DISTANCE))
        {
            return true;
        }
        else{
            return checkSingleLevelDampenedValues(reportList);
        }

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

    public static boolean checkSingleLevelDampenedValues(List<Integer> levels){
        boolean isValid = false;
        for(int i = 0; i < levels.size(); i++){
            List<Integer> tempLevels = new ArrayList<>(levels);
            tempLevels.remove(i);
            boolean isIncreasing = tempLevels.get(0) - tempLevels.get(1) < 0;
            isValid = isStrictlyMonotone(tempLevels, isIncreasing) && areValidRanges(tempLevels, REQUESTED_MIN_DISTANCE, REQUESTED_MAX_DISTANCE);

            if(isValid){
                return true; //if a single case of the removed level list is valid, then return the check as valid
            }
        }
        return false;
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