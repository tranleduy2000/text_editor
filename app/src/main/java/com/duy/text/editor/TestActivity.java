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

package com.duy.text.editor;

import android.app.Activity;
import android.core.util.GrowingArrayUtils;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.jecelyin.editor.v2.common.utils.DLog;
import com.jecelyin.editor.v2.io.CharArrayBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class TestActivity extends Activity implements TextWatcher {

    private static final String TAG = "TestActivity";
    private final static int BUFFER_SIZE = 16 * 1024;
    private TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        editText = findViewById(R.id.edit_text);
        editText.setSaveEnabled(false);
        editText.addTextChangedListener(this);

        if (DLog.DEBUG)
            DLog.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (DLog.DEBUG) {
            DLog.d(TAG, "onTextChanged: " + s.length());
            DLog.d(TAG, "onTextChanged: line count " + editText.getLayout().getLineCount());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            File file = new File(getCacheDir(), "temp.txt");
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            char[] buf = new char[BUFFER_SIZE];
            int len;
            CharArrayBuffer arrayBuffer = new CharArrayBuffer(GrowingArrayUtils.growSize((int) file.length()));
            while ((len = reader.read(buf, 0, BUFFER_SIZE)) != -1) {
                arrayBuffer.append(buf, 0, len);
            }

            reader.close();
            editText.setText(new String(arrayBuffer.buffer()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            File file = new File(getCacheDir(), "temp.txt");
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(editText.getText().toString().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        editText.removeTextChangedListener(this);
        super.onDestroy();
    }
}
