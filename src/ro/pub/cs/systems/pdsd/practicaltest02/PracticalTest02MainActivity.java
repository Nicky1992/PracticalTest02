package ro.pub.cs.systems.pdsd.practicaltest02;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest02MainActivity extends Activity {
	public EditText op1 = null;
	public EditText op2 = null;
	public Button plus = null;
	public Button mul = null;
	public TextView res_plus = null;
	public TextView res_mul = null;
	public TextView portServer = null;
	public ServerThread serverThread = null;
	
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
		
		String port = portServer.getText().toString();
		ServerThread server = new ServerThread(Integer.parseInt(port));
		server.run();
		
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
