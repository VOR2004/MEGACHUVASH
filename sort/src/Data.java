import java.util.LinkedList;
import java.io.*;
import java.util.Random;
import java.util.*;

public class Data {
    public static void main(String[] args) {
        //Data.testMake("data.txt", 100);
        //Data.clearFile("resultsOfTheTests.txt");
        Data.writingResultsToTheFile("data.txt", "resultsOfTheTests.txt");
    }

    public static void testMake(String fileName, int numberOfSets) {
        Random random = new Random();

        int valueCoefficient = 100;
        int minValue = -5000000;
        int maxValue = 5000000;

        File file = new File(fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("File made");
            }

            FileWriter writer = new FileWriter(file);

            for (int i = 1; i < numberOfSets + 1; i++) {
                writer.write("[");
                for (int j = 0; j < (i * valueCoefficient) - 1; j++) {
                    writer.write(random
                            .nextInt(minValue, maxValue) + " ");
                }
                writer.write(random
                        .nextInt(minValue, maxValue) + "]\n");
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writingResultsToTheFile(String fileWithTests, String destinationFile) {
        File firstFile = new File(fileWithTests);
        File secondFile = new File(destinationFile);
        List<Integer> resultList;
        try {
            if (firstFile.createNewFile()) {
                System.out.println("File made");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileWithTests));
             FileWriter writer = new FileWriter(secondFile, true);) {

            String currentLine;
            int lineNumber = 1;

            while ((currentLine = reader.readLine()) != null) {
                currentLine = currentLine.replaceAll("[\\[\\]]", "");
                resultList = Arrays.stream(currentLine.split(" "))
                        .map(Integer::parseInt)
                        .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

                long before = System.nanoTime();
                PatienceSort.patienceSort(resultList);
                long after = System.nanoTime();
                double timeOfExecution = (after - before)/1e6;
                writer.write(timeOfExecution + " " + PatienceSort.getIterations() + " " + (lineNumber * 100) + "\n");

                lineNumber++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(new File(fileName));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
