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

package com.tle.web.searching.selection;

import com.tle.common.Check;
import com.tle.core.guice.Bind;
import com.tle.web.sections.SectionInfo;
import com.tle.web.sections.SectionsController;
import com.tle.web.sections.equella.annotation.PlugKey;
import com.tle.web.sections.equella.annotation.PluginResourceHandler;
import com.tle.web.sections.render.Label;
import com.tle.web.selection.AbstractSelectionNavAction;
import com.tle.web.selection.SelectionSession;
import com.tle.web.selection.section.RootSelectionSection.Layout;
import javax.inject.Inject;
import javax.inject.Singleton;

@Bind
@Singleton
public class SearchSelectable extends AbstractSelectionNavAction {
  static {
    PluginResourceHandler.init(SearchSelectable.class);
  }

  @PlugKey("searching.search.title")
  private static Label LABEL_SEARCH;

  @Inject private SectionsController controller;

  @Override
  public SectionInfo createSectionInfo(SectionInfo info, SelectionSession session) {
    return getSearchTree(info);
  }

  @SuppressWarnings("nls")
  protected SectionInfo getSearchTree(SectionInfo info) {
    return controller.createForward(info, "/selectoradd/searching.do");
  }

  public void setController(SectionsController controller) {
    this.controller = controller;
  }

  @Override
  public Label getLabelForNavAction(SectionInfo info) {
    return LABEL_SEARCH;
  }

  @Override
  public SectionInfo createForwardForNavAction(SectionInfo fromInfo, SelectionSession session) {
    return getSearchTree(fromInfo);
  }

  @Override
  public boolean isActionAvailable(SectionInfo info, SelectionSession session) {
    if (!super.isActionAvailable(info, session)) {
      return false;
    }
    if (session.getLayout() != Layout.NORMAL) {
      return false;
    }
    return session.isAllCollections() || !Check.isEmpty(session.getCollectionUuids());
  }

  @Override
  public String getActionType() {
    return "search";
  }

  @Override
  public boolean isShowBreadcrumbs() {
    return false;
  }
}
