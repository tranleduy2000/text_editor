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

package com.jecelyin.editor.v2.highlight.pack;

import android.support.v4.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Stack;

/**
 * Created by Duy on 15-Apr-18.
 */

class JsonPacker implements IPacker {
    public static final String ROOT_KEY = "root";
    private JSONObject mJsonObject;
    private Stack<Pair<Integer, JSONArray>> mStack = new Stack<>();
    private OutputStream mOutputStream;

    JsonPacker(OutputStream out) throws JSONException {
        this.mOutputStream = out;
        mJsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        mJsonObject.put(ROOT_KEY, jsonArray);
        mStack.push(new Pair<>(Integer.MAX_VALUE, jsonArray));
    }

    @Override
    public void close() throws IOException, JSONException {
        String content = mJsonObject.toString(4);
        System.out.println(content);
        mOutputStream.write(content.getBytes());
        mOutputStream.close();
    }

    @Override
    public void packString(String value) throws IOException, JSONException {
        checkStackSize();

        Pair<Integer, JSONArray> pair = mStack.peek();
        JSONArray jsonArray = pair.second;
        jsonArray.put(value);
        checkStackSize();
    }


    @Override
    public void packMapHeader(int size) throws IOException {
        checkStackSize();

        final JSONArray parent = mStack.peek().second;

        JSONArray map = new JSONArray();
        parent.put(map);
        mStack.push(new Pair<>(size, map));

        for (int i = 0; i < size; i++) {
            JSONArray entry = new JSONArray();
            map.put(entry);
            mStack.push(new Pair<>(2, entry));
        }
    }

    @Override
    public void packInt(int value) throws IOException {
        checkStackSize();
        Pair<Integer, JSONArray> pair = mStack.peek();
        JSONArray jsonArray = pair.second;
        jsonArray.put(value);
    }

    private void checkStackSize() {
        if (!mStack.isEmpty()) {
            Pair<Integer, JSONArray> pair = mStack.peek();
            if (pair.first/*max size*/ <= pair.second.length()/*current length*/) {
                mStack.pop();
            }
        }
    }
}
