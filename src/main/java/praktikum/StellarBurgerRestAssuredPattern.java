package praktikum;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

// класс StellarBurgerRestAssuredPattern представляет собой базовый класс для выполнения автотестов с использованием библиотеки RestAssured

public class StellarBurgerRestAssuredPattern {

    // базовый URL, к которому будут отправляться запросы
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    // метод для получения базовой спецификации запроса
    // возвращает объект RequestSpecification, содержащий настройки для выполнения запроса
    protected static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured()) // добавление фильтра для интеграции с Allure-отчетами
                .log(LogDetail.ALL) // логирование всех деталей запроса и ответа
                .setContentType(JSON) // установка типа контента запроса как JSON
                .setBaseUri(BASE_URL) // установка базового URL для запросов
                .build();
    }
}