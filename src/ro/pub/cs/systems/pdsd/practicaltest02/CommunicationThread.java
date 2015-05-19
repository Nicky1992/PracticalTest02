package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

public class CommunicationThread extends Thread{
	private Socket       socket;
	
	public CommunicationThread(Socket socket) {
		this.socket       = socket;
	}
	
	@Override
	public void run() {
		if (socket != null) {
			try {
				BufferedReader bufferedReader = Utilities.getReader(socket);
				PrintWriter    printWriter    = Utilities.getWriter(socket);
				if (bufferedReader != null && printWriter != null) {
					String line = "";
					while((line = bufferedReader.readLine()) != null) {
						String[] operators = line.split(",");
						if (operators[0].equals("add")) {
							Integer op1 = Integer.parseInt(operators[1]);
							Integer op2 = Integer.parseInt(operators[2]);
							Integer res = op1 + op2;
							printWriter.println("" + res);
							printWriter.flush();
						} else if (operators[0].equals("mul")) {
							Integer op1 = Integer.parseInt(operators[1]);
							Integer op2 = Integer.parseInt(operators[2]);
							Integer res = op1 * op2;
							Thread.sleep(3000);
							printWriter.println("" + res);
							printWriter.flush();
						}
					}
				} else {
					Log.e(Constants.TAG, "[Communication Thread] BufferedReader / PrintWriter are null!");
				}
				socket.close();
			} catch (IOException ioException) {
				Log.e(Constants.TAG, ioException.getMessage());
				if (Constants.DEBUG) {
					ioException.printStackTrace();
				}
			} catch (InterruptedException interruptedException) {
				Log.e(Constants.TAG, interruptedException.getMessage());
				if (Constants.DEBUG) {
					interruptedException.printStackTrace();
				}
			}
		} else {
			Log.e(Constants.TAG, "[Communication THREAD] Could not create socket!");
		}
	}
}
