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
import * as OEQ from "@openequella/rest-api-client";
import { API_BASE_URL } from "../AppConfig";
import { listEntities } from "./OEQHelpers";

/**
 * Provides a simple Map<string,string> summary of available collections, where K is the UUID
 * and V is the collections's name.
 *
 * On failure, an OEQ.Errors.ApiError will be thrown.
 */
export const collectionListSummary = (
  requiredPrivileges?: string[]
): Promise<Collection[]> => {
  const getCollections = (resumptionToken: string) =>
    OEQ.Collection.listCollections(API_BASE_URL, {
      privilege: requiredPrivileges,
      resumption: resumptionToken,
    });
  return listEntities<OEQ.Common.BaseEntity>(getCollections).then(
    (results) => results
  );
};
/**
 * A simplified type of Collection used when only collection uuid and name are required.
 */
export interface Collection {
  /**
   * Collection's uuid.
   */
  uuid: string;
  /**
   * Collection's name.
   */
  name: string;
}
