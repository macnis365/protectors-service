package com.protectors.app.protectorsservice.config;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProtectorsServiceApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(ProtectorsServiceApplicationTests.class);

    @Before
    public void setUp() {
        LOG.info("------------- TEST CONTEXT SETUP -------------");
    }

    @After
    public void tearDown() {
        LOG.info("------------- TEST CONTEXT TEAR DOWN -------------");
        TestContext.CONTEXT.reset();
    }

}
