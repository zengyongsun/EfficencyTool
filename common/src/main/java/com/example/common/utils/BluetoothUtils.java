package com.example.common.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Jacky.Cai
 * @version v1.0
 * @Package com.daydao.caterers.fastfood.utils
 * @Description:
 * @date 2017/8/17 下午5:51
 */
public class BluetoothUtils {

    public static List<BluetoothDevice> getBluetoothDevices() {
        List<BluetoothDevice> deviceList = new ArrayList<>();
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        if (!Null.isNull(adapter)) {
            if (adapter.isEnabled()) {//蓝牙为开启状态
                Class<BluetoothAdapter> bluetoothAdapterClass = BluetoothAdapter.class;//得到BluetoothAdapter的Class对象
                try {//得到连接状态的方法
                    Method method = bluetoothAdapterClass.getDeclaredMethod("getConnectionState", (Class[]) null);
                    //打开权限
                    method.setAccessible(true);
                    int state = (int) method.invoke(adapter, (Object[]) null);

//                    if (state == BluetoothAdapter.STATE_CONNECTED) {
                        Log.i("BLUETOOTH", "BluetoothAdapter.STATE_CONNECTED");
                        Set<BluetoothDevice> devices = adapter.getBondedDevices();
                        Log.i("BLUETOOTH", "devices:" + devices.size());

                        for (BluetoothDevice device : devices) {
//                            Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
//                            method.setAccessible(true);
//                            boolean isConnected = (boolean) isConnectedMethod.invoke(device, (Object[]) null);
//                            if (isConnected) {
                                Log.i("BLUETOOTH", "connected:" + device.getName());
                                deviceList.add(device);
//                            }
                        }
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //设备没有蓝牙模块
        }
        return deviceList;
    }
}
