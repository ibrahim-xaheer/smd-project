package activity;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.usman.phaedra.feely_beta.R;

public class SetBluetooth extends Activity {

	ArrayList<String> foundDevicess;
	ArrayList<String> names;
	private ArrayAdapter<String> mArrayAdapter;
	boolean b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_bluetooth);
		final SwipeRefreshLayout swipelist = (SwipeRefreshLayout) findViewById(R.id.swipelist);

		final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		foundDevicess = new ArrayList<String>();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
			// Loop through paired devices
			ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1);

			for (BluetoothDevice device : pairedDevices) {
				// Add the name and address to an array adapter to show in a
				// ListView
				mArrayAdapter
						.add(device.getName() + "\n" + device.getAddress());
				foundDevicess.add(device.getAddress());
			}
			ListView listView = (ListView) findViewById(R.id.listOfBT);
			listView.setAdapter(mArrayAdapter);
			swipelist.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					// TODO Auto-generated method stub
					Toast.makeText(SetBluetooth.this, "You refresh List",
							Toast.LENGTH_SHORT).show();
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							
							swipelist.setRefreshing(false);
							Toast.makeText(SetBluetooth.this, "refresh Closed",
									Toast.LENGTH_SHORT).show();
						}
					}, 2);
				}
			});
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent output = new Intent();
					output.putExtra("mac", foundDevicess.get(position));
					setResult(RESULT_OK, output);
					SetBluetooth.this.finish();

					// TODO Auto-generated method stub

				}
			});

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_bluetooth, menu);
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
}
