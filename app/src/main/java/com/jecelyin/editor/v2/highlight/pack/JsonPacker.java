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
    private JSONObject mJsonObject;
    private Stack<Pair<Integer, JSONArray>> mStack = new Stack<>();
    private OutputStream mOutputStream;

    JsonPacker(OutputStream out) throws JSONException {
        this.mOutputStream = out;
        mJsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        mJsonObject.put("root", jsonArray);
        mStack.push(new Pair<>(Integer.MAX_VALUE, jsonArray));
    }

    @Override
    public void close() throws IOException {
        String content = mJsonObject.toString();
//        mOutputStream.write(content.getBytes());
//        mOutputStream.close();
        System.out.println(content);
    }

    @Override
    public void packString(String value) throws IOException, JSONException {
        Pair<Integer, JSONArray> pair = mStack.peek();
        if (canPush(pair)) {
            mStack.pop();
            packString(value);
            return;
        }
        JSONArray jsonArray = pair.second;
        jsonArray.put(jsonArray);
    }

    private boolean canPush(Pair<Integer, JSONArray> pair) {
        return pair.first >= pair.second.length();
    }

    @Override
    public void packMapHeader(int size) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONArray parent = mStack.peek().second;
        parent.put(jsonArray);
        mStack.push(new Pair<>(size, jsonArray));
    }

    @Override
    public void packInt(int value) throws IOException {
        Pair<Integer, JSONArray> pair = mStack.peek();
        if (canPush(pair)) {
            mStack.pop();
            packInt(value);
            return;
        }
        JSONArray jsonArray = pair.second;
        jsonArray.put(jsonArray);
    }
}

