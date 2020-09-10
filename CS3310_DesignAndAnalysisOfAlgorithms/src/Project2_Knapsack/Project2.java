/*
 * 		Name: Steven Phung
 * 		Program: #2
 * 		Due: May 9th, 2018
 * 		Course: cs-3310-01-s19
 * 
 * 		Description:
 * 			A program that tries to solve the 0-1 Knapsack problem using brute force, backtrack, and branch and bound.
 */
package Project2_Knapsack;

import java.util.Scanner;

public class Project2 {

	static int maxProfit = 0, W = 0, nodesVisited = 0;
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Enter number of elements: ");
		int numberOfElements = keyboard.nextInt();
		
		int[] weight = new int[numberOfElements];
		int[] profit = new int[numberOfElements];
		
		System.out.print("Enter weight for " + numberOfElements + " elements: ");
		for(int i =  0; i < numberOfElements; i++) {
			weight[i] = keyboard.nextInt();
		}
		
		System.out.print("Enter profit for " + numberOfElements + " elements: ");
		for(int i =  0; i < numberOfElements; i++) {
			profit[i] = keyboard.nextInt();
		}
		
		System.out.print("Enter knapsack weight: ");
		int maxWeight = keyboard.nextInt();
		
		int[] possibleSol = new int[numberOfElements];
		int[] finalSol = new int[numberOfElements];
		
		int option = 0;
		while(option != 1 || option != 2 || option != 3 || option != 4) {
			System.out.println("\nPlease select method");
			System.out.println(" 1. Bruteforce");
			System.out.println(" 2. Backtrack");
			System.out.println(" 3. Branch and Bound");
			System.out.println(" 4. Exit");
			option = keyboard.nextInt();
			switch(option) {
				case 1:
					brute(weight, profit, maxWeight, 0, possibleSol, finalSol);
					break;
				case 2:
					backtrack(weight, profit, maxWeight, 0, possibleSol, finalSol);
					break;
				case 3:
					//branchAndBound(weight, profit, maxWeight, 0, possibleSol, finalSol);
					System.exit(0);
					break;
				default:
					System.out.println("Incorrect input.");
					break;
			}
		}
		
		keyboard.close();
	}
	
	public static void brute(int[] weight, int[] profit, int maxWeight, int index, int[] possibleSol, int[] finalSol) {
		knapsack(weight, profit, maxWeight, 0, possibleSol, finalSol);
		System.out.println("\nItem:weight selected: ");
		for(int i = 0; i < finalSol.length; i++) {
			if(finalSol[i] == 1) {
				System.out.print(i + 1 + ":" + weight[i] + " ");
			}
		}
		System.out.println("\nItem:weight not selected: ");
		for(int i = 0; i < finalSol.length; i++) {
			if(finalSol[i] == 0) {
				System.out.print(i + 1 + ":" + weight[i] + " ");
			}
		}
		System.out.println("\nMax profit: " + maxProfit);
		System.out.println("Nodes visited: " + nodesVisited);
		nodesVisited = 0;
	}
	
	public static void backtrack(int[] weight, int[] profit, int maxWeight, int index, int[] possibleSol, int[] finalSol) {
		knapsackbacktrack(weight, profit, maxWeight, 0, possibleSol, finalSol);
		System.out.println("\nItem:weight selected: ");
		for(int i = 0; i < finalSol.length; i++) {
			if(finalSol[i] == 1) {
				System.out.print(i + 1 + ":" + weight[i] + " ");
			}
		}
		System.out.println("\nItem:weight not selected: ");
		for(int i = 0; i < finalSol.length; i++) {
			if(finalSol[i] == 0) {
				System.out.print(i + 1 + ":" + weight[i] + " ");
			}
		}
		System.out.println("\nMax profit: " + maxProfit);
		System.out.println("Nodes visited: " + nodesVisited);
		nodesVisited = 0;
	}
	
	public static void knapsack(int[] weight, int[] profit, int maxWeight, int index, int[] possibleSol, int[] finalSol) {
		possibleSol[index] = -1;
		int n = weight.length;
		
		while(possibleSol[index] < 1) {
			nodesVisited++;
			possibleSol[index] = possibleSol[index] + 1;
			if(calcWeight(index, possibleSol, weight) <= maxWeight && index == (n - 1)) {
				updateMaxProfit(weight, profit, maxWeight, index, possibleSol, finalSol);
			} else if(index < n - 1) {
				knapsack(weight, profit, maxWeight, index + 1, possibleSol, finalSol);
			}
		}
	}
	
	public static void knapsackbacktrack(int[] weight, int[] profit, int maxWeight, int index, int[] possibleSol, int[] finalSol) {
		possibleSol[index] = -1;
		int n = weight.length;
		
		while(possibleSol[index] < 1) {
			possibleSol[index] = possibleSol[index] + 1;
			if(calcWeight(index, possibleSol, weight) <= maxWeight && index == (n - 1)) {
				updateMaxProfit(weight, profit, maxWeight, index, possibleSol, finalSol);
				nodesVisited++;
			} else if(index < n - 1) {
				knapsackbacktrack(weight, profit, maxWeight, index + 1, possibleSol, finalSol);
			}
		}
	}
	
	private static int calcWeight(int index, int[] possibleSol, int[] weight) {
		int w = 0;
		for(int i = 0; i < possibleSol.length; i++) {
			w += possibleSol[i] * weight[i];
		}
		return w;
	}
	
	private static void updateMaxProfit(int[] weight, int[] profit, int maxWeight, int index, int[] possibleSol, int[] finalSol) {
		int totalProfit = 0, totalWeight = 0;
		for(int i = 0; i < weight.length; i++) {
			if(possibleSol[i] == 1) {
				totalProfit += profit[i];
				totalWeight += weight[i];
			}
		}
		if(totalProfit > maxProfit) {
			for(int i = 0; i < weight.length; i++) {
				finalSol[i] = possibleSol[i];
			}
			maxProfit = totalProfit;
			W = totalWeight;
		}
	}
}
