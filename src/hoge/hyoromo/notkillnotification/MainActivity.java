package hoge.hyoromo.notkillnotification;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

public class MainActivity extends Activity {
    private IMainService mMainServiceIf;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMainServiceIf = IMainService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMainServiceIf = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        Intent intent = new Intent(getApplicationContext(), MainService.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        super.onStart();
    }


    public void onShowClick(View v) {
        try {
            if (mMainServiceIf != null) mMainServiceIf.showNotification();
        } catch (RemoteException e) {
        }
    }

    public void onClearClick(View v) {
        try {
            if (mMainServiceIf != null) mMainServiceIf.clearNotification();
        } catch (RemoteException e) {
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}