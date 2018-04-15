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

package com.duy.text.editor.hightlight.pack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import static com.jecelyin.common.utils.IOUtils.read;

/**
 * Created by Duy on 15-Apr-18.
 */

class JsonUnpacker implements IUnpacker {
    private InputStream in;
    private JSONObject mJsonObject;
    private JSONArray mJsonArray;
    private Stack<Cursor> mStack = new Stack<>();

    JsonUnpacker(InputStream in) throws IOException, JSONException {
        this.in = in;
        String content = read(in, "UTF-8");
        mJsonObject = new JSONObject(content);
        mJsonArray = mJsonObject.getJSONArray(JsonPacker.ROOT_KEY);
        mStack.push(new Cursor(mJsonArray));
    }

    @Override
    public boolean hasNext() throws IOException {
        if (mStack.isEmpty()) {
            return false;
        }
        return mStack.peek().hasNext();
    }

    @Override
    public void close() throws IOException {
        in.close();
    }

    @Override
    public String unpackString() throws IOException, JSONException {
        checkCursorIndex();
        return (String) mStack.peek().next();
    }

    private void checkCursorIndex() {
        if (mStack.isEmpty()) {
            return;
        }
        if (!mStack.peek().hasNext()) {
            mStack.pop();
            checkCursorIndex();
        }
    }

    @Override
    public int unpackMapHeader() throws IOException, JSONException {
        checkCursorIndex();
        JSONArray array = mStack.peek().nextArray();
        mStack.push(new Cursor(array));
        return array.length();
    }

    @Override
    public int unpackInt() throws IOException, JSONException {
        checkCursorIndex();
        return mStack.peek().nextInt();
    }

    private static class Cursor {
        private final JSONArray array;
        private int currentIndex;

        public Cursor(JSONArray array) {
            this(0, array);
        }

        public Cursor(int currentIndex, JSONArray array) {
            this.currentIndex = currentIndex;
            this.array = array;
        }

        boolean hasNext() {
            return currentIndex < array.length();
        }

        Object next() throws JSONException {
            Object o = array.get(currentIndex);
            currentIndex++;
            return o;
        }

        JSONArray nextArray() throws JSONException {
            return (JSONArray) next();
        }

        int nextInt() throws JSONException {
            return (int) next();
        }
    }
}
