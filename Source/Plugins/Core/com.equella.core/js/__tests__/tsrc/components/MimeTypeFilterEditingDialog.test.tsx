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
import * as React from "react";

import { shallow } from "enzyme";
import { getMimeTypesFromServer } from "../../../__mocks__/MimeTypes.mock";
import MimeTypeFilterEditingDialog from "../../../tsrc/settings/Search/searchfilter/MimeTypeFilterEditingDialog";
import { MimeTypeFilter } from "../../../tsrc/modules/SearchFilterSettingsModule";

describe("<MimeTypeFilterEditingDialog />", () => {
  const onClose = jest.fn();
  const addOrUpdate = jest.fn();
  const handleError = jest.fn();
  const renderDialog = (filter: MimeTypeFilter | undefined = undefined) =>
    shallow(
      <MimeTypeFilterEditingDialog
        open
        onClose={onClose}
        addOrUpdate={addOrUpdate}
        mimeTypeFilter={filter}
        handleError={handleError}
        mimeTypeSupplier={jest.fn().mockResolvedValue(getMimeTypesFromServer)}
      />
    );

  describe("when filter is defined", () => {
    it("should display 'OK' as the Save button text", () => {
      const component = renderDialog({
        id: "testing ID",
        name: "image filter",
        mimeTypes: ["IMAGE/PNG", "IMAGE/JPEG"],
      });
      const saveButton = component.find("#MimeTypeFilterEditingDialog_save");
      expect(saveButton.text()).toBe("OK");
    });
  });

  describe("when filter is undefined", () => {
    it("should display 'Add' as the Save button text", () => {
      const component = renderDialog();
      const saveButton = component.find("#MimeTypeFilterEditingDialog_save");
      expect(saveButton.text()).toBe("Add");
    });
  });

  describe("when filter name is empty", () => {
    it("should disable the Save button", () => {
      const component = renderDialog();
      const saveButton = component.find("#MimeTypeFilterEditingDialog_save");
      expect(saveButton.is("[disabled]")).toBeTruthy();
    });
  });
});
