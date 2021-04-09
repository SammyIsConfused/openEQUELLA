package testng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import testng.annotation.RetryTest;

public class FailureRetryAnalyzer implements IRetryAnalyzer {
  int currentRetry = 0;
  Logger LOGGER = LoggerFactory.getLogger(FailureRetryAnalyzer.class.getName());

  @Override
  public boolean retry(ITestResult result) {
    RetryTest failureRetryCount =
        result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(RetryTest.class);
    int maxRetryCount = (failureRetryCount == null) ? 0 : failureRetryCount.value();
    if (++currentRetry > maxRetryCount) {
      return false;
    } else {
      logRetryInfo(result, maxRetryCount);
      return true;
    }
  }

  private void logRetryInfo(ITestResult result, int maxRetryCount) {
    // print failure stack trace
    LOGGER.debug("Stack trace of failure to be retried:", result.getThrowable());
    LOGGER.warn(
        String.format(
            "Running retry %d/%d for test '%s' in class %s",
            currentRetry,
            maxRetryCount,
            result.getName(),
            result.getTestClass().getRealClass().getSimpleName()));
  }
}
