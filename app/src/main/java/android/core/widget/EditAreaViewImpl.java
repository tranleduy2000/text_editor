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

package android.core.widget;

import android.content.Context;
import android.core.text.method.MovementMethod;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by Duy on 10-Apr-18.
 */

public class EditAreaViewImpl extends AppCompatEditText implements IEditAreaView {
    public EditAreaViewImpl(Context context) {
        super(context);
    }

    public EditAreaViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditAreaViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setReadOnly(boolean readOnly) {

    }

    @Override
    public void duplication() {

    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public boolean canRedo() {
        return false;
    }

    @Override
    public boolean backLocation() {
        return false;
    }

    @Override
    public boolean forwardLocation() {
        return false;
    }

    @Override
    public void gotoEnd() {

    }

    @Override
    public void gotoTop() {

    }

    @Override
    public void gotoLine(int line) {

    }

    @Override
    public void convertWrapCharTo(String object) {

    }

    @Override
    public boolean selectAllText() {
        return false;
    }

    @Override
    public boolean paste() {
        return false;
    }

    @Override
    public boolean copy() {
        return false;
    }

    @Override
    public boolean cut() {
        return false;
    }

    @Override
    public void redo() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void showSoftInput() {

    }

    @Override
    public void hideSoftInput() {

    }

    @Override
    public void setSelection(int start, int end) {

    }

    @Override
    public void setSelection(int offset) {

    }

    @Override
    public void setMovementMethod(MovementMethod method) {

    }

    @Override
    public void setLineNumber(int lineNumber) {

    }


}
