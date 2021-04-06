Java Knapsack with genetic algorithm

Phase 1: Brute Force Method

    compile instructions:
        javac knapsack.java

    execution instructions:
        java knapsack <input.txt> <output.txt>

    input file structure, <> - denotes list of numbers seperated by commmas, x values represent weight values, y values represent "value" values
        <x1,x2,...,x10> <y1,y2,...,y10> <x1,x2,...,x15> <y1,y2,...,y15> <x1,x2,...,x20> <y1,y2,...,y20>        

    Summary:
        To solve the knapsack problem with a brute force method, my idea was to iterate through every possible combination of items that could be build from the given data
        then while iterating through each possible combination, the program checks to see if the total weight is less than or equal to the max weight allowed by the "knapsack"
        then comparing its total value with the previously found best value.
