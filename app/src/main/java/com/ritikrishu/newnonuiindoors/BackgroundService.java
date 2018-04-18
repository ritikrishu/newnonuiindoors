package com.ritikrishu.newnonuiindoors;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.customlbs.library.Indoors;
import com.customlbs.library.IndoorsException;
import com.customlbs.library.IndoorsFactory;
import com.customlbs.library.IndoorsLocationListener;
import com.customlbs.library.LocalizationParameters;
import com.customlbs.library.callbacks.IndoorsServiceCallback;
import com.customlbs.library.callbacks.LoadingBuildingStatus;
import com.customlbs.library.model.Building;
import com.customlbs.library.model.Zone;
import com.customlbs.library.util.IndoorsCoordinateUtil;
import com.customlbs.shared.Coordinate;

import java.util.List;

public class BackgroundService extends NonStopIntentService {


    public BackgroundService() {
        super(BackgroundService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        start();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            boolean ss = intent.getBooleanExtra("ss", false);
            if (!ss) {
                stop();
            }
        }
    }

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();
    }

    public void start(){
        Message msg = Message.obtain(null, LocalService.START, 0, 0);
        try {
            MainApplication.getInstance().getService().send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        Message msg = Message.obtain(null, LocalService.STOP, 0, 0);
        try {
            MainApplication.getInstance().getService().send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
