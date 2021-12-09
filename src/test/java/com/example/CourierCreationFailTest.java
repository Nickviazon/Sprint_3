/*
    ������� ����� �������;  +
    ������ ������� ���� ���������� ��������; +
    ����� ������� �������, ����� �������� � ����� ��� ������������ ����; +
    ������ ���������� ���������� ��� ������; +
    �������� ������ ���������� ok: true; +
    ���� ������ �� ����� ���, ������ ���������� ������; +
    ���� ������� ������������ � �������, ������� ��� ����, ������������ ������. +
*/

package com.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierCreationFailTest {


    private Courier courier;

    @After
    public void tearDown() {
        CourierClient courierClient = new CourierClient();
        if (courier.login != null && courier.password != null) {
            int courierId = courierClient.loginCourier(CourierCredentials.from(courier));
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    public void cantBeCreatedTwoIdenticalCouriers() {
        courier = Courier.getRandom();

        // Create first courier
        Response firstCreatedCourierResponse = CourierClient.createCourierResponse(courier);
        int firstCourierResponseCode = firstCreatedCourierResponse.getStatusCode();
        assertThat("Response code is incorrect, 201 expected", firstCourierResponseCode, is(201));

        boolean firstResponseOkField= firstCreatedCourierResponse.getBody().path("ok");
        assertThat("Ok field is incorrect", firstResponseOkField, is(true));

        // Create second courier
        Response secondCourierCreatedResponse = CourierClient.createCourierResponse(courier);

        int secondCourierResponseCode = secondCourierCreatedResponse.getStatusCode();
        assertThat("Response code is incorrect, 409 expected", secondCourierResponseCode, is(409));

        String secondResponseOkField = secondCourierCreatedResponse.getBody().path("message");
        assertThat("Body message is incorrect",
                secondResponseOkField,
                containsString("���� ����� ��� ������������"));

    }

    @Test
    public void courierCantBeCreatedWithoutCredentials() {
        courier = Courier.getCourierWithEmptyCredentials();
        Response courierResponse = CourierClient.createCourierResponse(courier);
        int responseCode = courierResponse.getStatusCode();
        assertThat("Courier without credentials was created", responseCode, is(400));

        String responseBody = courierResponse.getBody().path("message");
        assertThat("Body message is incorrect when attempting to create a courier without credentials",
                responseBody,
                containsString("������������ ������ ��� �������� ������� ������"));
    }

    @Test
    public void courierCantBeCreatedWithTheSameLogin() {
        // Create first courier
        courier = Courier.getRandom();
        Response firstCreatedCourierResponse = CourierClient.createCourierResponse(courier);

        int firstCourierResponseCode = firstCreatedCourierResponse.getStatusCode();
        assertThat("Response code is incorrect, 201 expected", firstCourierResponseCode, is(201));

        boolean firstResponseOkField= firstCreatedCourierResponse.getBody().path("ok");
        assertThat("Ok field is incorrect", firstResponseOkField, is(true));

        // Create second courier
        Courier secondCourier = Courier.getCourierWithExactLogin(courier.login);
        Response secondCourierCreatedResponse = CourierClient.createCourierResponse(courier);

        int secondCourierResponseCode = secondCourierCreatedResponse.getStatusCode();
        assertThat("Response code is incorrect, 409 expected", secondCourierResponseCode, is(409));

        String secondResponseOkField = secondCourierCreatedResponse.getBody().path("message");
        assertThat("Body message is incorrect",
                secondResponseOkField,
                containsString("���� ����� ��� ������������"));

    }
}
