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

package com.tle.core.settings.dao.impl;

import com.tle.beans.ConfigurationProperty;
import com.tle.beans.ConfigurationProperty.PropertyKey;
import com.tle.common.institution.CurrentInstitution;
import com.tle.core.guice.Bind;
import com.tle.core.hibernate.dao.GenericDaoImpl;
import com.tle.core.settings.dao.ConfigurationDao;
import java.util.Collection;
import javax.inject.Singleton;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@SuppressWarnings("nls")
@Bind(ConfigurationDao.class)
public class ConfigurationDaoImpl extends GenericDaoImpl<ConfigurationProperty, PropertyKey>
    implements ConfigurationDao {
  public ConfigurationDaoImpl() {
    super(ConfigurationProperty.class);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteAll() {
    getHibernateTemplate()
        .deleteAll(
            getHibernateTemplate()
                .findByNamedParam(
                    "from ConfigurationProperty where key.institutionId = :institution",
                    "institution",
                    CurrentInstitution.get().getDatabaseId()));
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public synchronized void deletePropertiesLike(Collection<String> select) {
    final StringBuilder buffer = new StringBuilder();
    buffer.append("from ConfigurationProperty where (");
    int length = select.size();
    int paramCounter = 0;
    Object[] values = select.toArray(new Object[length + 1]);
    for (int i = 0; i < length; i++) {
      if (i > 0) {
        buffer.append(" or ");
      }
      values[i] = values[i].toString() + '%';
      buffer.append("key.property like ?").append(paramCounter++);
    }
    buffer.append(")");

    buffer.append(" and key.institutionId = ?").append(paramCounter++);
    values[length] = CurrentInstitution.get().getDatabaseId();

    Collection<?> col = getHibernateTemplate().find(buffer.toString(), values);
    getHibernateTemplate().deleteAll(col);

    // If you don't flush then you get a NonUniqueObjectException
    getHibernateTemplate().flush();
  }
}
