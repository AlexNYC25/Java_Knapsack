import java.io.File; 
import java.util.Scanner;

class Knapsack {
    public static void main(String[] args) throws Exception{
        // initial data file
        System.out.println(args[0]);
        
        File file = new File(args[0]); 
        Scanner sc = new Scanner(file); 
        String[] initialStrings = null;

        for(int x = 0; x < 1; x++){
            initialStrings = sc.nextLine().split(" ");
        }

        for(int x = 0; x < initialStrings.length; x++){
            System.out.println(initialStrings[x]);
        }

        System.out.println(initialStrings[0].split("")[0]);

    }
}