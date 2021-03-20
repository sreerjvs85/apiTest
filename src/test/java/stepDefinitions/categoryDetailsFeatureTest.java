package stepDefinitions;

import AssuirtyAPIs.apiList;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class categoryDetailsFeatureTest {

    Map<String,String> requestParams = new HashMap<String,String>();
    String CategoryDetailsEndPoint = apiList.ApiList.CategoryDetails;
    Response response;
    String genericKey;

    @Given("User has access to category details end point with parameter as {string} equals {string}")
    public void UserHasAccessToCategoryDetailsEndPointWithParameterAsCatalogueEqualsFalse(String key, String value){
        requestParams.put(key, value);
    }

    @When("User hits the endpoint and gets a response")
    public void UserHitsTheEndpointAndGetsAResponse(){
        RestAssured.baseURI= apiList.ApiList.BaseURL;
        RequestSpecification requestSpecification = RestAssured.given();
        response=requestSpecification.queryParams(requestParams).get(CategoryDetailsEndPoint);
        System.out.println(response.toString());
    }

    @Then("Validates {string} key for value {string}")
    public void ValidatesStringKeyForValueString(String key, String value) {
        genericKey=response.jsonPath().getString(key);
        AssertionsForClass(genericKey,value);
    }

    @Then("Validates {string} key for another value {string}")
    public void ValidatesStringKeyForAnotherValueString(String key, String value) {
        ValidatesStringKeyForValueString(key, value);
    }

    @And("Validates promotion item's {string} {string} has a {string} {string}")
    public void ValidatesPromotionItemsHasADescription(String key, String value, String key1, String value1) {
        List<String> promotionItemNames = response.jsonPath().getList("Promotions."+key);

        int id = promotionItemNames.indexOf(value);
        if (id>0) {
            Map<String, String> finalObject = response.jsonPath().getMap("Promotions[" + id + "]");
            genericKey= finalObject.get(key);
            AssertionsForClass(genericKey, value);
            genericKey=finalObject.get(key1);
            AssertionsForClass(genericKey, value1);

        } else {
            Assert.fail("Searched term does not exist");
        }
    }

    public void AssertionsForClass(String key, String value) {
        try {
            Assert.assertEquals(key, value);
        } catch (Throwable throwable) {
            Assert.fail("Expected value: " + value + " but actual value: " + key);
        }
    }

}
