package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionHandler implements Runnable {

	Scanner scan = new Scanner(System.in);
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private int id;

	public ConnectionHandler(Socket socket, BufferedReader in, PrintWriter out, int id) {
		this.socket = socket;
		this.in = in;
		this.out = out;
		this.id = id;
	}

	private void getNumberOfThreads() {
		String inputLine;
		try {
			out.println("Enter number of threads:");
			inputLine = in.readLine();
			Quicksort.numberOfThreads = Integer.parseInt(inputLine);
			Quicksort.count = 0;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		getNumberOfThreads();
		out.println("Client " + this.id + " connected.");
		while (!socket.isClosed()) {
			String inputLine;
			try {
				inputLine = in.readLine();
				if (inputLine != null) {
					String[] array = inputLine.split(" ");
					int arrayNum[] = new int[array.length];
					for (int i = 0; i < array.length; i++) {
						arrayNum[i] = Integer.parseInt(array[i]);
					}
					Quicksort.parallelQuicksort(arrayNum, 0, arrayNum.length - 1);
					for (int i = 0; i < arrayNum.length; i++) {
						out.print(arrayNum[i] + " ");
					}
					out.println();
				}
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}
}
