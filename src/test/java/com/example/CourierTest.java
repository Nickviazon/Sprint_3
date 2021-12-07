/*
    ������� ����� �������;  +
    ������ ������� ���� ���������� ��������; +
    ����� ������� �������, ����� �������� � ����� ��� ������������ ����; +
    ������ ���������� ���������� ��� ������; +
    �������� ������ ���������� ok: true; +
    ���� ������ �� ����� ���, ������ ���������� ������; +
    ���� ������� ������������ � �������, ������� ��� ����, ������������ ������. +
*/

// TODO: �������� ��� ������� �������� ��������� � ��������� ��
// TODO: �������� ��� ���������������
package com.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (courier.login != null && courier.password != null) {
            int courierId = courierClient.loginCourier(CourierCredentials.from(courier));
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    public void courierCanBeCreatedWithCredentials() {
        courier = Courier.getRandom();
        Response courierCreatedResponse = courierClient.createCourierResponse(courier);
        int responseCode = CourierClient.getResponseCode(courierCreatedResponse);
        boolean responseBody = CourierClient.getResponseBodyFieldByPath(courierCreatedResponse, "ok");
        assertThat("Courier not created",
                true, both(is(responseCode == 201)).and(is(responseBody)));
    }

    @Test
    public void courierCanBeCreatedWithRequiredFields() {
        courier = Courier.getCourierWithOnlyRequiredFields();
        Response courierCreatedResponse = courierClient.createCourierResponse(courier);
        int responseCode = CourierClient.getResponseCode(courierCreatedResponse);
        boolean responseBody = CourierClient.getResponseBodyFieldByPath(courierCreatedResponse, "ok");
        assertThat("Courier not created",
                true, both(is(responseCode == 201)).and(is(responseBody)));
    }

    @Test
    public void cantBeCreatedTwoIdenticalCouriers() {
        courier = Courier.getRandom();
        // Create first courier
        Response firstCreatedCourierResponse = courierClient.createCourierResponse(courier);
        int firstCourierResponseCode = CourierClient.getResponseCode(firstCreatedCourierResponse);
        boolean firstResponseBody= CourierClient.getResponseBodyFieldByPath(firstCreatedCourierResponse, "ok");
        // Create second courier
        Response secondCourierCreatedResponse = courierClient.createCourierResponse(courier);
        int secondCourierResponseCode = CourierClient.getResponseCode(secondCourierCreatedResponse);
        String secondResponseBody = CourierClient.getResponseBodyFieldByPath(secondCourierCreatedResponse, "message");

        assertThat("First courier wasn't created",
                true, both(is(firstCourierResponseCode == 201)).and(is(firstResponseBody)));
        assertThat("Second courier was created", 409, is(secondCourierResponseCode));
        assertThat("Body message is incorrect when attempting to create second identical Courier",
                secondResponseBody,
                containsString("���� ����� ��� ������������"));

    }

    @Test
    public void courierCantBeCreatedWithoutCredentials() {
        courier = Courier.getCourierWithEmptyCredentials();
        Response courierResponse = courierClient.createCourierResponse(courier);
        int responseCode = CourierClient.getResponseCode(courierResponse);
        String responseBody = CourierClient.getResponseBodyFieldByPath(courierResponse, "message");
        assertThat("Courier without credentials was created", 400, is(responseCode));
        assertThat("Body message is incorrect when attempting to create a courier without credentials",
                responseBody,
                containsString("������������ ������ ��� �������� ������� ������"));
    }

    @Test
    public void courierCantBeCreatedWithTheSameLogin() {
        courier = Courier.getRandom();
        Courier secondCourier = Courier.getCourierWithExactLogin(courier.login);
        // Create first courier
        Response firstCreatedCourierResponse = courierClient.createCourierResponse(courier);
        int firstCourierResponseCode = CourierClient.getResponseCode(firstCreatedCourierResponse);
        boolean firstResponseBody= CourierClient.getResponseBodyFieldByPath(firstCreatedCourierResponse, "ok");
        // Create second courier
        Response secondCourierCreatedResponse = courierClient.createCourierResponse(secondCourier);
        int secondCourierResponseCode = CourierClient.getResponseCode(secondCourierCreatedResponse);
        String secondResponseBody = CourierClient.getResponseBodyFieldByPath(secondCourierCreatedResponse, "message");

        assertThat("First courier wasn't created",
                true, both(is(firstCourierResponseCode == 201)).and(is(firstResponseBody)));
        assertThat("Second courier was created", 409, is(secondCourierResponseCode));
        assertThat("Body message is incorrect when attempting to create second identical Courier",
                secondResponseBody,
                containsString("���� ����� ��� ������������"));
    }
}
