/*
 * Licensed to The Apereo Foundation under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * The Apereo Foundation licenses this file to you under the Apache License,
 * Version 2.0, (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tle.core.settings.security;

import com.tle.common.security.SettingsTarget;
import com.tle.core.guice.Bind;
import com.tle.core.security.SecurityTargetHandler;
import java.util.Set;
import javax.inject.Singleton;

@Bind
@Singleton
public class SettingsSecurityTargetHandler implements SecurityTargetHandler {
  @Override
  public void gatherAllLabels(Set<String> labels, Object target) {
    labels.add(getPrimaryLabel(target));
  }

  @Override
  @SuppressWarnings("nls")
  public String getPrimaryLabel(Object target) {
    return "C:" + ((SettingsTarget) target).getId();
  }

  @Override
  public Object transform(Object target) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isOwner(Object target, String userId) {
    throw new UnsupportedOperationException();
  }
}
