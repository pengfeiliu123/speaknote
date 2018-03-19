package com.lpf.utilcode.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.lpf.utilcode.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liupengfei on 2018/2/24 11:14.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    private String[] tips = {"WIFI已连接,移动数据已连接", "WIFI已连接,移动数据已断开", "WIFI已断开,移动数据已连接", "WIFI已断开,移动数据已断开"};

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            //获得ConnectivityManager对象
            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            boolean wifiNetworkConnect = wifiNetworkInfo.isConnected();
            boolean dataNetworkConnect = dataNetworkInfo.isConnected();
            if (wifiNetworkConnect && dataNetworkConnect) {
                EventBus.getDefault().post(new NetworkMessage(tips[0]));
            } else if (wifiNetworkConnect && !dataNetworkConnect) {
                EventBus.getDefault().post(new NetworkMessage(tips[1]));
            } else if (!wifiNetworkConnect && dataNetworkConnect) {
                EventBus.getDefault().post(new NetworkMessage(tips[2]));
            } else {
                EventBus.getDefault().post(new NetworkMessage(tips[3]));
            }
        } else {
            System.out.println("API level 大于23");
            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < networks.length; i++) {
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
            }
            if (TextUtils.isEmpty(sb.toString())) {
                ToastUtil.showShort(context, "no connect");
            } else {
                EventBus.getDefault().post(new NetworkMessage(sb.toString()));
            }
        }
    }
}
