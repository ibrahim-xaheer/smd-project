package activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.usman.phaedra.feely_beta.R;

public class BlueToothMain extends Activity {

	public BluetoothAdapter mBluetoothAdapter;
	private UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
	private String mac_address = "0E:10:0F:08:0B:38";
	BluetoothSocket tmp = null;
	BluetoothSocket mmSocket = null;
	Handler mHandler;
	
	ConnectedThread c;
	int a = 1;
	boolean isConnected = false;
	boolean b1,b2,b3,b4,b5,b6;
	
	public Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int REQUEST_ENABLE_BT = 1;
		setContentView(R.layout.activity_fblogin);

		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		b1=b2=b3=b4=b5=b6=true;	
		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth

			Toast T = new Toast(context);
			T.setText("Bluetooth not supported!");
		}
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

		}
		mHandler = new Handler(Looper.getMainLooper()) {
			 @Override
		        public void handleMessage(Message inputMessage) {
		            // Gets the image task from the incoming Message object.
				 switch (inputMessage.what) {
				case 1:
					byte[] readBuf = (byte[]) inputMessage.obj;
					 String readMessage = new String(readBuf, 0, inputMessage.arg1);
					//t.setText(readMessage);
					break;

				default:
					break;
				}
		        }
		 };

		Button connect = (Button) findViewById(R.id.button7);
		final Button btn1=(Button) findViewById(R.id.button1);
		final Button btn2=(Button) findViewById(R.id.button2);
		final Button btn3=(Button) findViewById(R.id.button3);
		final Button btn4=(Button) findViewById(R.id.button4);
		final Button btn5=(Button) findViewById(R.id.button5);
		final Button btn6=(Button) findViewById(R.id.button6);
		final Button dhussButton=(Button) findViewById(R.id.button8);
		connect.setBackgroundColor(Color.YELLOW);
		btn1.setBackgroundColor(Color.CYAN);
		btn2.setBackgroundColor(Color.CYAN);
		btn3.setBackgroundColor(Color.CYAN);
		btn4.setBackgroundColor(Color.WHITE);
		btn5.setBackgroundColor(Color.WHITE);
		btn6.setBackgroundColor(Color.WHITE);
		dhussButton.setBackgroundColor(Color.DKGRAY);
		connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(context, "Select a bluetooth device..",
						Toast.LENGTH_SHORT).show();
				Intent i = new Intent(context, SetBluetooth.class);
				startActivityForResult(i, 1);
			}
		});
	
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isConnected == true)
				{
					if(b1==true){
						c.write("0".getBytes());
						btn1.setBackgroundColor(Color.GREEN);
					}else{
						c.write("1".getBytes());						
						btn1.setBackgroundColor(Color.RED);
					}
					b1=!b1;
				}
				else
				{
					Toast.makeText(context, "No device connected..",Toast.LENGTH_SHORT).show();
				}
			}
		});
	
		dhussButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//Toast.makeText(context, "thaaaaa.....",Toast.LENGTH_SHORT).show();
		
			String s = null;
			s.charAt(2);
			
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isConnected == true)
				{
					if(b2==true){
						c.write("2".getBytes());
						btn2.setBackgroundColor(Color.GREEN);
					}else{
						c.write("3".getBytes());						
						btn2.setBackgroundColor(Color.RED);
					}
					b2=!b2;
				}
				else
				{
					Toast.makeText(context, "No device connected..",Toast.LENGTH_SHORT).show();
				}
			}
		});
	
		btn3.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isConnected == true)
				{
				if(b3==true){
						c.write("4".getBytes());
						btn3.setBackgroundColor(Color.GREEN);
					}else{
						c.write("5".getBytes());						
						btn3.setBackgroundColor(Color.RED);
					}
					b3=!b3;
				}
				else
				{
					Toast.makeText(context, "No device connected..",Toast.LENGTH_SHORT).show();
				}
			}
		});
	
		btn4.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isConnected == true)
				{
				if(b4==true){
						c.write("6".getBytes());
						btn4.setBackgroundColor(Color.GREEN);
					}else{
						c.write("7".getBytes());						
						btn4.setBackgroundColor(Color.RED);
					}
					b4=!b4;
				}
				else
				{
					Toast.makeText(context, "No device connected..",Toast.LENGTH_SHORT).show();
				}
			}
		});
	
		btn5.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isConnected == true)
				{
				if(b5==true){
						c.write("8".getBytes());
						btn5.setBackgroundColor(Color.GREEN);
					}else{
						c.write("9".getBytes());						
						btn5.setBackgroundColor(Color.RED);
					}
					b5=!b5;
				}
				else
				{
					Toast.makeText(context, "No device connected..",Toast.LENGTH_SHORT).show();
				}
			}
		});
	
		btn6.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isConnected == true)	
				{
				if(b6==true){
						c.write("10".getBytes());
						btn6.setBackgroundColor(Color.GREEN);
					}else{
						c.write("11".getBytes());			
						btn6.setBackgroundColor(Color.RED);						
					}
					b6=!b6;
				}
				else
				{
					Toast.makeText(context, "No device connected..",Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null && requestCode == 1) {

//			myfun(data.getStringExtra("mac"));
			BluetoothDevice device = mBluetoothAdapter
					.getRemoteDevice(data.getStringExtra("mac"));
			try {
				BluetoothSocket tmp = device
						.createInsecureRfcommSocketToServiceRecord(uuid);
				Method m = device.getClass().getMethod("createRfcommSocket",
						new Class[] { int.class });
				tmp = (BluetoothSocket) m.invoke(device, 1);
				mmSocket = tmp;
				mmSocket.connect();
				c = new ConnectedThread(mmSocket);
				c.start();
				isConnected = true;
				Toast.makeText(BlueToothMain.this, "connected..", Toast.LENGTH_SHORT)
						.show();
			} catch (IOException e) {
				Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
				.show();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
				.show();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
				.show();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
				.show();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
				.show();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	}

	private void myfun(String mac) {
		// TODO Auto-generated method stub

		//mBluetoothAdapter.cancelDiscovery();
		BluetoothDevice device = mBluetoothAdapter
				.getRemoteDevice(mac);
		try {
			BluetoothSocket tmp = device
					.createInsecureRfcommSocketToServiceRecord(uuid);
			Method m = device.getClass().getMethod("createRfcommSocket",
					new Class[] { int.class });
			tmp = (BluetoothSocket) m.invoke(device, 1);
			mmSocket = tmp;
			mmSocket.connect();
			c = new ConnectedThread(mmSocket);
			c.start();
			Toast.makeText(BlueToothMain.this, "connected..", Toast.LENGTH_SHORT)
					.show();
		} catch (IOException e) {
			Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
			.show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
			.show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
			.show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
			.show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Toast.makeText(BlueToothMain.this, "connection failed...", Toast.LENGTH_SHORT)
			.show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fblogin, menu);
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

	private class ConnectedThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;

		public ConnectedThread(BluetoothSocket socket) {
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;

			// Get the input and output streams, using temp objects because
			// member streams are final
			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
			} catch (IOException e) {
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}

		public void run() {
			byte[] buffer = new byte[1024]; // buffer store for the stream
			int bytes; // bytes returned from read()
			final int MESSAGE_READ = 9999;
			// Keep listening to the InputStream until an exception occurs
			while (true) {
				try {
					// Read from the InputStream
					bytes = mmInStream.read(buffer);
					// Send the obtained bytes to the UI activity
					mHandler.obtainMessage(1, bytes, -1, buffer).sendToTarget();
				} catch (IOException e) {
					break;
				}
			}
		}

		/* Call this from the main activity to send data to the remote device */
		public void write(byte[] bytes) {
			try {
				mmOutStream.write(bytes);
			} catch (IOException e) {
			}
		}

		/* Call this from the main activity to shutdown the connection */
		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
			}
		}
	}

}
