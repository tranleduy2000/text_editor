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

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Duy on 15-Apr-18.
 */
public interface IUnpacker {
    boolean hasNext() throws IOException;

    void close() throws IOException;

    String unpackString() throws IOException, JSONException;

    int unpackMapHeader() throws IOException, JSONException;

    int unpackInt() throws IOException, JSONException;
}
