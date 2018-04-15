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
import java.io.OutputStream;

/**
 * Created by Duy on 15-Apr-18.
 */

public class MockPacker implements IPacker {
    private OutputStream in;

    public MockPacker(OutputStream in) {

        this.in = in;
    }

    @Override
    public void close() throws IOException {
        System.out.println("MockPacker.close");
    }

    @Override
    public void packString(String value) throws IOException {
        System.out.println("MockPacker.packString");
        System.out.println("string = " + value);
    }

    @Override
    public void packMapHeader(int size) throws IOException {
        System.out.println("MockPacker.packMapHeader");
        System.out.println("size = " + size);
    }

    @Override
    public void packInt(int value) throws IOException {
        System.out.println("MockPacker.packInt");
        System.out.println("size = " + value);
    }
}
