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

import org.msgpack.core.MessagePack;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Duy on 15-Apr-18.
 */

public class PackFactory {

    public static IPacker create(PackMode mode, OutputStream in) throws Exception {
        switch (mode) {
            case JSON:
                return new JsonPacker(in);
            case MESSAGE_PACK:
                return new MessagePackWrapper(MessagePack.newDefaultPacker(in));
            case TEST:
                return new JsonPacker(in);
        }
        return null;
    }


    public static IUnpacker create(PackMode mode, InputStream in) throws Exception {
        switch (mode) {
            case JSON:
                return new JsonUnpacker(in);
            case MESSAGE_PACK:
                return new MessageUnpackerWrapper(MessagePack.newDefaultUnpacker(in));
            case TEST:
                return new JsonUnpacker(in);
        }
        return null;
    }

    /**
     * Created by Duy on 15-Apr-18.
     */

    public static enum PackMode {
        JSON, MESSAGE_PACK, TEST
    }
}
