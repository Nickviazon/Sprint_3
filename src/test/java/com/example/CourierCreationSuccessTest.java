package com.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CourierCreationSuccessTest {

    @Parameterized.Parameter()
    public Courier courier;

    @Parameterized.Parameter(1)
    public int expectedStatusCode;

    @Parameterized.Parameter(2)
    public boolean expectedOkField;

    @Parameterized.Parameters
    public static Object[][] courierData() {
        return new Object[][] {
                {Courier.getRandom(), 201, true}, // создание курьера со случайными полями
                {Courier.getCourierWithOnlyRequiredFields(), 201, true}, // создание курьера с обязательными полями (логин, пароль)
        };
    }

    private int courierId;

    @After
    public void tearDown() {
        CourierClient courierClient = new CourierClient();
        if (courier.login != null && courier.password != null) {
            int courierId = courierClient.loginCourier(CourierCredentials.from(courier));
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    public void courierCanBeCreated() {
        Response courierCreatedResponse = CourierClient.createCourierResponse(courier);

        int actualResponseCode = courierCreatedResponse.getStatusCode();
        assertThat("Response code is incorrect, 201 expected", actualResponseCode, is(expectedStatusCode));

        boolean actualResponseOkField = courierCreatedResponse.getBody().path("ok");
        assertThat("Ok field is incorrect", actualResponseOkField, is(expectedOkField));
    }
}
