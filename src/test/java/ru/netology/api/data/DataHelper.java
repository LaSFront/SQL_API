package ru.netology.api.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;

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

    //CARDS
    @Data
    public static class CardInfo {
        private String id;
        private String number;
        private String balance;
    }

    public static int generateRandomTransferAmount(int balance) {
        return new Random().nextInt(Math.abs(balance)) + 1;
    }

    public static String selectCard(CardInfo cardInfo) {
        var cardForTransfer = cardInfo.getNumber();
        return cardForTransfer;
    }
}
