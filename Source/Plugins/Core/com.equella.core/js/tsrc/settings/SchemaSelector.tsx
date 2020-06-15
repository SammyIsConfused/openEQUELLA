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
import React from "react";
import { Grid, InputLabel, MenuItem, Select } from "@material-ui/core";
import {
  schemaListSummary,
  SchemaNode,
  schemaTree,
} from "../schema/SchemaModule";
import SchemaNodeSelector from "../settings/SchemaNodeSelector";

interface SchemaSelectorProps {
  setSchemaNode: (node: string) => void;
}

/**
 * This component defines a schema selector, for selecting a schema and then a node within.
 * When a schema is selected, it will display that schema within a SchemaNodeSelector.
 */
export default function SchemaSelector({ setSchemaNode }: SchemaSelectorProps) {
  const [selectedSchema, setSelectedSchema] = React.useState<
    string | undefined
  >(undefined);
  const [schema, setSchema] = React.useState<SchemaNode>();
  const [schemaList, setSchemaList] = React.useState<JSX.Element[]>([]);
  React.useEffect(() => {
    schemaListSummary().then((schemas) => {
      const elementList: JSX.Element[] = [
        <MenuItem value={undefined}>Select a schema...</MenuItem>,
      ];
      for (const [uuid, name] of schemas) {
        elementList.push(
          <MenuItem id={uuid} value={uuid}>
            {name}
          </MenuItem>
        );
      }
      setSchemaList(elementList);
    });
  }, []);

  React.useEffect(() => {
    if (selectedSchema && selectedSchema !== "") {
      schemaTree(selectedSchema).then((tree) => setSchema(tree));
    } else {
      setSchema(undefined);
      setSchemaNode("");
    }
  }, [selectedSchema]);

  return (
    <Grid container direction={"column"} spacing={1}>
      <Grid item>
        {schemaList && (
          <>
            <Select
              style={{ width: "620px" }}
              fullWidth
              label={
                <InputLabel shrink id="select-label">
                  Schema
                </InputLabel>
              }
              value={selectedSchema}
              displayEmpty
              onChange={(event) => {
                setSelectedSchema(event.target.value as string | undefined);
              }}
            >
              {schemaList}
            </Select>
          </>
        )}
      </Grid>
      <Grid item>
        {schema && (
          <SchemaNodeSelector tree={schema} setSelectedNode={setSchemaNode} />
        )}
      </Grid>
    </Grid>
  );
}