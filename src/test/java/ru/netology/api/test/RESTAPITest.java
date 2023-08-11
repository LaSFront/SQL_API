package ru.netology.api.test;
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
    @DisplayName("Should verify be success")
    void shouldVerifyBeSuccess() {
        var user = DataHelper.getUser();
        authentication(user,200);
        var actualCode = DataHelper.getVerifyCode();
        verification(actualCode, 200);
        String token = verification(actualCode,200);
        getCards(token);
    }




}
