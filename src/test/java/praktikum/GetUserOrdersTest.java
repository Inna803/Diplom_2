package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

// класс GetUserOrdersTest содержит автотесты для получения заказов пользователей

public class GetUserOrdersTest {
    // константы для использования в тестах
    private final String EMAIL = "vfhujif2023@mail.ru";
    private final String PASSWORD = "EyAaAI3e1ru^";

    // создание экземпляра UserDataPattern с данными для авторизации
    UserDataPattern loginForToken = new UserDataPattern(EMAIL, PASSWORD);

    // создание экземпляра OrderApiPattern для вызова методов API заказов
    OrderApiPattern orderApi;

    // аннотация @Before указывает, что данный метод будет выполняться перед каждым тестовым методом
    // метод setUp() инициализирует экземпляр OrderApiPattern
    @Before
    public void setUp() {
        orderApi = new OrderApiPattern();
    }

    // аннотация @Test указывает, что данный метод является тестовым методом
    // метод getUserOrdersBeingAuthorized() тестирует получение заказов при авторизации пользователя
    @Test
    @DisplayName("Получение заказов при авторизации пользователя")
    public void getUserOrdersBeingAuthorized() {
        // получение токена доступа путем авторизации пользователя
        String token = UserApiPattern.login(loginForToken).extract().path("accessToken");

        // вызов метода API для получения заказов с авторизацией и получение ответа в виде ValidatableResponse
        ValidatableResponse response = orderApi.getUserOrdersWithAuth(token);

        // проверка статус кода ответа, значения поля success с использованием Hamcrest-сопоставления
        response.statusCode(200).and().assertThat().body("success", is(true));
    }

    // метод getUserOrdersWithoutAuthorization() тестирует получение заказов без авторизации пользователя
    @Test
    @DisplayName("Получение заказов без авторизации пользователя")
    public void getUserOrdersWithoutAuthorization() {
        // вызов метода API для получения заказов без авторизации и получение ответа в виде ValidatableResponse
        ValidatableResponse response = orderApi.getUserOrdersWithoutAuth();

        // проверка статус кода ответа, значения поля message с использованием Hamcrest-сопоставления
        response.statusCode(401).and().assertThat().body("message", is("You should be authorised"));
    }
}