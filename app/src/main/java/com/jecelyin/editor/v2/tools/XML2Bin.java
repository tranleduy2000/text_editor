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

package com.jecelyin.editor.v2.tools;

import com.duy.text.editor.BuildConfig;
import com.jecelyin.android.file_explorer.util.FileUtils;
import com.duy.text.editor.hightlight.pack.IPacker;
import com.duy.text.editor.hightlight.pack.PackFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.jecelyin.editor.v2.tools.Tool.fileNameToResName;
import static com.jecelyin.editor.v2.tools.Tool.o;
import static com.jecelyin.editor.v2.tools.Tool.readFile;
import static com.jecelyin.editor.v2.tools.Tool.space;
import static com.jecelyin.editor.v2.tools.Tool.textString;
import static com.jecelyin.editor.v2.tools.Tool.writeFile;


public class XML2Bin {
    public static File assetsPath;
    private static File highlightPath;
    private static File rawPath;
    private static IPacker packer;
    private static File syntaxPath;

    static {
        File f = new File(".");
        String path = f.getAbsolutePath();
        highlightPath = new File(path, "app/src/main/java/com/jecelyin/editor/v2/highlight");
        assetsPath = new File(path, "app/src/main/assets");
        syntaxPath = new File(assetsPath, "syntax");
        if (BuildConfig.DEBUG) {
            rawPath = new File(path, "app/src/test/res/raw");
        } else {
            rawPath = new File(path, "app/src/main/res/raw");
        }
    }

    public static void main(String[] args) {


        for (File f2 : rawPath.listFiles()) {
            f2.delete();
        }

        File[] files = syntaxPath.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".xml");
            }
        });

        StringBuilder mapCode = new StringBuilder();
        try {
            for (File file : files) {
                o("File: %s", file.getName());
                parseXml(file, mapCode);
            }

            String langMap = readFile(new File(assetsPath, "lang_map.tpl"));
            langMap = langMap.replace("@CASE_LIST@", mapCode.toString());
            writeFile(new File(highlightPath, "LangMap.java"), langMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void parseXml(final File file, StringBuilder mapCode) throws Exception {
        String clsName = fileNameToResName(file.getName()) + "_lang";

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
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getChildNodes();
        int length = nList.getLength();
        for (int i = 0; i < length; i++) {
            Node item = nList.item(i);
            o("node " + item.getNodeName() + " " + item.getNodeType());
            if (item.getNodeType() == Node.ELEMENT_NODE) {

                if (!item.getNodeName().equals("MODE"))
                    throw new RuntimeException("!MODE: " + item.getNodeName());
                File langFile = new File(rawPath, clsName);
                FileUtils.createNewFile(langFile);
                packer = PackFactory.create(PackFactory.PackMode.TEST, new FileOutputStream(langFile));

                handleChild(item);

                mapCode.append(space(12))
                        .append("case ")
                        .append(textString(file.getName()))
                        .append(": return R.raw.")
                        .append(clsName)
                        .append(";\n");


                packer.close();
            }
        }
    }


    private static void handleMode(Element element) throws Exception {
        NodeList childNodes = element.getChildNodes();
        int length = childNodes.getLength();

        ArrayList<Node> items = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            items.add(item);
        }

        for (Node item : items) {
            handleChild(item);
        }

    }

    private static void handleChild(Node node) throws Exception {
        String tag = node.getNodeName();
        packer.packString(tag);

        StringBuilder text = new StringBuilder();
        List<Node> nodes = nodes(node, text);
        packer.packString(text.toString());

        HashMap<String, String> attrs = attrs(node);
        packer.packMapHeader(attrs.size());
        for (Map.Entry<String, String> entry : attrs.entrySet()) {
            packer.packString(entry.getKey());
            packer.packString(entry.getValue());
        }

        packer.packInt(nodes.size());
        for (Node child : nodes) {
            handleChild(child);
        }

    }

    private static List<Node> nodes(Node node, StringBuilder text) {
        NodeList childNodes = node.getChildNodes();
        int length = childNodes.getLength();

        List<Node> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == Node.TEXT_NODE) {
                String str = item.getTextContent().trim();
                if (str.isEmpty())
                    continue;
                text.append(str);
                continue;
            } else if (item.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            list.add(item);
        }

        return list;
    }

    private static HashMap<String, String> attrs(Node node) {
        HashMap<String, String> map = new HashMap<String, String>();

        NamedNodeMap childNodes = node.getAttributes();
        if (childNodes == null)
            return map;

        int length = childNodes.getLength();

        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            map.put(item.getNodeName(), item.getNodeValue());
        }

        return map;
    }


}
