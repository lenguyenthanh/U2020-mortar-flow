/*
 * Copyright 2014 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lt.eliga.u2020.core.navigation_screen.flow;

import android.view.View;

import flow.Flow;

/**
 * Support for {@link HandlesUp} and {@link HandlesBack}.
 */
public class UpAndBack {
  public static boolean onUpPressed(View childView) {
    if (childView instanceof HandlesUp) {
      if (((HandlesUp) childView).onUpPressed()) {
        return true;
      }
    }
    // Try to go up.  If up isn't supported, go back.
    return Flow.get(childView).goUp() || onBackPressed(childView);
  }

  public static boolean onBackPressed(View childView) {
    if (childView instanceof HandlesBack) {
      if (((HandlesBack) childView).onBackPressed()) {
        return true;
      }
    }
    return Flow.get(childView).goBack();
  }

  private UpAndBack() {
  }
}