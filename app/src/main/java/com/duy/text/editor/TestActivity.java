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

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.core.util.GrowingArrayUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;

import com.duy.text.editor.views.EditorView;
import com.jecelyin.common.utils.DLog;
import com.jecelyin.editor.v2.io.CharArrayBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class TestActivity extends Activity implements TextWatcher {

    private static final String TAG = "TestActivity";
    private final static int BUFFER_SIZE = 16 * 1024;
    private EditorView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        editText = findViewById(R.id.edit_text);
        editText.setupEditor();
        editText.addTextChangedListener(this);

        if (DLog.DEBUG)
            DLog.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (DLog.DEBUG) {
            DLog.d(TAG, "onTextChanged: " + (s != null ? s.length() : "0"));
            DLog.d(TAG, "onTextChanged: line count " + (editText.getLayout() != null ? editText.getLayout().getLineCount() : ""));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        read();
    }

    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    private void read() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            char[] buf = new char[BUFFER_SIZE];
            int len;
            CharArrayBuffer arrayBuffer = new CharArrayBuffer(GrowingArrayUtils.growSize((int) file.length()));
            while ((len = reader.read(buf, 0, BUFFER_SIZE)) != -1) {
                arrayBuffer.append(buf, 0, len);
            }

            reader.close();
            String text = new String(arrayBuffer.buffer());
            editText.setText(text.substring(0, Math.min(1024, text.length())));
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
//        try {
//            File file = new File(getCacheDir(), "temp.txt");
//            file.createNewFile();
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            fileOutputStream.write(editText.getText().toString().getBytes());
//            fileOutputStream.flush();
//            fileOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onDestroy() {
        editText.removeTextChangedListener(this);
        super.onDestroy();
    }
}
