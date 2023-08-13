package ru.netology.api.test;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.netology.api.data.DataHelper;
import ru.netology.api.data.SQLHelper;

import static ru.netology.api.data.APIHelper.*;

public class RESTAPITest {

    @AfterAll
    static void teardown() {
        SQLHelper.cleanDB();
    }

    @Test
    @DisplayName("Should get cards info")
    void shouldGetCardsInfo() {
        var user = DataHelper.getUser();
        authentication(user, 200);

        var actualCode = DataHelper.getVerifyCode();
        String token = verification(actualCode, 200);
        System.out.println(ArrayUtils.toString(getCards(token)));
    }

    @Test
    @DisplayName("Should do transfer success")
    void shouldDoTransferSuccess() {
        var user = DataHelper.getUser();
        authentication(user, 200);

        var actualCode = DataHelper.getVerifyCode();
        String token = verification(actualCode, 200);
        System.out.println(ArrayUtils.toString(getCards(token)));

        transaction(token, 200);
        System.out.println(ArrayUtils.toString(getCards(token)));

    }

    @Test
    @DisplayName("Should do transfer success")
    void shouldDoTransferInvalid() {
        var user = DataHelper.getUser();
        authentication(user, 200);

        var actualCode = DataHelper.getVerifyCode();
        String token = verification(actualCode, 200);
        System.out.println(ArrayUtils.toString(getCards(token)));

        transactionInvalid(token, 200);
        System.out.println(ArrayUtils.toString(getCards(token)));

    }
}


