package com.ritikrishu.newnonuiindoors;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.customlbs.shared.Coordinate;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IndoorsServiceCallback, IndoorsLocationListener {

    private Indoors indoors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRequiredPermissions();
    }

    private void getRequiredPermissions(){
        if(!(
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
                )){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE
            }, 0);
        }
    }

    public void start(View v){
        IndoorsFactory.createInstance(this, /*todo*/"insert api key", this, false);
    }
    public void stop(View v){
        super.onStop();

        indoors.removeLocationListener(this);

        IndoorsFactory.releaseInstance(this);
    }

    @Override
    public void loadingBuilding(LoadingBuildingStatus loadingBuildingStatus) {

    }

    @Override
    public void buildingLoaded(Building building) {

    }

    @Override
    public void leftBuilding(Building building) {

    }

    @Override
    public void buildingReleased(Building building) {

    }

    @Override
    public void positionUpdated(Coordinate coordinate, int i) {

    }

    @Override
    public void orientationUpdated(float v) {

    }

    @Override
    public void changedFloor(int i, String s) {

    }

    @Override
    public void enteredZones(List<Zone> list) {

    }

    @Override
    public void buildingLoadingCanceled() {

    }

    @Override
    public void connected() {
        indoors = IndoorsFactory.getInstance();

        indoors.registerLocationListener(this);

        indoors.setLocatedCloudBuilding(0/*todo change with actual building id*/, new LocalizationParameters(), true);
    }

    @Override
    public void onError(IndoorsException e) {

    }
}
