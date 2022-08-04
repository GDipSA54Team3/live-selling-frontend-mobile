package iss.workshop.livestreamapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class FetchStreamLog extends Service {

    private Thread backgroundThread;
    private boolean isCollectingData = true;

    public FetchStreamLog() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        int duration = intent.getIntExtra("duration", 5);

        if (action.equals("send_messages")){
            backgroundThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (Thread.interrupted())
                        return;

                    while (isCollectingData) {
                        //code to run in the background
                        System.out.println("Hello this is the string");

                        //fetch current stream details as intent
                        try {
                            Thread.sleep(1000 * duration);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            backgroundThread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (backgroundThread != null) backgroundThread.interrupt();
        isCollectingData = false;
        super.onDestroy();
    }

}