package ru.netology.api.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    private DataHelper() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    //USER
    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    //VERIFY
    @Value
    public static class VerifyInfo {
        private String login;
        private String code;
    }

    public static VerifyInfo getVerifyCode() {
        return new VerifyInfo(getUser().getLogin(), SQLHelper.getVerifyData());
    }
    //cards


    @Value
    public static class Transfer {
        private String from;
        private String to;
        private Integer amount;

    }

    public static Transfer transfer(String token) {
        Random random = new Random();
        List<CardInfo> cards = SQLHelper.getCards();
        final int cardFromRandom = random.nextInt(cards.size());
        CardInfo cardFrom = cards.get(cardFromRandom);
        cards.remove(cardFrom);
        final int cardToRandom = random.nextInt(cards.size());
        CardInfo cardTo = cards.get(cardToRandom);
        final int amountToTransfer = generateAmountValid(cardFrom.getBalance() / 100);
        return new Transfer(cardFrom.getNumber(), cardTo.getNumber(), amountToTransfer);
    }

    public static int generateAmountValid(int balance) {
        return new Random().nextInt(Math.abs(balance)) + 1;
    }

    public static Transfer transferInvalid(String token) {
        Random random = new Random();
        List<CardInfo> cards = SQLHelper.getCards();
        final int cardFromRandom = random.nextInt(cards.size());
        CardInfo cardFrom = cards.get(cardFromRandom);
        cards.remove(cardFrom);
        final int cardToRandom = random.nextInt(cards.size());
        CardInfo cardTo = cards.get(cardToRandom);
        final int amountToTransfer = generateAmountInvalid(cardFrom.getBalance() / 100);
        return new Transfer(cardFrom.getNumber(), cardTo.getNumber(), amountToTransfer);
    }

    public static int generateAmountInvalid(int balance) {
        return Math.abs(balance) + new Random().nextInt(1000);
    }
}
