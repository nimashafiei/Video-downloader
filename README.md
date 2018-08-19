# Video-downloader
A very simple bare-minimum WebSocket client for Android.

## Quick Start
Download the project and import to Andrid studio
Build project

## Usage
Here's the entire API:
```
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
   ```
  
## Related webservice project
[Video check node](https://github.com/afshines/VideoCheckNode) <br /> [Video laravel](https://github.com/afshines/video-laravel)
