package org.example.paymentderviceaplicationii;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example.paymentderviceaplicationii.stepdefs",
        plugin = {"pretty", "summary"},
        publish = true
)
public class CucumberTest {
}
