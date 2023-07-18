package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

// класс OrderApiPattern является подклассом класса StellarBurgerRestAssuredPattern и представляет собой набор методов для выполнения автотестов, связанных с заказами

public class OrderApiPattern extends StellarBurgerRestAssuredPattern {

    // путь к ресурсу, который предоставляет информацию об ингредиентах
    public static final String INGREDIENTS_PATH = "api/ingredients";

    // путь к ресурсу, который предоставляет информацию о заказах
    private static final String ORDER_PATH = "/api/orders/";

    // метод для получения заказов пользователя с авторизацией
    @Step("Получить заказы пользователя с авторизацией")
    public ValidatableResponse getUserOrdersWithAuth(String token) {
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }

    // метод для получения заказов пользователя без авторизации
    @Step("Получить заказы пользователя без авторизации")
    public ValidatableResponse getUserOrdersWithoutAuth() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }

    // метод для создания заказа с авторизацией
    @Step("Создать заказ со авторизацией")
    public static ValidatableResponse createOrder(Ingredients order, String token) {
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .when()
                .body(order)
                .post(ORDER_PATH)
                .then().log().all();
    }

    // метод для получения информации об ингредиентах
    @Step("Получить информацию об ингредиентах")
    public static ValidatableResponse getIngredients() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then();
    }
}