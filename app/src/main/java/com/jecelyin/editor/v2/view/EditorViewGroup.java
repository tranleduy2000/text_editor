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

package com.jecelyin.editor.v2.view;

import android.content.Context;
import android.core.widget.EditorView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.duy.text.editor.R;
import com.jecelyin.editor.v2.common.OnVisibilityChangedListener;

/**
 * @author Jecelyin Peng <jecelyin@gmail.com>
 */
public class EditorViewGroup extends RelativeLayout {
    private EditorView editText;
    private ProgressBar progressView;
    private boolean removed = false;
    private OnVisibilityChangedListener visibilityChangedListener;

    public EditorViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        editText = findViewById(R.id.edit_text);
        progressView = findViewById(R.id.progress_view);

    }

    public EditorView getEditText() {
        return editText;
    }

    public void setLoading(boolean loading) {
        if (loading) {
            ((View) editText).setVisibility(GONE);
            progressView.setVisibility(VISIBLE);
        } else {
            ((View) editText).setVisibility(VISIBLE);
            progressView.setVisibility(GONE);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved() {
        this.removed = true;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (visibilityChangedListener != null)
            visibilityChangedListener.onVisibilityChanged(visibility);
    }

    public void setVisibilityChangedListener(OnVisibilityChangedListener visibilityChangedListener) {
        this.visibilityChangedListener = visibilityChangedListener;
    }
}