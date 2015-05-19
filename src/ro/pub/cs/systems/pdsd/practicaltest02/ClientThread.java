package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;
import android.widget.TextView;

public class ClientThread extends Thread {
	private Socket   socket;
	private int      port;
	private String op1, op2, operation;
	private TextView myView;
	
	public ClientThread(int port, String op1, String op2, String operation, TextView myView) {
		this.port = port;
		this.op1 = op1;
		this.op2 = op2;
		this.operation = operation;
		this.myView = myView;
	}
	@Override
	public void run() {
		try {
			socket = new Socket("localhost", port);
			if (socket == null) {
				Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
			}
			
			BufferedReader bufferedReader = Utilities.getReader(socket);
			PrintWriter    printWriter    = Utilities.getWriter(socket);
			
			if (bufferedReader != null && printWriter != null) {
				String text = operation + "," + op1 + "," + op2;
				printWriter.println(text);
				printWriter.flush();
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					final String res = line;
					if (myView != null) {
						myView.post( new Runnable() {
							@Override
							public void run() {
								myView.setText(res);
								
							}
						});
					} else {
						Log.e(Constants.TAG, "[CLIENT THREAD] Could not modify view!");
					}
				}
			}
			socket.close();
		} catch (UnknownHostException unknownHostException) {
			Log.e(Constants.TAG, unknownHostException.getMessage());
			if (Constants.DEBUG) {
				unknownHostException.printStackTrace();
			}
			
		} catch (IOException ioException) {
			Log.e(Constants.TAG, ioException.getMessage());
			if (Constants.DEBUG) {
				ioException.printStackTrace();
			}
		}
	}
}

