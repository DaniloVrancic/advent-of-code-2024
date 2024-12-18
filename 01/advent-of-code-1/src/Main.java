import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        final String filePath = "resource/input.txt";
        File myFile = new File(filePath);
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader(myFile));
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
            return;
        } //At this point the file reader is initialized

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();



        String line = "";
        do{ //read and parse the file into the two arrays, sort at insertion

            line = reader.readLine();
            if(line != null && !line.isEmpty())
            {
                String[] tokens = line.split("   ");
                insertSorted(list1, Integer.parseInt(tokens[0].trim()));
                insertSorted(list2, Integer.parseInt(tokens[1].trim()));
            }

        }while(line != null);

        distanceCalculator(list1, list2);
        similarityCalculator(list1,list2);
    }

    private static void similarityCalculator(List<Integer> list1, List<Integer> list2) {
        List<Integer> similarities = new ArrayList<>();

        list1.forEach(leftElement -> {
            int occurences = (int)list2.stream().filter(rightElement -> leftElement.equals(rightElement)).count();

            similarities.add(leftElement.intValue() * occurences);
        });

        System.out.println("The similarity value is: " + similarities.stream().mapToInt(element -> element.intValue()).sum());
    }


    private static void distanceCalculator(List<Integer> list1, List<Integer> list2) {
        List<Integer> distances = new ArrayList<>(); //All the differences for corresponding list members
        if(list1.size() == list2.size())
        {
            for(int i = list1.size() - 1; i >= 0; --i){
                distances.add(Math.abs(list1.get(i) - list2.get(i)));
            }

            int sumOfDistances = distances.stream().mapToInt(Integer::intValue).sum();

            System.out.println("SUM OF DISTANCES IS: " + sumOfDistances);
        }
        else{
            System.out.println("Lists are not of equal size!");
        }
    }

    public static void insertSorted(List<Integer> list, int value) {
        int pos = Collections.binarySearch(list, value);
        if (pos < 0) {
            pos = -pos - 1; // Binary search returns the insertion point
        }
        list.add(pos, value);
    }
}