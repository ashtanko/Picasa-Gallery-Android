/*
 * Copyright 2017 Alexey Shtanko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.shtanko.picasagallery.vendors.utils;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import io.shtanko.picasagallery.view.util.AndroidUtils;

public class LayoutHelper {

  public static final int MATCH_PARENT = -1;
  public static final int WRAP_CONTENT = -2;

  private static int getSize(float size) {
    return (int) (size < 0 ? size : AndroidUtils.INSTANCE.dp(size));
  }

  public static FrameLayout.LayoutParams createScroll(int width, int height, int gravity) {
    return new ScrollView.LayoutParams(getSize(width), getSize(height), gravity);
  }

  public static FrameLayout.LayoutParams createFrame(int width, float height, int gravity,
      float leftMargin, float topMargin, float rightMargin, float bottomMargin) {
    FrameLayout.LayoutParams layoutParams =
        new FrameLayout.LayoutParams(getSize(width), getSize(height), gravity);
    layoutParams.setMargins(AndroidUtils.INSTANCE.dp(leftMargin),
        AndroidUtils.INSTANCE.dp(topMargin), AndroidUtils.INSTANCE.dp(rightMargin),
        AndroidUtils.INSTANCE.dp(bottomMargin));
    return layoutParams;
  }

  public static FrameLayout.LayoutParams createFrame(int width, int height, int gravity) {
    return new FrameLayout.LayoutParams(getSize(width), getSize(height), gravity);
  }

  public static FrameLayout.LayoutParams createFrame(int width, float height) {
    return new FrameLayout.LayoutParams(getSize(width), getSize(height));
  }

  public static RelativeLayout.LayoutParams createRelative(float width, float height,
      int leftMargin, int topMargin, int rightMargin, int bottomMargin, int alignParent,
      int alignRelative, int anchorRelative) {
    RelativeLayout.LayoutParams layoutParams =
        new RelativeLayout.LayoutParams(getSize(width), getSize(height));
    if (alignParent >= 0) {
      layoutParams.addRule(alignParent);
    }
    if (alignRelative >= 0 && anchorRelative >= 0) {
      layoutParams.addRule(alignRelative, anchorRelative);
    }
    layoutParams.leftMargin = AndroidUtils.INSTANCE.dp(leftMargin);
    layoutParams.topMargin = AndroidUtils.INSTANCE.dp(topMargin);
    layoutParams.rightMargin = AndroidUtils.INSTANCE.dp(rightMargin);
    layoutParams.bottomMargin = AndroidUtils.INSTANCE.dp(bottomMargin);
    return layoutParams;
  }

  public static RelativeLayout.LayoutParams createRelative(int width, int height, int leftMargin,
      int topMargin, int rightMargin, int bottomMargin) {
    return createRelative(width, height, leftMargin, topMargin, rightMargin, bottomMargin, -1, -1,
        -1);
  }

  public static RelativeLayout.LayoutParams createRelative(int width, int height, int leftMargin,
      int topMargin, int rightMargin, int bottomMargin, int alignParent) {
    return createRelative(width, height, leftMargin, topMargin, rightMargin, bottomMargin,
        alignParent, -1, -1);
  }

  public static RelativeLayout.LayoutParams createRelative(float width, float height,
      int leftMargin, int topMargin, int rightMargin, int bottomMargin, int alignRelative,
      int anchorRelative) {
    return createRelative(width, height, leftMargin, topMargin, rightMargin, bottomMargin, -1,
        alignRelative, anchorRelative);
  }

  public static RelativeLayout.LayoutParams createRelative(int width, int height, int alignParent,
      int alignRelative, int anchorRelative) {
    return createRelative(width, height, 0, 0, 0, 0, alignParent, alignRelative, anchorRelative);
  }

  public static RelativeLayout.LayoutParams createRelative(int width, int height) {
    return createRelative(width, height, 0, 0, 0, 0, -1, -1, -1);
  }

  public static RelativeLayout.LayoutParams createRelative(int width, int height, int alignParent) {
    return createRelative(width, height, 0, 0, 0, 0, alignParent, -1, -1);
  }

  public static RelativeLayout.LayoutParams createRelative(int width, int height, int alignRelative,
      int anchorRelative) {
    return createRelative(width, height, 0, 0, 0, 0, -1, alignRelative, anchorRelative);
  }

  public static LinearLayout.LayoutParams createLinear(int width, int height, float weight,
      int gravity, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(getSize(width), getSize(height), weight);
    layoutParams.setMargins(AndroidUtils.INSTANCE.dp(leftMargin),
        AndroidUtils.INSTANCE.dp(topMargin), AndroidUtils.INSTANCE.dp(rightMargin),
        AndroidUtils.INSTANCE.dp(bottomMargin));
    layoutParams.gravity = gravity;
    return layoutParams;
  }

  public static LinearLayout.LayoutParams createLinear(int width, int height, float weight,
      int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(getSize(width), getSize(height), weight);
    layoutParams.setMargins(AndroidUtils.INSTANCE.dp(leftMargin),
        AndroidUtils.INSTANCE.dp(topMargin), AndroidUtils.INSTANCE.dp(rightMargin),
        AndroidUtils.INSTANCE.dp(bottomMargin));
    return layoutParams;
  }

  public static LinearLayout.LayoutParams createLinear(int width, int height, int gravity,
      int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(getSize(width), getSize(height));
    layoutParams.setMargins(AndroidUtils.INSTANCE.dp(leftMargin),
        AndroidUtils.INSTANCE.dp(topMargin), AndroidUtils.INSTANCE.dp(rightMargin),
        AndroidUtils.INSTANCE.dp(bottomMargin));
    layoutParams.gravity = gravity;
    return layoutParams;
  }

  public static LinearLayout.LayoutParams createLinear(int width, int height, float leftMargin,
      float topMargin, float rightMargin, float bottomMargin) {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(getSize(width), getSize(height));
    layoutParams.setMargins(AndroidUtils.INSTANCE.dp(leftMargin),
        AndroidUtils.INSTANCE.dp(topMargin), AndroidUtils.INSTANCE.dp(rightMargin),
        AndroidUtils.INSTANCE.dp(bottomMargin));
    return layoutParams;
  }

  public static LinearLayout.LayoutParams createLinear(int width, int height, float weight,
      int gravity) {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(getSize(width), getSize(height), weight);
    layoutParams.gravity = gravity;
    return layoutParams;
  }

  public static LinearLayout.LayoutParams createLinear(int width, int height, int gravity) {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(getSize(width), getSize(height));
    layoutParams.gravity = gravity;
    return layoutParams;
  }

  public static LinearLayout.LayoutParams createLinear(int width, int height, float weight) {
    return new LinearLayout.LayoutParams(getSize(width), getSize(height), weight);
  }

  public static LinearLayout.LayoutParams createLinear(int width, int height) {
    return new LinearLayout.LayoutParams(getSize(width), getSize(height));
  }
}
