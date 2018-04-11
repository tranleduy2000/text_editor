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
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;

/**
 * Created by Duy on 10-Apr-18.
 */

public interface IEditAreaView {
    void setReadOnly(boolean readOnly);

    void setCustomSelectionActionModeCallback(ActionMode.Callback editorSelectionActionModeCallback);

    boolean hasSelection();

    void duplication();

    void setFreezesText(boolean b);

    Parcelable onSaveInstanceState();

    int getSelectionStart();

    int getSelectionEnd();

    boolean canUndo();

    boolean canRedo();

    boolean backLocation();

    boolean forwardLocation();

    Editable getText();

    void gotoEnd();

    void gotoTop();

    void gotoLine(int line);

    void convertWrapCharTo(String object);

    boolean selectAllText();

    boolean paste();

    boolean copy();

    boolean cut();

    void redo();

    void undo();

    void showSoftInput();

    void hideSoftInput();

    void setSelection(int start, int end);

    Editable getEditableText();

    void setEnabled(boolean b);

    void setSelection(int offset);

    boolean post(Runnable runnable);

    void addTextChangedListener(TextWatcher textWatcher);

    void setText(CharSequence content);

    void onRestoreInstanceState(Parcelable editorState);

    void setMovementMethod(MovementMethod method);

    Context getContext();

    void setText(int searching);

    void append(CharSequence s);

    void setLineNumber(int lineNumber);
}
