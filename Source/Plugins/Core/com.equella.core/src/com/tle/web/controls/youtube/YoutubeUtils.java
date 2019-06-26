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

package com.tle.web.controls.youtube;

import java.time.Duration;

@SuppressWarnings("nls")
public final class YoutubeUtils {
  public static final String ATTACHMENT_TYPE = "youtube";
  public static final String MIME_TYPE = "equella/attachment-youtube";

  public static final String PROPERTY_ID = "videoId";
  public static final String PROPERTY_TITLE = "title";
  public static final String PROPERTY_AUTHOR = "uploader";
  public static final String PROPERTY_DESCRIPTION = "description";
  public static final String PROPERTY_DATE = "uploaded";
  public static final String PROPERTY_PLAY_URL = "playUrl";
  public static final String PROPERTY_THUMB_URL = "thumbUrl";

  @Deprecated public static final String PROPERTY_TAGS = "tags";

  // ISO-8601 Duration string
  public static final String PROPERTY_DURATION = "duration";
  public static final String PROPERTY_PARAMETERS = "custom_parameters";

  // YouTube API V2 returned duration in seconds
  @Deprecated
  public static String formatDuration(long duration) {
    return formatDuration(Duration.ofSeconds(duration).toString());
  }

  public static String formatDuration(String duration) {
    Duration d = Duration.parse(duration);
    long hours = d.toHours();
    Duration minusHours = d.minus(Duration.ofHours(hours));
    long minutes = minusHours.toMinutes();
    long seconds = minusHours.minus(Duration.ofMinutes(minutes)).getSeconds();
    String format = hours > 0 ? "%3$d:%2$02d:%1$02d" : "%2$d:%1$02d";
    return String.format(format, seconds, minutes, hours);
  }

  private YoutubeUtils() {
    throw new Error();
  }
}
