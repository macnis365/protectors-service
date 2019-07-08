package com.protectors.app.protectorsservice;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        plugin = {"json:target/cucumber-report.json",
                "pretty:target/cucumber-pretty.txt"},
        glue = {"com.protectors.app.protectorsservice.config",
                "com.protectors.app.protectorsservice.stepdefs"})
public class CucumberTest {
}
