/*
 * Copyright (c) 2015 The CyanogenMod Project
 * Copyright (c) 2017-2022 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.aospa.settings.device.actions;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import co.aospa.settings.device.MotoActionsSettings;
import co.aospa.settings.device.SensorHelper;

public class CameraActivationSensor implements SensorEventListener, UpdatedStateNotifier {
    private static final String TAG = "MotoActions-CameraSensor";

    private final MotoActionsSettings mMotoActionsSettings;

    private boolean mIsEnabled;

    public CameraActivationSensor(MotoActionsSettings motoActionsSettings,
                                  SensorHelper sensorHelper) {
        mMotoActionsSettings = motoActionsSettings;
        Sensor sensor = sensorHelper.getCameraActivationSensor();
        sensorHelper.registerListener(sensor, this);
    }

    @Override
    public synchronized void updateState() {
        if (mMotoActionsSettings.isCameraGestureEnabled() && !mIsEnabled) {
            Log.d(TAG, "Enabling");
            mIsEnabled = true;
        } else if (!mMotoActionsSettings.isCameraGestureEnabled() && mIsEnabled) {
            Log.d(TAG, "Disabling");
            mIsEnabled = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "activate camera");
        if (mIsEnabled) {
            mMotoActionsSettings.cameraAction();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public static boolean hasSensor(Context context) {
        SensorHelper sensorHelper = new SensorHelper(context);
        return sensorHelper.getCameraActivationSensor() != null;
    }
}
