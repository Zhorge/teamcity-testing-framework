package com.example.teamcity;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class BaseTest {
  protected SoftAssert softy;
  protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserSpec());
  protected CheckedRequests userCheckedRequests;
  protected TestData testData;

  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    softy = new SoftAssert();
    testData = generate();
    userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    TestDataStorage.getStorage().deleteCreatedEntities();
    softy.assertAll();
  }
}
