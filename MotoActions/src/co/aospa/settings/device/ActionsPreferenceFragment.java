/*
 * Copyright (C) 2015-2016 The CyanogenMod Project
 * Copyright (C) 2017-2022 The LineageOS Project
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

package co.aospa.settings.device;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragment;

import co.aospa.settings.device.actions.CameraActivationSensor;

public class ActionsPreferenceFragment extends PreferenceFragment {

    private static final String KEY_ACTIONS_CATEGORY = "actions_key";
    private static final String KEY_GESTURE_CAMERA_ACTION = "gesture_camera_action";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.actions_panel);

        if (!CameraActivationSensor.hasSensor(getContext())) {
            PreferenceCategory category = findPreference(KEY_ACTIONS_CATEGORY);
            category.removePreferenceRecursively(KEY_GESTURE_CAMERA_ACTION);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return false;
    }
}
