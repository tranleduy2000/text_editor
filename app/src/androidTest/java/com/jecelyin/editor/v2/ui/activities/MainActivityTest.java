/*
 * Copyright (C) 2016 Jecelyin Peng <jecelyin@gmail.com>
 *
 * This file is part of 920 Text Editor.
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

package com.jecelyin.editor.v2.ui.activities;

import android.core.widget.EditAreaView;
import android.support.test.rule.ActivityTestRule;

import com.duy.text.editor.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;

/**
 * Created by Duy on 15-Apr-18.
 */
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    public static String duplicateStr(String value, int count) {
        StringBuilder stringBuilder = new StringBuilder(value.length() * count);
        for (int i = 0; i < count; i++) {
            stringBuilder.append(value);
        }
        return stringBuilder.toString();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onSaveInstanceState() throws Throwable {
        final String value = duplicateStr("ActivityTestRule<MainActivity> mRule = new ActivityTestRule<MainActivity>(MainA\n", 4000);
//        onView(withId(R.id.edit_text)).perform(replaceText(value));
        mRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditAreaView editView = mRule.getActivity().findViewById(R.id.edit_text);
                editView.setText(value);
            }
        });
        onView(withContentDescription(mRule.getActivity().getString(R.string.open))).perform(click());
    }

}