package org.example.paymentderviceaplicationii;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example.paymentderviceaplicationii",
        plugin = {"pretty"}
)
public class CucumberTest {
}
