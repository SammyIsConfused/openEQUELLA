package com.tle.webtests.pageobject.tasklist;

import com.tle.webtests.framework.PageContext;
import com.tle.webtests.pageobject.WaitingPageObject;
import com.tle.webtests.pageobject.generic.component.EquellaSelect;
import com.tle.webtests.pageobject.generic.component.SelectUserDialog;
import com.tle.webtests.pageobject.searching.AbstractQueryableSearchPage;
import com.tle.webtests.pageobject.searching.ModerateListSearchResults;
import com.tle.webtests.pageobject.searching.ModerationSearchResult;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageTasksPage
    extends AbstractQueryableSearchPage<
        ManageTasksPage, ModerateListSearchResults, ModerationSearchResult> {
  private final String SEARCH_WITHIN_WORKFLOW_ID = "searchform-in";
  private final String TASK_FILTER_ID = "task";

  @FindBy(id = "sa_filterBox_minimise")
  private WebElement filterMinimise;

  @FindBy(id = "rf_resetButton")
  private WebElement clearFilters;

  @FindBy(id = "fbm_so_opener")
  private WebElement selectModerator;

  @FindBy(id = "fbm_r")
  private WebElement removeModerator;

  @FindBy(id = "moderator")
  private WebElement moderatorAjax;

  public ManageTasksPage(PageContext context) {
    super(context);
    loadedBy = byForPageTitle("Manage tasks");
  }

  @Override
  protected WebElement findLoadedElement() {
    return driver.findElement(loadedBy);
  }

  @Override
  protected void loadUrl() {
    driver.get(context.getBaseUrl() + "access/managetasks.do");
  }

  @Override
  public ModerateListSearchResults resultsPageObject() {
    return new ModerateListSearchResults(context);
  }

  public void searchWithinWorkflow(String workflow) {
    WaitingPageObject<ModerateListSearchResults> waiter = resultsPageObject.getUpdateWaiter();
    EquellaSelect resourceOptions =
        new EquellaSelect(context, getFilterControl(SEARCH_WITHIN_WORKFLOW_ID));
    resourceOptions.selectByValue(workflow);
    waitForResultsReload(waiter);
  }

  public void setFilter(String value) {
    WaitingPageObject<ModerateListSearchResults> waiter = resultsPageObject.getUpdateWaiter();
    EquellaSelect resourceOptions = new EquellaSelect(context, getFilterControl(TASK_FILTER_ID));
    resourceOptions.selectByValue(value);
    waitForResultsReload(waiter);
  }

  public ManageTasksPage clearFilters() {
    WaitingPageObject<ModerateListSearchResults> waiter = resultsPageObject.getUpdateWaiter();
    scrollToElement(clearFilters);
    clearFilters.click();
    return waitForResultsReload(waiter);
  }

  public ManageTasksPage selectModerator(String query, String username) {
    selectModerator.click();
    SelectUserDialog dialog = new SelectUserDialog(context, "fbm_so").get();
    dialog.search(query);
    return dialog.selectAndFinish(username, ajaxUpdate(moderatorAjax));
  }

  public ManageTasksPage clearModerator() {
    WaitingPageObject<ManageTasksPage> waiter = ajaxUpdate(moderatorAjax);
    removeModerator.click();
    return waiter.get();
  }

  @Override
  public ModerateListSearchResults exactQuery(String query) {
    return search('"' + query + '"');
  }
  //
  // public ModerateListSearchResults search(String query)
  // {
  // WaitingPageObject<ModerateListSearchResults> waiter =
  // getResultsUpdateWaiter();
  // setQuery(query);
  // searchButton.click();
  // return waiter.get();
  // }
}
