package com.bs.wifimusic;

import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;

public class PListener implements PeerListListener{

		static int i=0;
	@Override
	public void onPeersAvailable(WifiP2pDeviceList arg0) {

		Log.i("wifimusic", "backstroke "+(++i));
		
		
	}

}
