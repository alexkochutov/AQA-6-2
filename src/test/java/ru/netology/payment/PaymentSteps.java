package ru.netology.payment;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;
import ru.netology.pages.TransferPage;
import ru.netology.pages.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;

    @Пусть("пользователь залогинен с именем {string} и паролем {string} и кодом {string}")
    public void validLogin(String login, String password, String code) {
        loginPage = open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.validLogin(login, password);
        dashboardPage = verificationPage.validVerify(code);
        dashboardPage.verifyIsDashboardPage();
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void makeTransfer(int amount, String cardNumber, int id) {
        transferPage = dashboardPage.chooseCardById(id);
        dashboardPage = transferPage.makeTransfer(amount, cardNumber);
    }

    @Тогда("тогда баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void checkBalance(int id, int balance) {
        assertEquals(balance, dashboardPage.getBalanceById(id));
    }
}