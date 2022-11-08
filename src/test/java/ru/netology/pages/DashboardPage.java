package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final ElementsCollection cardList = $$("li.list__item");
    private final SelenideElement refreshButton = $("[data-test-id='action-reload']");
    private final SelenideElement heading = $("[data-test-id=dashboard]");

    private SelenideElement getCardElementByNumber(String cardNumber) {
        String cardId = cardNumber.substring(15);
        return cardList.findBy(Condition.text(cardId));
    }

    private SelenideElement getCardElementById(int id) {
        return cardList.get(id - 1);
    }

    public void verifyIsDashboardPage() {
        heading.shouldBe(visible);
    }

    private int extractBalance(String text) {
        String balanceStart = "баланс: ";
        String balanceFinish = " р.";
        int start = text.indexOf(balanceStart) + balanceStart.length();
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start, finish);
        return Integer.parseInt(value);
    }

    public int getBalanceByNumber(String cardNumber) {
        String text = getCardElementByNumber(cardNumber).getText();
        return extractBalance(text);
    }

    public int getBalanceById(int id) {
        String text = getCardElementById(id).getText();
        return extractBalance(text);
    }

    public TransferPage chooseCardByNumber(String cardNumber) {
        getCardElementByNumber(cardNumber).$("button").click();
        return new TransferPage();
    }

    public TransferPage chooseCardById(int id) {
        getCardElementById(id).$("button").click();
        return new TransferPage();
    }

    public void refreshPage() {
        refreshButton.click();
    }
}