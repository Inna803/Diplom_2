package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class UserLoginTest {

    // задание констант для электронной почты и пароля
    private final String EMAIL = "vfhujif2023@mail.ru";
    private final String PASSWORD = "EyAaAI3e1ru^";

    // создание объектов UserDataPattern для различных вариантов входа пользователя
    UserDataPattern login = new UserDataPattern(EMAIL, PASSWORD);
    UserDataPattern loginWithIncorrectPassword = new UserDataPattern(EMAIL, "123456");
    UserDataPattern loginWithIncorrectEmail = new UserDataPattern("vfhujif2023@mail.ruu", PASSWORD);

    // метод checkCorrectLogin() проверяет правильный вход пользователя с действительными учетными данными
    @Test
    @DisplayName("Правильный вход пользователя с действительными учетными данными")
    public void checkCorrectLogin() {
        // выполнение запроса на вход пользователя с использованием объекта UserDataPattern login и получение ответа в виде ResponseSpecification
        ValidatableResponse response = UserApiPattern.login(login);

        // проверка статус кода ответа, значения поля success с использованием методов класса Assert
        response.statusCode(200).and().assertThat().body("success", is(true));
    }

    // метод checkLoginWithIncorrectPassword() проверяет вход пользователя с неверным паролем
    @Test
    @DisplayName("Вход пользователя с неверным паролем")
    public void checkLoginWithIncorrectPassword() {
        // выполнение запроса на вход пользователя с использованием объекта UserDataPattern loginWithIncorrectPassword и получение ответа в виде ResponseSpecification
        ValidatableResponse response = UserApiPattern.login(loginWithIncorrectPassword);

        // проверка статус кода ответа, значения поля message с использованием методов класса Assert
        response.statusCode(401).and().assertThat().body("message", is("email or password are incorrect"));
    }

    // метод checkLoginWithIncorrectEmail() проверяет вход пользователя с неверным адресом электронной почты
    @Test
    @DisplayName("Вход пользователя с неверным адресом электронной почты")
    public void checkLoginWithIncorrectEmail() {
        // выполнение запроса на вход пользователя с использованием объекта UserDataPattern loginWithIncorrectEmail и получение ответа в виде ResponseSpecification
        ValidatableResponse response = UserApiPattern.login(loginWithIncorrectEmail);

        // проверка статус кода ответа, значения поля message с использованием методов класса Assert
        response.statusCode(401).and().assertThat().body("message", is("email or password are incorrect"));
    }
}