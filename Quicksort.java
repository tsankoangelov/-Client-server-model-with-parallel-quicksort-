package project;

class Quicksort extends Thread {
	private int arr[];
	private int left;
	private int right;
	public static int numberOfThreads;
	public static int count = 0;

	public Quicksort(int[] arr, int left, int right, int n) {
		this.arr = arr;
		this.left = left;
		this.right = right;
		numberOfThreads = n;
		count++;
	}

	public void run() {
		parallelQuicksort(arr, left, right);	
	}

	public static void quicksort(int[] arr, int left, int right) {
		if (right > left) {
			int i = partition(arr, left, right);
			quicksort(arr, left, i - 1);
			quicksort(arr, i + 1, right);
		}
	}

	public static void parallelQuicksort(int[] arr, int left, int right) {
		if (right > left) {
			int i = partition(arr, left, right);
			if (count + 1 < numberOfThreads) {
				
				Quicksort lquicksort = new Quicksort(arr, left, i - 1, numberOfThreads--);
				lquicksort.start();
				System.out.println("Thread " + count + " working.");
				
				Quicksort hquicksort = new Quicksort(arr, i + 1, right, numberOfThreads--);
				hquicksort.start();
				System.out.println("Thread " + count + " working.");
				
				try {
					lquicksort.join();
					hquicksort.join();
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			} else {
				quicksort(arr, left, i - 1);
				quicksort(arr, i + 1, right);
			}
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static int partition(int[] array, int left, int right) {
		int pivot = array[right];
		int i = left - 1;
		for (int j = left; j <= right - 1; j++) {
			if (array[j] < pivot) {
				i++;
				swap(array, i, j);
			}
		}
		swap(array, i + 1, right);
		return (i + 1);
	}
}