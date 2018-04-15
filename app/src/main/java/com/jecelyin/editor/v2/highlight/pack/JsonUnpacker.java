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

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Duy on 15-Apr-18.
 */

class JsonUnpacker implements IUnpacker {
    public JsonUnpacker(InputStream in) {
    }

    @Override
    public boolean hasNext() throws IOException {
        return false;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public String unpackString() throws IOException {
        return null;
    }

    @Override
    public int unpackMapHeader() throws IOException {
        return 0;
    }

    @Override
    public int unpackInt() throws IOException {
        return 0;
    }
}
