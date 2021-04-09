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

package com.tle.core.taxonomy.datasource.internal;

import com.tle.annotation.Nullable;
import com.tle.common.Pair;
import com.tle.common.beans.exception.InvalidDataException;
import com.tle.common.taxonomy.SelectionRestriction;
import com.tle.common.taxonomy.Taxonomy;
import com.tle.common.taxonomy.TaxonomyConstants;
import com.tle.core.taxonomy.TermResult;
import com.tle.core.taxonomy.TermService;
import com.tle.core.taxonomy.datasource.TaxonomyDataSource;
import java.util.List;
import java.util.Map;

public class InternalTaxonomyDataSource implements TaxonomyDataSource {
  private final Taxonomy taxonomy;
  private final TermService termService;
  private final boolean supportsTermAddition;

  InternalTaxonomyDataSource(Taxonomy taxonomy, TermService termService) {
    this.taxonomy = taxonomy;
    this.termService = termService;
    this.supportsTermAddition = taxonomy.getAttribute(TaxonomyConstants.TERM_ALLOW_ADDITION, false);
  }

  @Override
  public TermResult getTerm(String fullTermPath) {
    return termService.getTermResult(taxonomy, fullTermPath);
  }

  @Override
  public List<TermResult> getChildTerms(String parentFullPath) {
    return termService.listTermResults(taxonomy, parentFullPath);
  }

  @Override
  public String getDataForTerm(String fullTermPath, String key) {
    return termService.getData(taxonomy, fullTermPath, key);
  }

  @Override
  public Pair<Long, List<TermResult>> searchTerms(
      String query, SelectionRestriction restriction, int limit, boolean searchFullTerm) {
    return termService.searchTerms(taxonomy, query, restriction, limit, searchFullTerm);
  }

  @Override
  public TermResult addTerm(
      String parentFullPath, @Nullable String termUuid, String termValue, boolean createHierarchy) {
    if (supportsTermAddition()) {
      return termService.addTerm(taxonomy, parentFullPath, termUuid, termValue, createHierarchy);
    }
    throw new UnsupportedOperationException();
  }

  @Override
  public void validateTerm(String parentFullTermPath, String termValue, boolean requireParent)
      throws InvalidDataException {
    termService.validateTerm(taxonomy, parentFullTermPath, termValue, requireParent);
  }

  @Override
  public boolean supportsTermAddition() {
    return supportsTermAddition;
  }

  @Override
  public boolean supportsTermBrowsing() {
    return true;
  }

  @Override
  public boolean supportsTermSearching() {
    return true;
  }

  @Override
  public boolean isReadonly() {
    return false;
  }

  @Override
  public TermResult getTermByUuid(String termUuid) {
    return termService.getTermResultByUuid(taxonomy, termUuid);
  }

  @Override
  public String getDataByTermUuid(String termUuid, String dataKey) {
    return termService.getDataByTermUuid(taxonomy, termUuid, dataKey);
  }

  @Override
  public Map<String, String> getAllDataByTermUuid(String termUuid) {
    return termService.getAllDataByTermUuid(taxonomy, termUuid);
  }
}
