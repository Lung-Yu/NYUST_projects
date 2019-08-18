package com.mipl.lungyu.licenseplaterecognition.utils;

import android.content.Context;

public interface IDFactory {
	int DeviceID = 1;
	int SubscriberID = 2;
	int SerialID = 3;
	int MacAddress = 4;
	int BluetoothAddress = 5;
	int AndroidID = 7;
	int UUID = 9;
	
	String SerialID();
//	String DeviceID();
//	String SubscriberID();
	String MACAddress(Context context);
	String BluetoothAddress();
//	String AndroidID();
	String UUID();
	
	String Separated();
	
	String MakeID(int mid, String uuid);
}
