import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

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
  
        // actual combination work done by other function, initial to start the recursive search
        combinationUtil(arr, data, 0, end-1, 0, size);
    }

    static void maxCombination(int[][] valueMatrix, int maxWeight, FileWriter output){
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
            if(totalWeight < maxWeight ){

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

        for(int x = 0; x < 1; x++){
            initialStrings = sc.nextLine().split(" ");
        }

        for(int x = 0; x < 10; x++){
            easyMatrix[x][0] = Integer.parseInt(initialStrings[0].split("")[x]);
            easyMatrix[x][1] = Integer.parseInt(initialStrings[1].split("")[x]);
        }

        for(int x = 0; x < 15; x++){
            mediumMatrix[x][0] = Integer.parseInt(initialStrings[2].split("")[x]);
            mediumMatrix[x][1] = Integer.parseInt(initialStrings[3].split("")[x]);
        }

        for(int x = 0; x < 20; x++){
            largeMatrix[x][0] = Integer.parseInt(initialStrings[4].split("")[x]);
            largeMatrix[x][1] = Integer.parseInt(initialStrings[5].split("")[x]);
        }
        

        try{
            // create file 
            File outPutFile = new File("output.txt");
            if(outPutFile.createNewFile()){
                System.out.println("File Created");

            } else {
                System.out.println("File already exists");
            }

            // create fileWriter object to write output
            FileWriter myFileWriter = new FileWriter("output.txt");

            int weight = 30;

            // iterates through loop to find all possible combinations of length x
            for(int x = 1; x <= 10; x++){
                createCombinations(smallNums, 10, x);
            }
            myFileWriter.write("Finding the best combination of " + easyMatrix.length + " items in terms of value, while the items together do not exceed " + weight + "\n");
            maxCombination(easyMatrix, 30, myFileWriter);
            myFileWriter.write("\n");
            // reset combination list
            listOfCombinations =  new Vector<int[]>();


            for(int x = 1; x <= 15; x++){
                createCombinations(mediumNums, 15, x);
            }
            myFileWriter.write("Finding the best combination of " + mediumMatrix.length + " items in terms of value, while the items together do not exceed " + weight + "\n");
            maxCombination(mediumMatrix, 30, myFileWriter);
            myFileWriter.write("\n");
            listOfCombinations =  new Vector<int[]>();

            for(int x = 1; x <= 20; x++){
                createCombinations(largeNums, 20, x);
            }
            myFileWriter.write("Finding the best combination of " + largeMatrix.length + " items in terms of value, while the items together do not exceed " + weight + "\n");
            maxCombination(largeMatrix, 30, myFileWriter);
            myFileWriter.write("\n");
            listOfCombinations =  new Vector<int[]>();



            //maxCombination(mediumMatrix, 30, myFileWriter);
            //maxCombination(hardMatrix, 30, myFileWriter);


            


            myFileWriter.close();

            System.out.println("Finished writing output file");
        }
        catch (IOException e) {
            System.out.println("An error occured when creating output file");
            e.printStackTrace();
        }

    }
}