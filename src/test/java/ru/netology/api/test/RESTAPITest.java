package ru.netology.api.test;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.netology.api.data.DataHelper;
import ru.netology.api.data.SQLHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.api.data.APIHelper.*;

public class RESTAPITest {

    @AfterAll
    static void teardown() {
        SQLHelper.cleanDB();
    }

    @Test
    @DisplayName("Should do transfer success")
    void shouldDoTransferSuccess() {
        var user = DataHelper.getUser();
        authentication(user, 200);

        var actualCode = DataHelper.getVerifyCode();
        String token = verification(actualCode, 200);

        var balanceCardFrom = DataHelper.balanceCardFrom();
        var balanceCardTo = DataHelper.balanceCardTo();
        var amountForTransfer = DataHelper.generateAmountValid();
        transaction(token, 200, amountForTransfer);

        ArrayUtils.toString(getCards(token));

        var expectedBalanceCardFrom = (balanceCardFrom - amountForTransfer) * 100;
        var expectedBalanceCardTo = (balanceCardTo + amountForTransfer) * 100;

        var actualBalanceCardFrom = SQLHelper.getBalanceCardFromAfterTransfer();
        var actualBalanceCardTo = SQLHelper.getBalanceCardToAfterTransfer();

        assertEquals(expectedBalanceCardFrom, actualBalanceCardFrom);
        assertEquals(expectedBalanceCardTo, actualBalanceCardTo);
    }

    @Test
    @DisplayName("Should block transfer because amount is more then balance")
    void shouldBeBlocked() {
        var user = DataHelper.getUser();
        authentication(user, 200);

        var actualCode = DataHelper.getVerifyCode();
        String token = verification(actualCode, 200);

        var balanceCardFrom = DataHelper.balanceCardFrom();
        var balanceCardTo = DataHelper.balanceCardTo();
        var amountForTransfer = DataHelper.generateAmountInvalid();
        transaction(token, 400, amountForTransfer);

        ArrayUtils.toString(getCards(token));

        var expectedBalanceCardFrom = (balanceCardFrom) * 100;
        var expectedBalanceCardTo = (balanceCardTo) * 100;

        var actualBalanceCardFrom = SQLHelper.getBalanceCardFromAfterTransfer();
        var actualBalanceCardTo = SQLHelper.getBalanceCardToAfterTransfer();

        assertEquals(expectedBalanceCardFrom, actualBalanceCardFrom);
        assertEquals(expectedBalanceCardTo, actualBalanceCardTo);
    }
}


