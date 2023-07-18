package praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class UserRegistrationTest {
    // создание объекта UserDataPattern для регистрации нового пользователя
    private final UserDataPattern user = UserDataPattern.getRandom();
    // сообщение об ошибке для пустых полей ввода
    private final String fieldInputErrorMessage = "Email, password and name are required fields";

    // метод createUserWithValidCredentials() создает пользователя с действительными учетными данными
    @Test
    @DisplayName("Создать пользователя с действительными учетными данными")
    public void createUserWithValidCredentials() {
        // выполнение запроса на регистрацию пользователя с использованием объекта UserDataPattern и получение ответа в виде ResponseSpecification
        var createResponse = UserApiPattern.registration(new UserDataPattern(user.getEmail(), user.getPassword(), user.getName()));

        // извлечение значений из ответа
        boolean isResponseSuccess = createResponse.extract().path("success");
        var actualStatusCode = createResponse.extract().statusCode();

        // удаление созданного пользователя
        UserApiPattern.deleteUserByUser(user);

        // проверка статус кода ответа, значения поля success с использованием методов класса Assert
        Assert.assertEquals("User is not created", actualStatusCode, SC_OK);
        Assert.assertTrue(isResponseSuccess);
    }

    // метод createUserWithSameCredentials() создает пользователя с уже существующими учетными данными
    @Test
    @DisplayName("Создание пользователя с одинаковыми учетными данными")
    public void createUserWithSameCredentials() {
        // сообщение об ошибке для пользователя с уже существующими учетными данными
        String sameUserErrorMessage = "User already exists";

        // регистрация первого пользователя
        UserApiPattern.registration(new UserDataPattern("test@test.ru", user.getPassword(), user.getName()));

        // выполнение запроса на регистрацию пользователя с уже существующими учетными данными и получение ответа в виде ResponseSpecification
        var createResponse = UserApiPattern.registration(new UserDataPattern("test@test.ru", user.getPassword(), user.getName()));

        // извлечение значений из ответа
        boolean isResponseSuccess = createResponse.extract().path("success");
        String responseMessage = createResponse.extract().path("message");
        var actualStatusCode = createResponse.extract().statusCode();

        // проверка статус кода ответа, значения поля message с использованием методов класса Assert
        Assert.assertEquals("User was created with empty password", actualStatusCode, SC_FORBIDDEN);
        Assert.assertFalse(isResponseSuccess);
        Assert.assertEquals("Message:" + sameUserErrorMessage + "is not displayed", responseMessage, sameUserErrorMessage);
    }

    // метод createUserWithEmptyEmail() создает пользователя с пустым адресом электронной почты
    @Test
    @DisplayName("Создать пользователя с пустым адресом электронной почты")
    public void createUserWithEmptyEmail() {
        // выполнение запроса на регистрацию пользователя с пустым адресом электронной почты и получение ответа в виде ResponseSpecification
        var createResponse = UserApiPattern.registration(new UserDataPattern("", user.getPassword(), user.getName()));

        // извлечение значений из ответа
        boolean isResponseSuccess = createResponse.extract().path("success");
        String responseMessage = createResponse.extract().path("message");
        var actualStatusCode = createResponse.extract().statusCode();

        // проверка статус кода ответа, значения поля message с использованием методов класса Assert
        Assert.assertEquals("User was created with empty email", actualStatusCode, SC_FORBIDDEN);
        Assert.assertFalse(isResponseSuccess);
        Assert.assertEquals("Message:" + fieldInputErrorMessage + "is not displayed", responseMessage, fieldInputErrorMessage);
    }

    // метод createUserWithEmptyPassword() создает пользователя с пустым паролем
    @Test
    @DisplayName("Создать пользователя с пустым паролем")
    public void createUserWithEmptyPassword() {
        // выполнение запроса на регистрацию пользователя с пустым паролем и получение ответа в виде ResponseSpecification
        var createResponse = UserApiPattern.registration(new UserDataPattern(user.getEmail(), "", user.getName()));

        // извлечение значений из ответа
        boolean isResponseSuccess = createResponse.extract().path("success");
        String responseMessage = createResponse.extract().path("message");
        int actualStatusCode = createResponse.extract().statusCode();

        // проверка статус кода ответа, значения поля message с использованием методов класса Assert
        Assert.assertEquals("User was created with empty password", actualStatusCode, SC_FORBIDDEN);
        Assert.assertFalse(isResponseSuccess);
        Assert.assertEquals("Message:" + fieldInputErrorMessage + "is not displayed", responseMessage, fieldInputErrorMessage);
    }

    // метод createUserWithEmptyName() создает пользователя с пустым именем
    @Test
    @DisplayName("Создать пользователя с пустым именем")
    public void createUserWithEmptyName() {
        // выполнение запроса на регистрацию пользователя с пустым именем и получение ответа в виде ResponseSpecification
        var createResponse = UserApiPattern.registration(new UserDataPattern(user.getEmail(), user.getPassword(), ""));

        // извлечение значений из ответа
        boolean isResponseSuccess = createResponse.extract().path("success");
        String responseMessage = createResponse.extract().path("message");
        int actualStatusCode = createResponse.extract().statusCode();

        // проверка статус кода ответа, значения поля message с использованием методов класса Assert
        Assert.assertEquals("User was created with empty name", actualStatusCode, SC_FORBIDDEN);
        Assert.assertFalse(isResponseSuccess);
        Assert.assertEquals("Message:" + fieldInputErrorMessage + "is not displayed", responseMessage, fieldInputErrorMessage);
    }
}