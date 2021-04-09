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

package com.tle.core.hibernate.equella.service;

import com.tle.common.DoNotClone;
import com.tle.common.DoNotSimplify;
import java.lang.annotation.Annotation;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public abstract class Property {
  private Boolean isCascade;
  private Boolean isManyToMany;
  private Boolean isManyToOne;
  private Boolean isDoNotSimplify;
  private Boolean isDoNotClone;

  public Property() {
    super();
  }

  public boolean isCascade() {
    if (isCascade == null) {
      isCascade = isCascadeAll();
    }
    return isCascade;
  }

  public boolean isManyToMany() {
    if (isManyToMany == null) {
      isManyToMany = getAnnotation(ManyToMany.class) != null;
    }
    return isManyToMany;
  }

  public boolean isManyToOne() {
    if (isManyToOne == null) {
      isManyToOne = getAnnotation(ManyToOne.class) != null;
    }
    return isManyToOne;
  }

  public boolean isDoNotSimplify() {
    if (isDoNotSimplify == null) {
      isDoNotSimplify = getAnnotation(DoNotSimplify.class) != null;
    }
    return isDoNotSimplify;
  }

  public boolean isDoNotClone() {
    if (isDoNotClone == null) {
      isDoNotClone = getAnnotation(DoNotClone.class) != null;
    }
    return isDoNotClone;
  }

  private boolean isCascadeAll() {
    OneToMany onetomany = getAnnotation(OneToMany.class);
    if (onetomany != null) {
      return hasCascadeAll(onetomany.cascade());
    }
    OneToOne onetoone = getAnnotation(OneToOne.class);
    if (onetoone != null) {
      return hasCascadeAll(onetoone.cascade());
    }
    ManyToOne manyToOne = getAnnotation(ManyToOne.class);
    if (manyToOne != null) {
      return hasCascadeAll(manyToOne.cascade());
    }
    // ElementCollection is a 'default' cascade all
    if (getAnnotation(ElementCollection.class) != null) {
      return true;
    }

    return false;
  }

  private boolean hasCascadeAll(CascadeType[] cascades) {
    for (CascadeType type : cascades) {
      if (CascadeType.ALL == type || CascadeType.REMOVE == type) {
        return true;
      }
    }
    return false;
  }

  public abstract Class<?> getReturnType();

  abstract <T extends Annotation> T getAnnotation(Class<T> name);

  public abstract Object get(Object object);

  public abstract void set(Object object, Object newObject);

  public abstract boolean isAnnotationPresent(Class<? extends Annotation> name);
}
