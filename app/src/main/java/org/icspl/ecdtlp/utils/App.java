package org.icspl.ecdtlp.utils;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.icspl.ecdtlp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abc on 4/5/2018.
 */

public class App extends Application {



    @Override
    public void onCreate() {
        super.onCreate();


        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        /**
         * Gets the default {@link Tracker} for this {@link Application}.
         * @return tracker
         */

        // Default Values
        Map<String, Object> map = new HashMap<>();
        map.put(UpdateHelper.KEY_UPDATE_ENABLE, false);
        map.put(UpdateHelper.KEY_UPDATE_VERSION, "1.0");
        map.put(UpdateHelper.KEY_UPDATE_UPDATE_URL, "details?id=org.icspl.ecdtlp");

        remoteConfig.setDefaults(map);
        remoteConfig.fetch(5).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Log.i("MyTag", "onComplete: Task Successfull");
                    remoteConfig.activateFetched();
                }
            }
        });

    }
}
