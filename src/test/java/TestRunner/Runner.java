package TestRunner;

import io.cucumber.core.cli.Main;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions()
public class Runner {
        Runner(){
                String features = "./src/test/resources/com/org/features";
                String steps = "steps";
                List<String> args = new ArrayList<>();
                args.add(features);
                args.add(steps);
        }
}
