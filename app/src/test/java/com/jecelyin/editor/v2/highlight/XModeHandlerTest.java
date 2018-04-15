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

import com.jecelyin.common.utils.DLog;

import junit.framework.TestCase;

import org.gjt.sp.jedit.Mode;
import org.gjt.sp.jedit.syntax.ModeProvider;
import org.gjt.sp.jedit.syntax.ParserRuleSet;
import org.gjt.sp.jedit.syntax.TokenMarker;
import org.gjt.sp.jedit.syntax.XModeHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.jecelyin.editor.v2.tools.XML2Bin.assetsPath;

/**
 * Created by Duy on 15-Apr-18.
 */
public class XModeHandlerTest extends TestCase {
    public void testProcessPascal() throws Exception {
        File file = new File(assetsPath, "syntax/pascal.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        dBuilder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String s, String systemId) throws SAXException, IOException {
                if (systemId.contains("xmode.dtd")) {
                    return new InputSource(new FileInputStream(new File(assetsPath, "xmode.dtd")));
                }
                return null;
            }
        });
        Document doc = dBuilder.parse(file);
        Element root = doc.getDocumentElement();
        root.normalize();


        XModeHandler pascal = new XModeHandler("Pascal"){
            @Override
            protected void error(String msg, Object subst) {
                DLog.e(getClass().getName() + " error: " + msg + " obj: " + subst);
            }

            @Override
            protected TokenMarker getTokenMarker(String modeName) {
                Mode mode = ModeProvider.instance.getMode(modeName);
                if (mode == null)
                    return null;
                else
                    return mode.getTokenMarker();
            }
        };

        Hashtable<String, String> modeProperties = pascal.getModeProperties();
        System.out.println("modeProperties = " + modeProperties);

        TokenMarker tokenMarker = pascal.getTokenMarker();
        ParserRuleSet[] ruleSets = tokenMarker.getRuleSets();
        System.out.println("ruleSets = " + Arrays.toString(ruleSets));

    }

}