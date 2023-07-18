package praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

// класс UpdateUserDataTest содержит автотесты для изменения данных пользователя

public class UpdateUserDataTest {
    // создание экземпляра UserDataPattern с случайными данными пользователя
    private final UserDataPattern user = UserDataPattern.getRandom();

    // метод setUp() регистрирует пользователя перед выполнением тестов
    @Before
    public void setUp() {
        UserApiPattern.registration(user);
    }

    // метод tearDown() удаляет зарегистрированного пользователя после выполнения тестов
    @After
    public void tearDown() {
        UserApiPattern.deleteUserByUser(user);
    }

    // метод changeUserEmail() тестирует изменение электронной почты пользователя с авторизацией
    @Test
    @DisplayName("Изменение электронной почты пользователя с авторизацией")
    public void changeUserEmail() {
        // генерация нового случайного адреса электронной почты
        String userEmail = UserDataPattern.getRandomEmail();

        // создание нового объекта UserDataPattern с новым адресом электронной почты
        UserDataPattern user = new UserDataPattern(userEmail, this.user.getPassword(), this.user.getName());

        // изменение данных пользователя с использованием авторизации и получение ответа в виде ResponseSpecification
        var changeResponse = UserApiPattern.changeUserData(UserApiPattern.getToken(this.user), user);

        // извлечение значения поля success из ответа
        boolean isResponseSuccess = changeResponse.extract().path("success");

        // извлечение значения измененного адреса электронной почты из ответа
        String changedEmail = changeResponse.extract().path("user.email");

        // извлечение статус кода ответа
        int actualStatusCode = changeResponse.extract().statusCode();

        // проверка статус кода ответа, значения поля success с использованием методов класса Assert
        Assert.assertEquals(actualStatusCode, SC_OK);
        Assert.assertTrue(isResponseSuccess);

        // проверка измененного адреса электронной почты
        Assert.assertEquals(changedEmail, userEmail.toLowerCase());
    }

    // метод changeUserPassword() тестирует изменение пароля пользователя с авторизацией
    @Test
    @DisplayName("Изменение пароля пользователя с авторизацией")
    public void changeUserPassword() {
        // генерация нового случайного пароля
        String newPassword = UserDataPattern.getRandomData();

        // создание нового объекта UserDataPattern с новым паролем
        UserDataPattern newUser = new UserDataPattern(user.getEmail(), newPassword, user.getName());

        // изменение данных пользователя с использованием авторизации и получение ответа в виде ResponseSpecification
        var changeResponse = UserApiPattern.changeUserData(UserApiPattern.getToken(user), newUser);

        // извлечение значения поля success из ответа
        boolean isResponseSuccess = changeResponse.extract().path("success");

        // извлечение статус кода ответа
        int actualStatusCode = changeResponse.extract().statusCode();

        // проверка статус кода ответа, значения поля success с использованием методов класса Assert
        Assert.assertEquals(actualStatusCode, SC_OK);
        Assert.assertTrue(isResponseSuccess);
    }

    // метод changeUserName() тестирует изменение имени пользователя с авторизацией
    @Test
    @DisplayName("Изменение имени пользователя с авторизацией")
    public void changeUserName() {
        // генерация нового случайного имени пользователя
        String newUserName = UserDataPattern.getRandomData();

        // создание нового объекта UserDataPattern с новым именем пользователя
        UserDataPattern changedUser = new UserDataPattern(user.getEmail(), user.getPassword(), newUserName);

        // изменение данных пользователя с использованием авторизации и получение ответа в виде ResponseSpecification
        var changeResponse = UserApiPattern.changeUserData(UserApiPattern.getToken(user), changedUser);

        // извлечение значения поля success из ответа
        boolean isResponseSuccess = changeResponse.extract().path("success");

        // извлечение значения измененного имени пользователя из ответа
        String actualUserName = changeResponse.extract().path("user.name");

        // извлечение статус кода ответа
        int actualStatusCode = changeResponse.extract().statusCode();

        // проверка статус кода ответа, значения поля success и измененного имени пользователя с использованием методов класса Assert
        Assert.assertEquals(actualStatusCode, SC_OK);
        Assert.assertTrue(isResponseSuccess);
        Assert.assertEquals(actualUserName, newUserName);
    }

    // метод changeUserDataWithoutLogin() тестирует изменение пользовательских данных без авторизации
    @Test
    @DisplayName("Изменение пользовательских данных без авторизации")
    public void changeUserDataWithoutLogin() {
        // изменение данных пользователя без авторизации и получение ответа в виде ResponseSpecification
        var changeResponse = UserApiPattern.changeUserData("", user);

        // ожидаемое сообщение об ошибке при отсутствии авторизации
        String authorisedErrorMessage = "You should be authorised";

        // извлечение значения поля success из ответа
        boolean isResponseSuccess = changeResponse.extract().path("success");

        // извлечение значения сообщения об ошибке из ответа
        String responseMessage = changeResponse.extract().path("message");

        // извлечение статус кода ответа
        int actualStatusCode = changeResponse.extract().statusCode();

        // проверка статус кода ответа, значения поля success, и сообщения об ошибке с использованием методов класса Assert
        Assert.assertEquals(actualStatusCode, SC_UNAUTHORIZED);
        Assert.assertFalse(isResponseSuccess);
        Assert.assertEquals("Message:" + authorisedErrorMessage + "is not displayed", responseMessage, authorisedErrorMessage);
    }

    // метод changeUserEmailToUsedEmail() тестирует изменение адреса электронной почты на уже используемый адрес
    @Test
    @DisplayName("Изменить адрес почты на уже использованную почту")
    public void changeUserEmailToUsedEmail() {
        // генерация нового случайного пользователя
        UserDataPattern firstUser = UserDataPattern.getRandom();
        UserApiPattern.registration(firstUser);

        // получение адреса электронной почты первого пользователя
        String firstUserEmail = firstUser.getEmail();

        // создание нового объекта UserDataPattern с адресом электронной почты первого пользователя
        UserDataPattern secondUser = new UserDataPattern(firstUserEmail, user.getPassword(), user.getName());

        // изменение данных пользователя с использованием авторизации и получение ответа в виде ResponseSpecification
        var changeResponse = UserApiPattern.changeUserData(UserApiPattern.getToken(user), secondUser);

        // ожидаемое сообщение об ошибке при использовании уже существующего адреса электронной почты
        String existsEmailErrorMessage = "User with such email already exists";

        // извлечение значения поля success из ответа
        boolean isResponseSuccess = changeResponse.extract().path("success");

        // извлечение значения сообщения об ошибке из ответа
        String responseMessage = changeResponse.extract().path("message");

        // извлечение статус кода ответа
        int actualStatusCode = changeResponse.extract().statusCode();

        // проверка статус кода ответа, значения поля success и сообщения об ошибке с использованием методов класса Assert
        Assert.assertEquals(actualStatusCode, SC_FORBIDDEN);
        Assert.assertFalse(isResponseSuccess);
        Assert.assertEquals("Message:" + existsEmailErrorMessage + "is not displayed", responseMessage, existsEmailErrorMessage);
    }
}