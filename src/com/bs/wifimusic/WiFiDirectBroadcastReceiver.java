package com.bs.wifimusic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private Activity mActivity;
    List<WifiP2pDevice> list;
    TextView t2;
    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
            Activity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        list = new ArrayList<WifiP2pDevice>();
        
    
        
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
        
        	int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            
        	if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context, "enbaled", Toast.LENGTH_LONG).show();
                
            } 
        	else {
        		Toast.makeText(context, "p2p not enbaled", Toast.LENGTH_LONG).show();
            }
        	
        	
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
        	if (mManager != null) {
               //mManager.requestPeers(mChannel, myPeerListListener);
               mManager.requestPeers(mChannel, new PeerListListener() {
				
				@Override
				public void onPeersAvailable(WifiP2pDeviceList peers) {
					// TODO Auto-generated method stub
					list.addAll(peers.getDeviceList());
					for(int i=0;i<list.size();i++)
					{
						Log.i("device",list.get(i).deviceName);
						final WifiP2pDevice device=list.get(i);
						WifiP2pConfig config = new WifiP2pConfig();
						config.deviceAddress = device.deviceAddress;
						mManager.connect(mChannel, config, new ActionListener() {

						    @Override
						    public void onSuccess() {
						        Log.i("device", "connected to "+device.deviceName+" with address "+device.deviceAddress);
						    }

						    @Override
						    public void onFailure(int reason) {
						        //failure logic
						    }
						});
					}
				}
			});
               
            }
            
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }
}
