package ro.pub.cs.systems.pdsd.practicaltest02;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02MainActivity extends Activity {
	public EditText op1 = null;
	public EditText op2 = null;
	public Button plus = null;
	public Button mul = null;
	public TextView res_plus = null;
	public TextView res_mul = null;
	public TextView portServer = null;
	public ServerThread serverThread = null;
	
	private ButtonListener plusListener = null;
	private ButtonListener mulListener = null;
	private class ButtonListener implements OnClickListener {
		private String operation;
		private TextView resultView;
		
		public ButtonListener(String operation, TextView resultView) {
			this.operation = operation;
			this.resultView = resultView;
		}
		
		@Override
		public void onClick(View v) {
			String oper1 = op1.getText().toString();
			if (!(oper1 != null && !oper1.isEmpty())) {
				Log.e(Constants.TAG, "Operand 1 is empty");
				Toast.makeText(getBaseContext(), "Operand 1 is missing. Fill it in" , Toast.LENGTH_LONG).show();
			}
			String oper2 = op2.getText().toString();
			if (!(oper2 != null && !oper2.isEmpty())) {
				Log.e(Constants.TAG, "Operand 2 is empty");
				Toast.makeText(getBaseContext(), "Operand 2 is missing. Fill it in" , Toast.LENGTH_LONG).show();
			}
			
			String port = portServer.getText().toString();
			if (!(port != null && !port.isEmpty())) {
				Log.e(Constants.TAG, "Port is empty");
				Toast.makeText(getBaseContext(), "Post is missing. Fill it in" , Toast.LENGTH_LONG).show();
			}
			ClientThread client = new ClientThread(Integer.parseInt(port), oper1, oper2, operation, resultView);
			client.start();
			
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test02_main);
		
		op1 = (EditText)findViewById(R.id.operator1_edit_text);
		op2 = (EditText)findViewById(R.id.operator2_edit_text);
		
		plus = (Button)findViewById(R.id.button1);
		mul = (Button)findViewById(R.id.button2);
		
		res_plus = (TextView)findViewById(R.id.plus_edit_text);
		res_mul = (TextView)findViewById(R.id.mul_edit_text);
		
		portServer = (TextView)findViewById(R.id.port);
		
		plusListener = new ButtonListener("add", res_plus);
		mulListener = new ButtonListener("mul", res_mul);
		
		
		plus.setOnClickListener(plusListener);
		mul.setOnClickListener(mulListener);
		
		String port = portServer.getText().toString();
		if (port != null && !port.isEmpty() && !"".equals(port)) {
			ServerThread server = new ServerThread(Integer.parseInt(port));
			server.start();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test02_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		if (serverThread != null) {
			serverThread.stopThread();
		}
		super.onDestroy();
	}
}
