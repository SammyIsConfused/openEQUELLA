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

package com.tle.web.wizard.impl;

import com.tle.web.sections.SectionInfo;
import com.tle.web.sections.events.js.JSHandler;
import com.tle.web.sections.js.JSCallable;
import com.tle.web.wizard.WebWizardPage;
import com.tle.web.wizard.WizardService;
import com.tle.web.wizard.WizardState;
import com.tle.web.wizard.section.PagesSection;
import com.tle.web.wizard.section.WizardSectionInfo;
import javax.inject.Inject;

public abstract class WizardCommand {
  private final String name;
  private final String value;
  @Inject private WizardService wizardService;

  public WizardCommand(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public final String getName() {
    return name;
  }

  public final String getValue() {
    return value;
  }

  public String getWarning(SectionInfo info, WizardSectionInfo winfo) {
    return null;
  }

  public abstract void execute(SectionInfo info, WizardSectionInfo winfo, String data)
      throws Exception;

  public JSHandler getJavascript(
      SectionInfo info, WizardSectionInfo winfo, JSCallable submitFunction) {
    return null;
  }

  public boolean isMajorAction() {
    return false;
  }

  public boolean addToMoreActionList() {
    return false;
  }

  @SuppressWarnings("nls")
  public String getStyleClass() {
    throw new UnsupportedOperationException(
        isMajorAction()
            ? "Major actions must supply an extra style class defining the background image"
            : "Requests for getStyleClass() should only be invoked if isMajorAction() is true");
  }

  public abstract boolean isEnabled(SectionInfo info, WizardSectionInfo winfo);

  public void validateMandatoryFields(SectionInfo info, WizardState state) {
    PagesSection ps = info.lookupSection(PagesSection.class);
    wizardService.getWizardPages(state).stream()
        .filter(WebWizardPage::isViewable)
        .forEach(p -> wizardService.ensureInitialisedPage(info, p, ps.getReloadFunction(), true));
  }
}
