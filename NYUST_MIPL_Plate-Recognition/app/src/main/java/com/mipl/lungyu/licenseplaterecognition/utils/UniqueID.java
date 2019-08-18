package com.mipl.lungyu.licenseplaterecognition.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

public class UniqueID implements IDFactory {

	public String getID(Context context) {
		try {

			String _serialID = SerialID();
			if (_serialID != null)
				return MakeID(SerialID, _serialID);

			String _bluetoothAddress = BluetoothAddress();
			if (_bluetoothAddress != null)
				return MakeID(BluetoothAddress, _bluetoothAddress);

			String _macAddress = MACAddress(context);
			if (_macAddress != null)
				return MakeID(MacAddress, _macAddress);

			String _uuid = UUID();
			if (_uuid != null)
				return MakeID(UUID, _uuid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "no have uuid";
	}

	@Override
	public String MakeID(int mid, String uuid) {
		// TODO Auto-generated method stub
		StringBuilder UniqueId = new StringBuilder();
		UniqueId.append(mid);
		UniqueId.append(Separated());
		UniqueId.append(uuid);
		return UniqueId.toString();
	}

	@Override
	public String SerialID() {
		// TODO Auto-generated method stub
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			return Build.SERIAL;
		}
		return null;
	}

	// @Override
	// public String DeviceID() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// @Override
	// public String SubscriberID() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public String MACAddress(Context context) {
		// TODO Auto-generated method stub
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifi = wm.getConnectionInfo();
		return wifi.getMacAddress();
	}

	@Override
	public String BluetoothAddress() {
		// TODO Auto-generated method stub
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter != null)
			return btAdapter.getAddress();
		return null;
	}

	// @Override
	// public String AndroidID() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public String UUID() {
		// TODO Auto-generated method stub
		return java.util.UUID.randomUUID().toString();
	}

	@Override
	public String Separated() {
		// TODO Auto-generated method stub
		return "!";
	}

}
