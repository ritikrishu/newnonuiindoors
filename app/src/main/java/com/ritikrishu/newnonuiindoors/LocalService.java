package com.ritikrishu.newnonuiindoors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

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

public class LocalService extends Service{
    final IncomingHandler mIncomingHandler = new IncomingHandler();
    final Messenger mMessenger = new Messenger(mIncomingHandler);
    public static final int START = 1;
    public static final int STOP = 2;

    public static final String TAG = "SGIND";

    @Override
    public void onCreate() {
        super.onCreate();
        mIncomingHandler.setContext(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
    private static class IncomingHandler extends Handler implements IndoorsServiceCallback, IndoorsLocationListener {
        private Indoors indoors;
        Building mBuilding;
        Context mContext;
        public void setContext(Context ctx){
            mContext = ctx;
        }
        public void start() {
            IndoorsFactory.createInstance(mContext, /*todo*/"insert api key", this, false);
        }
        public void stop(){
            indoors.removeLocationListener(this);

            IndoorsFactory.releaseInstance(this);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    start();
                    break;
                case STOP:
                    stop();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }



        @Override
        public void loadingBuilding(LoadingBuildingStatus loadingBuildingStatus) {
            Log.d(TAG, loadingBuildingStatus.getPhase() + " <- loading");
        }

        @Override
        public void buildingLoaded(Building building) {
            mBuilding = building;
            Log.d(TAG, building.getName() + " <- building loaded");
        }

        @Override
        public void leftBuilding(Building building) {
            Log.d(TAG, building.getName() + " <- building left");
        }

        @Override
        public void buildingReleased(Building building) {
            Log.d(TAG, building.getName() + " <- building released");
        }

        @Override
        public void positionUpdated(Coordinate coordinate, int i) {
            Location geoLocation = IndoorsCoordinateUtil.toGeoLocation(coordinate, mBuilding);
            Log.d(TAG, coordinate.x + " -- " + coordinate.y + " <- positionUpdated x -- y");
            Log.d(TAG, geoLocation.getLatitude() + " -- " + geoLocation.getLongitude() + " <- positionUpdated lat -- lng");
        }

        @Override
        public void orientationUpdated(float v) {
            Log.d(TAG, v + " <- orientationUpdated");
        }

        @Override
        public void changedFloor(int i, String s) {
            Log.d(TAG, s + " -- " + i + " <- changedFloor");
        }

        @Override
        public void enteredZones(List<Zone> list) {
            Log.d(TAG, list.size() + " <- enteredZones list size");
        }

        @Override
        public void buildingLoadingCanceled() {
            Log.d(TAG,"buildingLoadingCanceled");
        }

        @Override
        public void connected() {
            indoors = IndoorsFactory.getInstance();

            indoors.registerLocationListener(this);

            indoors.setLocatedCloudBuilding(0/*todo change with actual building id*/, new LocalizationParameters(), true);
        }

        @Override
        public void onError(IndoorsException e) {
            Log.d(TAG,"onError -> ");
            e.printStackTrace();
        }
    }
}
