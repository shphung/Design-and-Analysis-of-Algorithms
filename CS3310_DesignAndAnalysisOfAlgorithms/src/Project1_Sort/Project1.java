/*
 * Name: Steven Phung
 * Program: #3
 * Due: December 4th, 2018
 * Course: cs-3310-03-s19
 * 
 * Description:
 * 	A program that implements 5 different sorts, insertion sort, merge sort, and 3 variants of quick sort.
 *  Regular quicksort, quicksort / insertion sort combo, and randomized quicksort.
 */
package Project1_Sort;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class Project1 {
	
	public static void main(String[] args) {
		//Allows JVM time to initialize
		initializer();
		//Run any sort until quit
		while(true)
			runSorts();
	}
	
	public static void runSorts() {
		float startTime, endTime;
		Scanner keyboard = new Scanner(System.in);
		System.out.print("1. Insertion Sort\n"
				+ "2. Merge Sort\n"
				+ "3. Quick Sort 1\n"
				+ "4. Quick Sort 2\n"
				+ "5. Quick Sort 3\n"
				+ "6. Quit\n"
				+ "Enter Choice: ");
		int option = keyboard.nextInt();
		//Sort method performed based on option chosen
		switch(option) {
			case 1:
				System.out.println("Unsorted Array Times for Insertion Sort");
				break;
			case 2:
				System.out.println("Unsorted Array Times for Merge Sort");
				break;
			case 3:
				System.out.println("Unsorted Array Times for Quick Sort 1");
				break;
			case 4:
				System.out.println("Unsorted Array Times for Quick Sort 2");
				break;
			case 5:
				System.out.println("Unsorted Array Times for Quick Sort 3");
				break;
			case 6:
				System.out.println("Quit successful");
				keyboard.close();
				System.exit(0);
			default:
				break;
		}
		
		for(int i = 0; i < 16; i++) {
			int counter = 0;
			//Generates unsorted arrays using 2^n from 2^0 to 2^16
			int array[] = unsortedArrayGenerator(numOfElements(i));
			//Prints array is size is 32 or less
			if(array.length <= 32)
				printArray(array);
			//Records time before sort operations
			startTime = timer();
			//Array is copied over many times regardless of array size in order to see a clear elapsed time period
			while(counter < 1000000) {
				int[] array2 = new int[array.length];
				for(int j = 0; j < array.length; j++) {
					array2[j] = array[j];
				}
				counter++;
			}
			//Sort method performed based on option chosen
			switch(option) {
			case 1:
				insertionSort(array);
				break;
			case 2:
				mergeSort(array);
				break;
			case 3:
				quickSort1(array, 0, array.length - 1);
				break;
			case 4:
				quickSort2(array, 0, array.length - 1);
				break;
			case 5:
				quickSort3(array, 0, array.length - 1);
				break;
			default:
				break;
			}
			//Prints array is size is 32 or less
			if(array.length <= 32)
				printArray(array);
			//Records end time after sort operations are completed
			endTime = timer();
			System.out.println((i + 1) + ". " + (endTime - startTime) + " ms");
		}
		
		switch(option) {
		case 1:
			System.out.println("Sorted Array Times for Insertion Sort");
			break;
		case 2:
			System.out.println("Sorted Array Times for Merge Sort");
			break;
		case 3:
			System.out.println("Sorted Array Times for Quick Sort 1");
			break;
		case 4:
			System.out.println("Sorted Array Times for Quick Sort 2");
			break;
		case 5:
			System.out.println("Sorted Array Times for Quick Sort 3");
			break;
		default:
			break;
		}
		
		for(int i = 0; i < 16; i++) {
			int counter = 0;
			//Generates sorted arrays using 2^n from 2^0 to 2^16
			int array[] = sortedArrayGenerator(numOfElements(i));
			//Prints array is size is 32 or less
			if(array.length <= 32)
				printArray(array);
			//Records time before sort operations
			startTime = timer();
			//Array is copied over many times regardless of array size in order to see a clear elapsed time period
			while(counter < 1000000) {
				int[] array2 = new int[array.length];
				for(int j = 0; j < array.length; j++) {
					array2[j] = array[j];
				}
				counter++;
			}
			//Sort method performed based on option chosen
			switch(option) {
				case 1:
					insertionSort(array);
					break;
				case 2:
					mergeSort(array);
					break;
				case 3:
					//Overflow caught
					try {
						quickSort1(array, 0, array.length - 1);
					} catch(StackOverflowError e) {
						System.out.println((i + 1) + " has stack overflow error.");
					}
					break;
				case 4:
					try {
						quickSort2(array, 0, array.length - 1);
					} catch(StackOverflowError e) {
						System.out.println((i + 1) + " has stack overflow error.");
					}
					break;
				case 5:
					quickSort3(array, 0, array.length - 1);
					break;
				default:
					break;
			}
			//Prints array if array size is 32 or less
			if(array.length <= 32)
				printArray(array);
			//Records end time after sort operations are completed
			endTime = timer();
			//Prints elapsed time
			System.out.println((i + 1) + ". " + (endTime - startTime) + " ms");
		}
		System.out.println();
	}
	
	//Number of elements is based on 2^n
	public static int numOfElements(int exponent) {
		int numberOfElements = (int)Math.pow(2, exponent);
		return numberOfElements;
	}
	
	//Generates a random number
	public static int rng(int num) {
		int random = 0;
		Random rng = new Random();
		random = rng.nextInt(num) + 1;
		return random;
	}
	
	//Generates an unsorted array based on how many elements is required
	public static int[] unsortedArrayGenerator(int numOfElements) {
		int[] array = new int[numOfElements];
		boolean loop = false;
		int random = 0;
		for(int i = 0; i < array.length; i++) {
			//Ensures that every randomly generated number is not repeated
			do {
				loop = false;
				random = rng(numOfElements);
				for(int j = 0; j < array.length; j++) {
					if(random == array[j]) {
						loop = true;
						break;
					}
				}
			} while(loop);
			array[i] = random;
		}
		return array;
	}
	
	//Generates a sorted array based on how many elements is required
	public static int[] sortedArrayGenerator(int numOfElements) {
		int[] array = new int[numOfElements];
		for(int i = 0; i < array.length; i++) {
			array[i] = (i + 1);
		}
		return array;
	}

	//Kills time to initialize JVM
	public static void initializer() {
		for(int killTime = 0; killTime < 10; killTime++) {
			System.out.print("");
		}
	}
	
	//Calculates time
	public static float timer() {
		float time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
		return time;
	}
	
	public static void printArray(int A[]) {
		System.out.print("[ ");
		for(int i = 0; i < A.length; i++) {
			System.out.print(A[i] + " ");
		}
		System.out.print("]");
		System.out.println();
	}
	
	public static void insertionSort(int A[]) {
		for(int i = 1; i < A.length; i++) {
			int target = A[i];
			int j = i - 1;
			//Move elements that are greater than the target to one position ahead of their current position
			while (j >= 0 && target < A[j]) {
				A[j + 1] = A[j];
				j--;
			}
			A[j + 1] = target;
		}
	}
	
	public static void mergeSort(int A[]) {
		int n = A.length;
		if(n > 1) {
			//Middle points
			int uLen = (n / 2), vLen = n - uLen;
			//Create 2 new arrays called uLen and vLen
			int[] U = new int[uLen];
			int[] V = new int[vLen];
			//Copy A[0] to A[uLen - 1] into U[0] to U[uLen] 
			for(int i = 0; i < uLen; i++)
				U[i] = A[i];
			//Copy A[uLen] to A[n] into V[0] to V[vLen]
			for(int i = 0; i < vLen; i++)
				V[i] = A[uLen + i];
			mergeSort(U);
			mergeSort(V);
			merge(uLen, vLen, U, V, A);
		}
	}
	
	//Merges values in correct order
	public static void merge(int uLen, int vLen, int U[], int V[], int A[]) {
		//Indexes of first, second, and merged subarray
		int i = 0, j = 0, k = 0;
		while(i < uLen && j < vLen) {
			//If element in U is less than element in V, add U[i] to A, else add V before U
			if(U[i] <= V[j]) {
				A[k] = U[i];
				i++;
			} else {
				A[k] = V[j];
				j++;
			}
			k++;
		}
		//Copy remaining elements of U if any are left
		while(i < uLen) {
			A[k] = U[i];
			i++;
			k++;
		}
		//Copy remaining elements of V if any are left
		while(j < vLen) {
			A[k] = V[j];
			j++;
			k++;
		}
	}
	
	//Standard recursive quick sort
	public static void quickSort1(int A[], int low, int high) {
		if(high > low) {
			int pivotPoint = partition(A, low, high);
			quickSort1(A, low, pivotPoint - 1);
			quickSort1(A, pivotPoint + 1, high);
		}
	}
	
	public static void quickSort2(int A[], int low, int high) {
		//If the actual array or subarray is less than size 16, insertion sort is called
		if(A.length < 16) {
			insertionSort(A);
		} else {
			if(high > low) {
				int pivotPoint = partition(A, low, high);
				quickSort2(A, low, pivotPoint - 1);
				quickSort2(A, pivotPoint + 1, high);
			}
		}
	}
	
	public static void quickSort3(int A[], int low, int high) {
		if(high > low) {
			//Calls randomPartition to randomize pivot point if sub array's length is greater than 16
			int pivotPoint = randomPartition(A, low, high);
			quickSort3(A, low, pivotPoint - 1);
			quickSort3(A, pivotPoint + 1, high);
		}
	}

	//Uses a pivot point to place elements in sorted order
	public static int partition(int A[], int low, int high) {
		//Pivot point is always last element (unless randomized beforehand)
		int pivot = A[high];
		int i = (low - 1);
		for(int j = low; j < high; j++) {
			//If current element is smaller than or equal to pivot then swap A[i] A[j]
			if(A[j] <= pivot) {
				i++;
				int temp = A[i];
				A[i] = A[j];
				A[j] = temp;
			}
		}
		//Swap A[i + 1] and pivot point
		int temp = A[i + 1];
		A[i + 1] = A[high];
		A[high] = temp;
		return i + 1;
	}
	
	public static int randomPartition(int A[], int low, int high) {
		//Swaps pivot point if sub array's length is greater than 16
		if(A.length > 16) {
			Random rand = new Random();
			int num = low + rand.nextInt(high - low);
			int temp = A[num];
			A[num] = A[high];
			A[high] = temp;
		}
		//Calls partition with new randomized pivot point
		return partition(A, low, high);
	}
}