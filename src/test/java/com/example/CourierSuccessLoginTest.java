package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CourierSuccessLoginTest {

    private CourierClient courierClient;
    private Integer courierId;

    @Parameterized.Parameter()
    public Courier courier;

    @Parameterized.Parameter(1)
    public int expectedResponseCode;

    @Parameterized.Parameters(name="{index}: {0}")
    public static Object[][] courierData() {
        return new Object[][] {
                {Courier.getRandom(), 200},
                {Courier.getCourierWithOnlyRequiredFields(), 200}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courierClient.createCourier(courier);
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    public void courierCanLogin() {
        Response loginCourierResponse = courierClient.loginCourierResponse(CourierCredentials.from(courier));
        int responseCode = loginCourierResponse.getStatusCode();
        int courierId = loginCourierResponse.getBody().path("id");
        assertThat("Courier don't login", responseCode, is(expectedResponseCode));
        assertThat("Courier has incorrect Id", courierId, is(not(0)));
    }
}
