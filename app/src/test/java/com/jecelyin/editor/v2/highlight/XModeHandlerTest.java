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

package com.jecelyin.editor.v2.highlight;

import android.util.Log;

import com.jecelyin.common.utils.DLog;

import junit.framework.TestCase;

import org.gjt.sp.jedit.Mode;
import org.gjt.sp.jedit.syntax.ModeProvider;
import org.gjt.sp.jedit.syntax.ParserRuleSet;
import org.gjt.sp.jedit.syntax.TokenMarker;
import org.gjt.sp.jedit.syntax.XModeHandler;

import java.util.Arrays;
import java.util.Hashtable;

import static android.view.View.MeasureSpec.getMode;

/**
 * Created by Duy on 15-Apr-18.
 */
public class XModeHandlerTest extends TestCase {
    public void testProcessPascal() throws Exception {
        Mode mode = ModeProvider.instance.getMode("Pascal");

        XModeHandler pascal = new XModeHandler(mode.getName()) {
            @Override
            public void error(String what, Object subst) {
                DLog.log(Log.ERROR, this, subst);
            }

            @Override
            public TokenMarker getTokenMarker(String modeName) {
                Mode mode = ModeProvider.instance.getMode(modeName);
                if (mode == null)
                    return null;
                else
                    return mode.getTokenMarker();
            }
        };

        ModeProvider.instance.loadMode(mode);

        Hashtable<String, String> modeProperties = pascal.getModeProperties();
        System.out.println("modeProperties = " + modeProperties);

        Hashtable<String, String> props = pascal.getProps();
        System.out.println("props = " + props);

        TokenMarker tokenMarker = pascal.getTokenMarker();
        ParserRuleSet[] ruleSets = tokenMarker.getRuleSets();
        System.out.println("ruleSets = " + Arrays.toString(ruleSets));

    }

}