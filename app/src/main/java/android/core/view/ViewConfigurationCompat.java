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

package android.core.view;

import android.view.ViewConfiguration;

import com.jecelyin.common.utils.DLog;
import com.jecelyin.common.utils.MethodReflection;

/**
 * @author Jecelyin Peng <jecelyin@gmail.com>
 */
public class ViewConfigurationCompat {
    private static MethodReflection getScaledDoubleTapTouchSlop;

    public static int getScaledDoubleTapTouchSlop(ViewConfiguration viewConfiguration) {
        try {
            if (getScaledDoubleTapTouchSlop == null) {
                getScaledDoubleTapTouchSlop = new MethodReflection(ViewConfiguration.class, "getScaledDoubleTapTouchSlop", new Class[]{});
            }
            return getScaledDoubleTapTouchSlop.get(viewConfiguration);
        } catch (Throwable e) {
            DLog.e(e);
            final int TOUCH_SLOP = 8;
            return TOUCH_SLOP;
        }
    }
}
