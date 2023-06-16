package praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.http.HttpStatus.*;

// класс CreateOrderTest содержит автотесты для создания заказов

public class CreateOrderTest {
    // создание экземпляра UserDataPattern для использования в тестах
    private final UserDataPattern user = UserDataPattern.getRandom();

    // список ингредиентов
    List<String> ingredients = new ArrayList<>();

    // метод для генерации случайного числа
    public int randomNumber() {
        Random rand = new Random();
        int upperbound = 10;
        return rand.nextInt(upperbound);
    }

    // метод tearDown() удаляет пользователя после выполнения теста
    @After
    public void tearDown() {
        UserApiPattern.deleteUserByUser(user);
    }

    // метод createValidOrder() тестирует создание заказа с входом в систему и действительными ингредиентами
    @Test
    @DisplayName("Создайте заказ с логином и действительными ингредиентами")
    public void createValidOrder() {
        // регистрация пользователя и получение токена доступа
        String accessToken = UserApiPattern.registration(user).extract().path("accessToken");

        // получение списка ингредиентов
        ingredients = OrderApiPattern.getIngredients().extract().path("data._id");

        // создание экземпляра Ingredients с случайно выбранным ингредиентом из списка
        Ingredients orderIngredients = new Ingredients(ingredients.get(randomNumber()));

        // создание заказа с использованием токена доступа и выбранных ингредиентов
        var createOrder = OrderApiPattern.createOrder(orderIngredients, accessToken);

        // получение фактического статус кода ответа
        int actualStatusCode = createOrder.extract().statusCode();

        // получение значения поля success из ответа
        boolean isResponseSuccess = createOrder.extract().path("success");

        // проверка соответствия фактического статус кода и значения поля success ожидаемым значениям
        Assert.assertEquals(actualStatusCode, SC_OK);
        Assert.assertTrue(isResponseSuccess);
    }

    // метод createOrderWithoutLogin() тестирует создание заказа без входа в систему
    @Test
    @DisplayName("Создать заказ без входа в систему")
    public void createOrderWithoutLogin() {
        // получение списка ингредиентов
        ingredients = OrderApiPattern.getIngredients().extract().path("data._id");

        // создание экземпляра Ingredients с случайно выбранным ингредиентом из списка
        Ingredients orderIngredients = new Ingredients(ingredients.get(randomNumber()));

        // создание заказа без использования токена доступа и выбранных ингредиентов
        var createOrder = OrderApiPattern.createOrder(orderIngredients, "");

        // получение фактического статус кода ответа
        int actualStatusCode = createOrder.extract().statusCode();

        // получение значения поля success из ответа
        boolean isResponseSuccess = createOrder.extract().path("success");

        // проверка соответствия фактического статус кода и значения поля success ожидаемым значениям
        Assert.assertEquals(actualStatusCode, SC_OK);
        Assert.assertTrue(isResponseSuccess);
    }

    // метод createOrderWithoutIngredients() тестирует создание заказа без указания ингредиентов
    @Test
    @DisplayName("Создайте заказ без ингредиентов")
    public void createOrderWithoutIngredients() {
        // регистрация пользователя и получение токена доступа
        String accessToken = UserApiPattern.registration(user).extract().path("accessToken");

        // создание экземпляра Ingredients без указания ингредиентов
        Ingredients orderIngredients = new Ingredients("");

        // создание заказа с использованием токена доступа и пустых ингредиентов
        var createOrder = OrderApiPattern.createOrder(orderIngredients, accessToken);

        // ожидаемое сообщение об ошибке при отсутствии ингредиентов
        String ingredientsErrorMessage = "Ingredient ids must be provided";

        // получение фактического статус кода ответа
        int actualStatusCode = createOrder.extract().statusCode();

        // получение значения поля success из ответа
        boolean isResponseSuccess = createOrder.extract().path("success");

        // получение сообщения об ошибке из ответа
        String responseMessage = createOrder.extract().path("message");

        // проверка соответствия фактического статус кода, значения поля success и сообщения об ошибке ожидаемым значениям
        Assert.assertEquals(actualStatusCode, SC_BAD_REQUEST);
        Assert.assertFalse(isResponseSuccess);
        Assert.assertEquals(responseMessage, ingredientsErrorMessage);
    }

    // метод createOrderWithInvalidIngredients() тестирует создание заказа с недопустимым ингредиентом
    @Test
    @DisplayName("Создать заказ с недопустимым ингредиентом")
    public void createOrderWithInvalidIngredients() {
        // регистрация пользователя и получение токена доступа
        String accessToken = UserApiPattern.registration(user).extract().path("accessToken");

        // создание экземпляра Ingredients с недопустимым ингредиентом
        Ingredients invalidIngredients = new Ingredients("space cat fur");

        // создание заказа с использованием токена доступа и недопустимых ингредиентов
        var createOrder = OrderApiPattern.createOrder(invalidIngredients, accessToken);

        // получение фактического статус кода ответа
        int actualStatusCode = createOrder.extract().statusCode();

        // проверка соответствия фактического статус кода ожидаемому значению
        Assert.assertEquals(actualStatusCode, SC_INTERNAL_SERVER_ERROR);
    }
}