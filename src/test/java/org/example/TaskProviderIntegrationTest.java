package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.uhn.fhir.context.FhirContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hl7.fhir.r4.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"integration"})
class TaskProviderIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    void retrieveTaskWithDate() throws IOException, URISyntaxException {
        URL json = getClass().getClassLoader().getResource("test-request-body.json");
        assertNotNull(json);
        final String requestBody = Files.readString(Paths.get(json.toURI()));

        Response response = assertDoesNotThrow(() ->
                RestAssured.given()
                        .when()
                        .port(port)
                        .contentType("application/fhir+json")
                        .body(requestBody)
                        .post("/Task/$test"));

        assertEquals(200, response.statusCode(), response.then().extract().body().asString());
        Task task = FhirContext.forR4()
                .newJsonParser()
                .parseResource(Task.class, response.then().extract().asString());
        assertNotNull(task.getAuthoredOn());
    }
}
