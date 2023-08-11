package ru.netology.api.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class APIHelper {

    private static RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri("http://localhost:9999/api")
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .setContentType("application/json")
            .log(LogDetail.ALL)
            .build();

    DataHelper.CardInfo[] cards;
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

    public static  String verification(DataHelper.VerifyInfo info, Integer statusCode) {

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

    public static void transaction(String token, String cardFrom, String cardTo, String amountTransfer, Integer statusCode) {
        given()

                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token,
                        "Content-Type", "application/json")
                .body("{\n" +
                        " \"from\": " + "\"" + cardFrom + "\"" + ",\n" +
                        " \"to\": " + "\"" + cardTo + "\"" + ",\n" +
                        " \"amount\": " + "\"" + amountTransfer + "\"" + ",\n" +
                        "}")
                .when()
                .post("/transfer")
                .then()
                .statusCode(statusCode)
        ;
    }

    public static DataHelper.CardInfo[] getCards(String token) {
        specification.header("Authorization", "Bearer" + token);
        return given()
                        .spec(specification)
                        .when()
                        .get("/cards")
                        .then()
                        //.statusCode(200)
                        .extract()
                        .response().getBody().as(DataHelper.CardInfo[].class)
                ;
    }


}
