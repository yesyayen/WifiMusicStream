package com.bs.wifimusic;

import java.util.ArrayList;
import java.util.List;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	WifiP2pManager mManager;
	Channel mChannel;
	BroadcastReceiver mReceiver;
	IntentFilter mIntentFilter;
	TextView t1,t2,t3;
	List<WifiP2pDevice> list;
	public void updateText(String temp)
	{
		t3=(TextView)findViewById(R.id.textView3);
		t3.setText("P2P "+temp);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		t1=(TextView)findViewById(R.id.textView2);
		
		
		mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
	    mChannel = mManager.initialize(this, getMainLooper(), null);
	    mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
	    mIntentFilter = new IntentFilter();
	    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
	    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
	    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
	    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		
	    list = new ArrayList<WifiP2pDevice>();
	    
	    Button b1=(Button)findViewById(R.id.button1);
	    b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
				        @Override
				        public void onSuccess() {
				            Toast.makeText(getApplicationContext(), "success from button", Toast.LENGTH_LONG).show();
				            
				            mManager.requestPeers(mChannel, new PeerListListener() {
								
								@Override
								public void onPeersAvailable(WifiP2pDeviceList peers) {
									// TODO Auto-generated method stub
									Log.i("wifimusic", "backstroke labs");
									list.addAll(peers.getDeviceList());
									t1.setText("SUNJI LAGE "+list.size());
									
								}
							});
				        }

				        @Override
				        public void onFailure(int reasonCode) {
				        	Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
				        }
				    });
			}
		});
	   
	    
		
	}

	/* register the broadcast receiver with the intent values to be matched */
	@Override
	protected void onResume() {
	    super.onResume();
	    registerReceiver(mReceiver, mIntentFilter);
	}
	/* unregister the broadcast receiver */
	@Override
	protected void onPause() {
	    super.onPause();
	    unregisterReceiver(mReceiver);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
