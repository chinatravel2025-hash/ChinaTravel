package com.example.http.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.example.base.base.App;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * 网络工具类
 *
 * 作者：li yi on 2018/9/12 14:33
 */
public class NetworkUtil {
    public static final int NETWORK_TYPE_NONE = -1;
    public static final int NETWORK_TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
    public static final int NETWORK_TYPE_WIFI = ConnectivityManager.TYPE_WIFI;
    private static boolean reverseProxyOn = false;
    private static final String ANDROID_HOTSPOT_IP_ADDRESS = "192.168.43.1";
    private static final String IOS_HOTSPOT_IP_ADDRESS = "172.20.10.1";

    private NetworkUtil() {}

    public static boolean isNetworkConnected() {
        if (reverseProxyOn) {
            return true;
        }
        ConnectivityManager connManager = (ConnectivityManager) App.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        try {
            activeNetworkInfo = connManager.getActiveNetworkInfo();
        } catch (Exception e) {
        }
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connManager.getActiveNetworkInfo();
        return activeNetworkInfo;
    }

    public static boolean isMobileNetworkConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =
                connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isWifiConnected() {
        return isWifiConnected(App.getContext());
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager == null) {
            return false;
        }
        NetworkInfo networkInfo = null;
        try {
            // maybe throw exception in android framework
            networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        } catch (Exception e) {
        }

        // can not use pingSupplicant (), on cm9 or some other roms it will
        // block whole wifi network!
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Convert a IPv4 address from an integer to an InetAddress.
     *
     * @param hostAddress is an Int corresponding to the IPv4 address in network byte order
     * @return the IP address as an {@code InetAddress}, returns null if
     *         unable to convert or if the int is an invalid address.
     */
    private static InetAddress intToInetAddress(int hostAddress) {
        InetAddress inetAddress = null;
        byte[] addressBytes = {(byte) (0xff & hostAddress),
                (byte) (0xff & (hostAddress >> 8)),
                (byte) (0xff & (hostAddress >> 16)),
                (byte) (0xff & (hostAddress >> 24))};

        try {
            inetAddress = InetAddress.getByAddress(addressBytes);
        } catch (UnknownHostException e) {
        } catch (Exception e) {
        }

        return inetAddress;
    }

    /**
     * Check wifi is hotSpot or not.
     *
     * @return whether wifi is hotSpot or not.
     */
    public static boolean checkWifiIsHotSpot(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            return false;
        }
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if (dhcpInfo == null) {
            return false;
        }
        InetAddress address = intToInetAddress(dhcpInfo.gateway);
        if (address == null) {
            return false;
        }
        String currentGateway = address.getHostAddress();
        return TextUtils.equals(currentGateway, ANDROID_HOTSPOT_IP_ADDRESS)
                || TextUtils.equals(currentGateway, IOS_HOTSPOT_IP_ADDRESS);
    }

    public static boolean isReverseProxyOn() {
        return reverseProxyOn;
    }

    public static void setUsbReverseProxyState(boolean proxyOn) {
        reverseProxyOn = proxyOn;
    }

    public static int getNetworkType() {
        ConnectivityManager connManager =
                (ConnectivityManager) App.getContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        try {
            networkInfo = connManager.getActiveNetworkInfo();
        } catch (NullPointerException e) {
            return NETWORK_TYPE_NONE;
        }
        return parseNetworkType(networkInfo);
    }

    public static int parseNetworkType(NetworkInfo networkInfo) {
        if (networkInfo == null || !networkInfo.isConnected()) {
            return NETWORK_TYPE_NONE;
        }
        if (networkInfo.getType() == NETWORK_TYPE_MOBILE) {
            return NETWORK_TYPE_MOBILE;
        } else {
            return NETWORK_TYPE_WIFI;
        }
    }
}

