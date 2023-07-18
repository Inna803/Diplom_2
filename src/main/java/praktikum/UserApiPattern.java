package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

// класс UserApiPattern является подклассом класса StellarBurgerRestAssuredPattern и представляет набор методов для выполнения автотестов, связанных с пользователями

public class UserApiPattern extends StellarBurgerRestAssuredPattern {

    // путь к ресурсу для регистрации пользователя
    private static final String REGISTRATION_PATH = "api/auth/register/";

    // путь к ресурсу для входа пользователя
    private static final String LOGIN_PATH = "api/auth/login/";

    // путь к ресурсу для изменения пользовательских данных
    private static final String CHANGE_USER_DATA_PATH = "api/auth/user/";

    // метод для регистрации пользователя
    @Step("Регистрация пользователя")
    public static ValidatableResponse registration(UserDataPattern user) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(user)
                .post(REGISTRATION_PATH)
                .then().log().all();
    }

    // метод для входа пользователя
    @Step("Логин пользователя")
    public static ValidatableResponse login(UserDataPattern user) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(user)
                .post(LOGIN_PATH)
                .then().log().all();
    }

    // метод для изменения пользовательских данных
    @Step("Изменение пользовательских данных")
    public static ValidatableResponse changeUserData(String token, UserDataPattern user) {
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .when()
                .body(user)
                .patch(CHANGE_USER_DATA_PATH)
                .then().log().all();
    }

    // метод для получения токена доступа
    @Step("Получить токен доступа")
    public static String getToken(UserDataPattern user) {
        return login(user).extract().path("accessToken");
    }

    // метод для удаления пользователя по токену
    @Step("Удалить пользователя по токену")
    public static void deleteUserByToken(String token) {
        given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .when()
                .delete(CHANGE_USER_DATA_PATH)
                .then().log().all()
                .assertThat().statusCode(202);
    }

    // метод для удаления пользователя по имени пользователя
    @Step("Удалить пользователя по имени пользователя")
    public static void deleteUserByUser(UserDataPattern name) {
        if (getToken(name) != null) {
            deleteUserByToken(getToken(name));
        }
    }
}