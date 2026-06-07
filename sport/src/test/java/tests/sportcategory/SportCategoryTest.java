package tests.sportcategory;

import base.BaseTest;
import body.sportcategory.SportCategoryBody;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.TokenHelper;
import utils.Utils;

import static org.hamcrest.Matchers.*;

public class SportCategoryTest extends BaseTest {
    //Tambahkan Sportcategorytest
    //Utils (random data)
    //token helper (membantu mengambil token)
    private String categoryId;

    //Get Token -> ambil dari folder src/resources/json/token.json
    //Create
    @Test
    public void createSportCategories(){
        SportCategoryBody sportCategoryBody = new SportCategoryBody();
        String token = TokenHelper.getToken();
        String randomName = Utils.getCategoryName();

        //Ngehit endpoint
        Response response = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type", "application/json")
                .body(sportCategoryBody.createSportCategoryData(randomName).toString())
                .when()
                .post("v1/sport-categories/create")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("error", equalTo(false))
                .extract().response();

        System.out.println("Create Response: " + response.asString());

        //Assert
        //Get Category from response
        categoryId = response.jsonPath().getString("result.id");
        Assert.assertNotNull(categoryId,"Category ID should not be null");
        ConfigReader.setProperty("categoryId", categoryId);
        System.out.println("Created Category ID: " + categoryId);
    }
    //Read
    @Test
    public void getSportCategories(){
        String token = TokenHelper.getToken();
//curl --location 'https://sport-reservation-api-bootcamp.do.dibimbing.id/api/v1/sport-categories?is_paginate=false&per_page=&page=' \
        Response response = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type", "application/json")
                .queryParam("is_paginate","false")
                .queryParam("per_page","")
                .queryParam("page","")
                .when()
                .get("v1/sport-categories")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("error", equalTo(false))
                .body("result", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/sport-category-schema.json"))
                .extract().response();

        System.out.println("Get Response: " + response.asString());
    }


    //Update
    @Test(dependsOnMethods = "createSportCategories")
    public void updateSportCategory(){
        SportCategoryBody sportCategoryBody = new SportCategoryBody();
        String token = TokenHelper.getToken();
        String databaru = Utils.getCategoryName();
        String savedCategoryId = ConfigReader.getProperty("categoryId");

        Assert.assertNotNull(savedCategoryId, "Category ID should be saved in config.properties");

        Response response = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type", "application/json")
                .body(sportCategoryBody.createSportCategoryData(databaru).toString())
                .when()
                .post("v1/sport-categories/update/" + savedCategoryId)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("error", equalTo(false))
                .extract().response();

        System.out.println("Update Response: " + response.asString());

        //Assert
        //Get Category from response
        categoryId = response.jsonPath().getString("result.id");
        Assert.assertNotNull(categoryId,"Category ID should not be null");
        ConfigReader.setProperty("categoryId", categoryId);
        System.out.println("Updated Category ID: " + categoryId);
    }


    //Delete
    @Test(dependsOnMethods = "updateSportCategory")
    public void deleteSportCategory(){
        String token = TokenHelper.getToken();
        String savedCategoryId = ConfigReader.getProperty("categoryId");

        Assert.assertNotNull(savedCategoryId, "Category ID should be saved in config.properties");

        Response response = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type", "application/json")
                .when()
                .delete("v1/sport-categories/delete/" + savedCategoryId)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("error", equalTo(false))
                .extract().response();

        System.out.println("Get Response: " + response.asString());
    }
}
