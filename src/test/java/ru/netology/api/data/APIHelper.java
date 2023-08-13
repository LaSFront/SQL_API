package ru.netology.api.data;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static ru.netology.api.data.DataHelper.*;


public class APIHelper {

    private static RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri("http://localhost:9999/api")
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    {
        RestAssured.defaultParser = Parser.JSON;
    }

    private APIHelper() {
    }

    public static void authentication(DataHelper.AuthInfo info, Integer statusCode) {
        given()
                .spec(specification)
                .body("{'login': " + info.getLogin() + ", 'password': " + info.getPassword() + "}")
                .when()
                .post("/auth")
                .then()
                .statusCode(statusCode)
        ;
    }

    public static String verification(DataHelper.VerifyInfo info, Integer statusCode) {

        return given()
                .spec(specification)
                .body("{'login': " + info.getLogin() + ", 'code': " + info.getCode() + "}")
                .when()
                .post("/auth/verification")
                .then()
                .statusCode(statusCode)
                .extract()
                .path("token")
                ;
    }

    public static CardInfo[] getCards(String token) {
        specification.header("Authorization", "Bearer " + token);
        RestAssured.defaultParser = Parser.JSON;
        return given()
                .spec(specification)
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .extract()
                .response().getBody().as(CardInfo[].class);
    }

    public static void transaction(String token, Integer statusCode) {
        given()
                .spec(specification)
                .header("Authorization", "Bearer " + token)
                .body(transfer(token))
                .when()
                .post("/transfer")
                .then()
                .statusCode(statusCode)
        ;
    }

    public static void transactionInvalid(String token, Integer statusCode) {
        given()
                .spec(specification)
                .header("Authorization", "Bearer " + token)
                .body(transferInvalid(token))
                .when()
                .post("/transfer")
                .then()
                .statusCode(statusCode)
        ;
    }


}
