package com.protectors.app.protectorsservice.stepdefs;

import com.protectors.app.protectorsservice.config.TestContext;
import org.springframework.boot.web.server.LocalServerPort;

import static com.protectors.app.protectorsservice.config.TestContext.CONTEXT;

public abstract class AbstractSteps {

    @LocalServerPort
    private int port;

    public String baseUrl() {
        return "http://localhost:" + port;
    }

    public TestContext testContext() {
        return CONTEXT;
    }
}
