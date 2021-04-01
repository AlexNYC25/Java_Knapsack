import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.Random;

class Knapsack {

    // how many possible combinations exist currently    
    static int count = 0;
    // vector object of combination array
    static Vector<int[]> listOfCombinations =  new Vector<int[]>();

    // variables for the actual searching of the best combination
    static int[] bestCombination = null;
    static int bestCombinationValue = 0;


    /*
        combination function
        purpose: Creates every possible combination of elements, adding it to the class vector object listOfCombinations
        parameters:
            arr[] : input array of elements
            data[] : the temporary array holding the current combination
            start: from where to begin getting the next element
            end: position from where to stop searching for the next element
            index: current position
            size: the amount of elements for the combination
    */
    static void combinationUtil(int arr[], int data[], int start, int end, int index, int size)
    {
        // Current combination is ready to be printed, print it
        if (index == size)
        {
            // creats a temp array of the possible array 
            int[] temp = new int[size];

            // captures the current combination
            for (int j=0; j<size; j++){
                temp[j] = data[j];
            }
            
            count++;
            listOfCombinations.add(temp);
            return;
        }
  
        /*
            calls the next recursive call for the next combination
        */
        for (int i = start; i<=end && (end - i + 1 >= size-index); i++)
        {
            data[index] = arr[i];
            // calls the function recursivley with new parameters
            combinationUtil(arr, data, i+1, end, index+1, size);
        }
    }
  
    // The main utilty function that is called to properly use the combinationUtil function
    static void createCombinations(int arr[], int end, int size){
        // an initial empty array to hold the combination of elements of length size 
        int data[] = new int[size];
  
        // actual combination work done by other function, initial function call to start the recursive search
        combinationUtil(arr, data, 0, end-1, 0, size);
    }

    static void bestCombination(int[][] valueMatrix, int maxWeight, FileWriter output){
        // resets global values
        bestCombination = null;
        bestCombinationValue = 0;

        for(int x = 0; x < listOfCombinations.size(); x++){
            int[] currentCombination = listOfCombinations.get(x);
            int totalWeight = 0;
            int totalValue = 0;
            for(int y = 0; y < currentCombination.length; y++){
                totalWeight += valueMatrix[currentCombination[y]][0];
                totalValue += valueMatrix[currentCombination[y]][1];

            }
            if(totalWeight < maxWeight){

                if(bestCombination == null){
                    bestCombination = currentCombination;
                    bestCombinationValue = totalValue;
                }
                
                if(totalValue > bestCombinationValue){
                    bestCombination = currentCombination;
                    bestCombinationValue = totalValue;
                }

            }
        }

        try{
            int comboWeight = 0;
            output.write("Running " + count + " combinations for the knapsack problem \n");
            output.write("The best combinaton of items are: ");
            for(int x = 0; x < bestCombination.length; x++){
                comboWeight += valueMatrix[bestCombination[x]][0];
                output.write(bestCombination[x] + " ");
                if(x == bestCombination.length - 1){
                    output.write("\n");
                }
            }
            output.write("The total value of the items: " + bestCombinationValue + "\n");
            output.write("The total weight of the items: " + comboWeight + "\n");
            
        } catch (IOException e){
            System.out.println("An error has occured when writing to output file");
        }

        count = 0;

    }


    public static void main(String[] args) throws Exception{
        // initial data file
        File file = new File(args[0]); 
        Scanner sc = new Scanner(file); 
        String[] initialStrings = null;

        // 2d arrays to store values 
        int[][] easyMatrix= new int[10][2];
        int[][] mediumMatrix = new int[15][2];
        int[][] largeMatrix = new int[20][2];
        // companion array used for functions
        int[] smallNums = {0,1,2,3,4,5,6,7,8,9};
        int[] mediumNums = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        int[] largeNums = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};

        // split initial file into array of strings
        for(int x = 0; x < 1; x++){
            initialStrings = sc.nextLine().split(" ");
        }
        // split strings into the proper array
        for(int x = 0; x < 10; x++){
            String[] tempWeight = initialStrings[0].split(",");
            String[] tempValue = initialStrings[1].split(",");
            easyMatrix[x][0] = Integer.parseInt(tempWeight[x]);
            easyMatrix[x][1] = Integer.parseInt(tempValue[x]);
        }

        for(int x = 0; x < 15; x++){
            String[] tempWeight = initialStrings[2].split(",");
            String[] tempValue = initialStrings[3].split(",");
            mediumMatrix[x][0] = Integer.parseInt(tempWeight[x]);
            mediumMatrix[x][1] = Integer.parseInt(tempValue[x]);
        }

        for(int x = 0; x < 20; x++){
            String[] tempWeight = initialStrings[4].split(",");
            String[] tempValue = initialStrings[5].split(",");
            largeMatrix[x][0] = Integer.parseInt(tempWeight[x]);
            largeMatrix[x][1] = Integer.parseInt(tempValue[x]);
        }
        
        // actual execution and writing output
        try{

            File outPutFile = null;
            FileWriter myFileWriter = null;

            if(args.length == 1){
                // create file 
                outPutFile = new File("output.txt");
                if(outPutFile.createNewFile()){
                    System.out.println("Output file created");

                } else {
                    System.out.println("Output file has been overwritten");
                }

                // create fileWriter object to write output
                myFileWriter = new FileWriter("output.txt");
            }
            else{
                outPutFile = new File(args[1]);
                if(outPutFile.createNewFile()){
                    System.out.println("Output file created");

                } else {
                    System.out.println("Output file has been overwritten");
                }
                myFileWriter = new FileWriter(args[1]);
                
            }
            

            // max weight knapsack can handle
            // TODO: create a random weight for every iteration
            Random randomWeightGen = new Random();
            // this is the abs max weight random gen can create for problem
            int upperbound = 35;
            int weight = randomWeightGen.nextInt(upperbound);

            myFileWriter.write("Starting Brute Force Knapsack Problem \n\n");

            // iterates through loop to find all possible combinations of length x
            for(int x = 1; x <= 10; x++){
                createCombinations(smallNums, 10, x);
            }
            myFileWriter.write("Finding the best combination of " + easyMatrix.length + " items in terms of value, while the items together do not exceed weight of " + weight + "\n");
            bestCombination(easyMatrix, weight, myFileWriter);
            myFileWriter.write("\n");
            // reset combination list
            listOfCombinations =  new Vector<int[]>();


            for(int x = 1; x <= 15; x++){
                createCombinations(mediumNums, 15, x);
            }
            myFileWriter.write("Finding the best combination of " + mediumMatrix.length + " items in terms of value, while the items together do not exceed " + weight + "\n");
            bestCombination(mediumMatrix, weight, myFileWriter);
            myFileWriter.write("\n");
            listOfCombinations =  new Vector<int[]>();

            for(int x = 1; x <= 20; x++){
                createCombinations(largeNums, 20, x);
            }
            myFileWriter.write("Finding the best combination of " + largeMatrix.length + " items in terms of value, while the items together do not exceed " + weight + "\n");
            bestCombination(largeMatrix, weight, myFileWriter);
            myFileWriter.write("\n");
            listOfCombinations =  new Vector<int[]>();


            myFileWriter.close();

        }
        catch (IOException e) {
            System.out.println("An error occured when creating output file");
            e.printStackTrace();
        }

        System.out.println("Program has finished running");
    }

}