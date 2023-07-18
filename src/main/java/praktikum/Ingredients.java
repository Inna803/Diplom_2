package praktikum;

// класс Ingredients представляет объект, который содержит информацию об ингредиентах

public class Ingredients {
    private String ingredients; // приватное поле для хранения списка ингредиентов

    // конструктор класса Ingredients
    // принимает список ингредиентов и инициализирует поле ingredients
    public Ingredients(String ingredients) {
        this.ingredients = ingredients;
    }

    // метод для получения списка ингредиентов
    // возвращает значение поля ingredients
    public String getIngredients() {
        return ingredients;
    }

    // метод для установки списка ингредиентов
    // принимает новый список ингредиентов и присваивает его полю ingredients
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}