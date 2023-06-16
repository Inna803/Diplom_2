package praktikum;

import org.apache.commons.lang3.RandomStringUtils;

// класс UserDataPattern представляет собой шаблон данных пользователя, используемый в автотестах

public class UserDataPattern {
    // поле для хранения адреса электронной почты пользователя
    private String email;

    // поле для хранения пароля пользователя
    private String password;

    // поле для хранения имени пользователя
    private String name;

    // конструктор класса, принимающий адрес электронной почты и пароль пользователя
    public UserDataPattern(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // конструктор класса, принимающий адрес электронной почты, пароль и имя пользователя
    public UserDataPattern(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // метод для получения адреса электронной почты пользователя
    public String getEmail() {
        return email;
    }

    // метод для установки адреса электронной почты пользователя
    public void setEmail(String email) {
        this.email = email;
    }

    // метод для получения пароля пользователя
    public String getPassword() {
        return password;
    }

    // метод для установки пароля пользователя
    public void setPassword(String password) {
        this.password = password;
    }

    // метод для получения имени пользователя
    public String getName() {
        return name;
    }

    // метод для установки имени пользователя
    public void setName(String name) {
        this.name = name;
    }

    // статический метод для получения случайных данных пользователя
    public static UserDataPattern getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru".toLowerCase();
        final String password = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        final String name = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        return new UserDataPattern(email, password, name);
    }

    // статический метод для получения случайного адреса электронной почты
    public static String getRandomEmail() {
        return RandomStringUtils.randomAlphabetic(10) + "@yandex.ru".toLowerCase();
    }

    // статический метод для получения случайных данных
    public static String getRandomData() {
        return RandomStringUtils.randomAlphabetic(10).toLowerCase();
    }

    // переопределение метода toString() для представления объекта UserDataPattern в виде строки
    @Override
    public String toString() {
        return "UserDataPattern{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}