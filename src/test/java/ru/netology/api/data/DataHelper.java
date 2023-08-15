package ru.netology.api.data;

import lombok.Value;

import java.util.List;
import java.util.Random;

public class DataHelper {

    private DataHelper() {
    }

    //private static final Faker faker = new Faker(new Locale("ru"));

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

    public static List<CardInfo> getAllCards() {
        return SQLHelper.getCards();
    }

    public static CardInfo getInfoCardFrom() {
        Random random = new Random();
        List<CardInfo> cards = getAllCards();
        final int cardFromRandom = random.nextInt(cards.size());
        return cards.get(cardFromRandom);
    }

    public static CardInfo getInfoCardTo() {
        Random random = new Random();
        List<CardInfo> cards = getAllCards();
        CardInfo cardFrom = getInfoCardFrom();
        cards.remove(cardFrom);
        final int cardToRandom = random.nextInt(cards.size());
        return cards.get(cardToRandom);

    }

    public static Integer balanceCardFrom() {
        CardInfo cardFrom = getInfoCardFrom();
        var balanceFrom = cardFrom.getBalance();
        return (balanceFrom / 100);
    }

    public static Integer balanceCardTo() {
        CardInfo cardTo = getInfoCardTo();
        var balanceTo = cardTo.getBalance();
        return (balanceTo / 100);
    }

    public static Transfer transfer(String token, Integer amount) {
        Random random = new Random();
        List<CardInfo> cards = getAllCards();
        CardInfo cardFrom = getInfoCardFrom();
        cards.remove(cardFrom);
        final int cardToRandom = random.nextInt(cards.size());
        CardInfo cardTo = cards.get(cardToRandom);
        return new Transfer(cardFrom.getNumber(), cardTo.getNumber(), amount);
    }

    public static int generateAmountValid() {
        return new Random().nextInt(Math.abs(balanceCardFrom())) + 1;
    }

    public static int generateAmountInvalid() {
        return Math.abs(balanceCardFrom()) + new Random().nextInt(1000);
    }

    @Value
    public static class Balance {
        private Integer balanceAfter;
    }
}
