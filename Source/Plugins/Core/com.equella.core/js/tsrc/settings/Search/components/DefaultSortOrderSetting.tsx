import { FormLabel, Grid, MenuItem, Select } from "@material-ui/core";
import { SearchSettings, SortOrder } from "../SearchSettingsModule";
import * as React from "react";
import { languageStrings } from "../../../util/langstrings";

export interface DefaultSortOrderSettingProps {
  disabled: boolean;
  searchSettings: SearchSettings;
  setSearchSettings: (searchSettings: SearchSettings) => void;
}
export default function DefaultSortOrderSetting(
  props: DefaultSortOrderSettingProps
) {
  const { disabled, searchSettings, setSearchSettings } = props;
  const searchPageSettingsStrings =
    languageStrings.settings.searching.searchPageSettings;
  return (
    <Grid container direction={"column"} spacing={8}>
      <Grid item>
        <FormLabel>{searchPageSettingsStrings.defaultSortOrder}</FormLabel>
      </Grid>
      <Grid item>
        <Select
          SelectDisplayProps={{ id: "_sortOrder" }}
          disabled={disabled}
          onChange={event =>
            setSearchSettings({
              ...searchSettings,
              defaultSearchSort: event.target.value as SortOrder
            })
          }
          variant={"standard"}
          value={searchSettings.defaultSearchSort}
        >
          <MenuItem value={SortOrder.RANK}>
            {searchPageSettingsStrings.relevance}
          </MenuItem>
          <MenuItem value={SortOrder.DATEMODIFIED}>
            {searchPageSettingsStrings.lastModified}
          </MenuItem>
          <MenuItem value={SortOrder.DATECREATED}>
            {searchPageSettingsStrings.dateCreated}
          </MenuItem>
          <MenuItem value={SortOrder.NAME}>
            {searchPageSettingsStrings.title}
          </MenuItem>
          <MenuItem value={SortOrder.RATING}>
            {searchPageSettingsStrings.userRating}
          </MenuItem>
        </Select>
      </Grid>
    </Grid>
  );
}