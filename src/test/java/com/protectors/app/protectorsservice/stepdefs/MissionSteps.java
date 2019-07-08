package com.protectors.app.protectorsservice.stepdefs;

import com.protectors.app.protectorsservice.entity.Mission;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;

public class MissionSteps extends AbstractSteps implements En {

    public MissionSteps() {
        Given("user wants to create a mission with the following attributes", (DataTable missionData) -> {
            List<Mission> missions = missionData.asList(Mission.class);
            testContext().setPayload(missions.get(0));
        });

        When("the user saves new missions", () -> {
            final String createMissionUrl = baseUrl() + "/mission";
            final Response response = given().log()
                    .all()
                    .when()
                    .contentType(ContentType.JSON)
                    .body(testContext().getPayload())
                    .post(createMissionUrl)
                    .andReturn();

            testContext().setResponse(response);

            response.then()
                    .log()
                    .all();
            Assert.assertEquals(201, response.getStatusCode());
        });
    }
}