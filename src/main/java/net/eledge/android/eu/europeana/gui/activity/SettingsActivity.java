/*
 * Copyright (c) 2014 eLedge.net and the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.eledge.android.eu.europeana.gui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import net.eledge.android.eu.europeana.R;
import net.eledge.android.eu.europeana.gui.fragment.SettingsFragment;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class SettingsActivity extends PreferenceActivity {

    public static final String PREFS_LOCALE = "net.eledge.android.eu.europeana.prefs.PREFS_LOCALE";
    public static final String PREFS_ABOUT = "net.eledge.android.eu.europeana.prefs.PREFS_ABOUT";

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            String action = getIntent().getAction();
            if (StringUtils.isNotBlank(action)) {
                switch (action) {
                    case PREFS_LOCALE:
                        addPreferencesFromResource(R.xml.settings_locale);
                        break;
                    case PREFS_ABOUT:
                        addPreferencesFromResource(R.xml.settings_about);
                        break;
                    default:
                        addPreferencesFromResource(R.xml.settings_legacy);
                        break;
                }
            } else {
                addPreferencesFromResource(R.xml.settings_legacy);
            }
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.settings, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return StringUtils.equals(SettingsFragment.class.getName(), fragmentName);
    }
}
