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

package com.tle.core.item.standard.service;

import com.tle.beans.item.Comment;
import com.tle.beans.item.Item;
import com.tle.beans.item.ItemKey;
import com.tle.beans.item.ItemPack;
import java.util.EnumSet;
import java.util.List;

public interface ItemCommentService {
  enum CommentFilter {
    MUST_HAVE_COMMENT,
    MUST_HAVE_RATING,
    NOT_ANONYMOUS_OR_GUEST,
    ONLY_MOST_RECENT_PER_USER
  }

  enum CommentOrder {
    REVERSE_CHRONOLOGICAL,
    CHRONOLOGICAL,
    HIGHEST_RATED,
    LOWEST_RATED
  }

  float getAverageRatingForItem(ItemKey itemId);

  /** Retrieve a single comment for an item by ID. */
  Comment getComment(Item item, String commentUuid);

  /**
   * Retrieve several comments for an item.
   *
   * @param filter EnumSet filters to apply. Null or an empty set indicates no filtering.
   * @param order null defaults to REVERSE_CHRONOLOGICAL
   * @param limit Integer indicating the maximum number of comments to return. A value of zero or
   *     less will return all comments.
   */
  List<Comment> getComments(
      Item item, EnumSet<CommentFilter> filter, CommentOrder order, int limit);

  /**
   * Get the item first.
   *
   * @param itemId
   * @param filter
   * @param order
   * @param limit
   * @return
   */
  List<Comment> getComments(
      ItemKey itemId, EnumSet<CommentFilter> filter, CommentOrder order, int limit);

  /**
   * Check the permission of viewing an item's comments and return a list of comments if the
   * permission is granted.
   */
  List<Comment> getCommentsWithACLCheck(
      ItemKey itemId, EnumSet<CommentFilter> filter, CommentOrder order, int limit);

  /**
   * Check the permission of viewing an item's comments and return the number of comments if the
   * permission is granted.
   */
  Integer getCommentCountWithACLCheck(ItemKey itemId);

  /**
   * Add a new comment to an item.
   *
   * @param commentText Comment text to add. Blank if only a rating.
   * @param rating Integer where 0 indicates no rating, other wise between 1 and 5 inclusive.
   * @param anonymous true if the identify of the comment author should be hidden when comments are
   *     being viewed. The author user ID is still recorded for auditing purposes.
   */
  ItemPack<Item> addComment(ItemKey itemId, String commentText, int rating, boolean anonymous);

  /**
   * Add a new comment to an item.
   *
   * @param commentText Comment text to add. Blank if only a rating.
   * @param rating Integer where 0 indicates no rating, other wise between 1 and 5 inclusive.
   * @param anonymous true if the identify of the comment author should be hidden when comments are
   *     being viewed. The author user ID is still recorded for auditing purposes.
   * @param userId The userId for the author of the comment. If blank current authenticated user
   *     will be used.
   */
  ItemPack<Item> addComment(
      ItemKey itemId, String commentText, int rating, boolean anonymous, String userId);

  /** Delete a single comment for an item by ID. */
  void deleteComment(ItemKey itemId, String commentUuid);
}
