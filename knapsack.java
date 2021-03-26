import java.io.File; 
import java.util.Scanner;

class Knapsack {
    public static void main(String[] args) throws Exception{
        // initial data file
        File file = new File(args[0]); 
        Scanner sc = new Scanner(file); 
        String[] initialStrings = null;

        // 2d arrays
        int[][] easyMatrix= new int[10][2];
        int[][] mediumMatrix = new int[15][2];
        int[][] hardMatrix = new int[20][2];

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
            hardMatrix[x][0] = Integer.parseInt(initialStrings[4].split("")[x]);
            hardMatrix[x][1] = Integer.parseInt(initialStrings[5].split("")[x]);
        }

        System.out.println();
        for(int x = 0; x < 20; x++){
            System.out.println(hardMatrix[x][0]);
        }

    }
}