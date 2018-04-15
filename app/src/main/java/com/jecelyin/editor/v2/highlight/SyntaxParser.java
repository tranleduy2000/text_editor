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

import com.jecelyin.editor.v2.TextEditorApplication;

import org.gjt.sp.jedit.Mode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.jecelyin.editor.v2.tools.XML2Bin.assetsPath;

/**
 * @author Jecelyin Peng <jecelyin@gmail.com>
 */

public class SyntaxParser {
    public static void loadMode(Mode mode) {
        String filename = mode.getFile();
//        int langDefine = LangMap.get(filename);
//        if (langDefine == 0) {
//            DLog.d("Can't find a lang define: " + filename);
//            return;
//        }
//        DLog.d("load mode: " + filename);

//        ModeObjectHandler handler = new ModeObjectHandler(mode.getName());
//        mode.setTokenMarker(handler.getTokenMarker());
//        try {
//            handler.process(langDefine);
//        } catch (Exception e) {
//            DLog.e(e);
//        }
        try {
            InputStream inputStream = TextEditorApplication.getContext().getAssets().open("syntax/" + filename);
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
            Document doc = dBuilder.parse(inputStream);
            Element rootNode = doc.getDocumentElement();
            rootNode.normalize();

            XModeHandler handler = new XModeHandler(mode.getName());
            mode.setTokenMarker(handler.getTokenMarker());
            handler.process(rootNode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
