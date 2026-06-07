package tests.sportactivity;

import base.BaseTest;
import body.sportactivity.SportActivityBody;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.TokenHelper;
import utils.Utils;

import static org.hamcrest.Matchers.*;

public class SportActivityTest extends BaseTest {
    private String activityId;

    // Create Sport Activity
    @Test
    public void createSportActivity(){
        SportActivityBody sportActivityBody = new SportActivityBody();
        String token = TokenHelper.getToken();
        
        String title = Utils.getActivityTitle();
        String description = Utils.getActivityDescription();
        String activityDate = Utils.getActivityDate();
        
        String sportCategoryId = ConfigReader.getProperty("categoryId");
        Assert.assertNotNull(sportCategoryId, "Category ID should be saved in config.properties");

        Response response = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "multipart/form-data")
                .body(sportActivityBody.createSportActivityData(
                        sportCategoryId,
                        3172,
                        title,
                        description,
                        9,
                        70000,
                        "Lapangan Revo, Jakarta Timur",
                        activityDate,
                        "09:00",
                        "10:00",
                        "https://maps.app.goo.gl/h1AV4bfB2cojJMxK7"
                ).toString())
                .when()
                .post("v1/sport-activities/create")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("error", equalTo(false))
                .body("result", notNullValue())
                .body("result.id", notNullValue())
                .extract().response();

        System.out.println("Create Response: " + response.asString());

        // Assert
        activityId = response.jsonPath().getString("result.id");
        Assert.assertNotNull(activityId,"Activity ID should not be null");
        ConfigReader.setProperty("activityId", activityId);
        System.out.println("Created Activity ID: " + activityId);
    }

    // Read Sport Activity By ID
    @Test(dependsOnMethods = "createSportActivity")
    public void getSportActivityById(){
        String token = TokenHelper.getToken();
        String savedActivityId = ConfigReader.getProperty("activityId");

        Assert.assertNotNull(savedActivityId, "Activity ID should be saved in config.properties");

        Response response = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get("v1/sport-activities/" + savedActivityId)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("error", equalTo(false))
                .body("result", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/sport-activity-schema.json"))
                .extract().response();

        System.out.println("Get Response: " + response.asString());
    }

    // Update Sport Activity
    @Test(dependsOnMethods = "getSportActivityById")
    public void updateSportActivity(){
        SportActivityBody sportActivityBody = new SportActivityBody();
        String token = TokenHelper.getToken();
        
        String title = Utils.getActivityTitle();
        String description = Utils.getActivityDescription();
        String activityDate = Utils.getUpdatedActivityDate();
        String savedActivityId = ConfigReader.getProperty("activityId");
        String sportCategoryId = ConfigReader.getProperty("categoryId");

        Assert.assertNotNull(savedActivityId, "Activity ID should be saved in config.properties");
        Assert.assertNotNull(sportCategoryId, "Category ID should be saved in config.properties");

        Response response = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(sportActivityBody.updateSportActivityData(
                        sportCategoryId,
                        3172,
                        title,
                        description,
                        12,
                        80000,
                        "Lapangan Revo, Jakarta Timur",
                        activityDate,
                        "10:00",
                        "11:00",
                        "https://maps.app.goo.gl/h1AV4bfB2cojJMxK7"
                ).toString())
                .when()
                .post("v1/sport-activities/update/" + savedActivityId)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("error", equalTo(false))
                .extract().response();

        System.out.println("Update Response: " + response.asString());

        // Assert
        activityId = response.jsonPath().getString("result.id");
        Assert.assertNotNull(activityId,"Activity ID should not be null");
        ConfigReader.setProperty("activityId", activityId);
        System.out.println("Updated Activity ID: " + activityId);
    }

    // Delete Sport Activity
    @Test(dependsOnMethods = "updateSportActivity")
    public void deleteSportActivity(){
        String token = TokenHelper.getToken();
        String savedActivityId = ConfigReader.getProperty("activityId");

        Assert.assertNotNull(savedActivityId, "Activity ID should be saved in config.properties");

        Response response = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type", "application/json")
                .when()
                .delete("v1/sport-activities/delete/" + savedActivityId)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("error", equalTo(false))
                .extract().response();

        System.out.println("Delete Response: " + response.asString());
    }
}
