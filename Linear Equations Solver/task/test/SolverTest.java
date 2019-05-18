import org.hyperskill.hstest.v4.testcase.CheckResult;
import org.hyperskill.hstest.v4.stage.MainMethodTest;
import org.hyperskill.hstest.v4.testcase.TestCase;
import solver.Main;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;


class TestClue {

    TestClue(){

    }
}


public class SolverTest extends MainMethodTest<TestClue> {

    public SolverTest() throws Exception {
        super(Main.class);
    }

    @Override
    public List<TestCase<TestClue>> generateTestCases() {
        return List.of(
            new TestCase<TestClue>()
                .setInput("4 5 7\n3 9 9"),
            new TestCase<TestClue>()
                .setInput("1 2 3\n4 5 6"),
            new TestCase<TestClue>()
                .setInput("1.2 3.4 5.6\n7.8 9.0 12.3"),
            new TestCase<TestClue>()
                .setInput("23.21 32.12 65.43\n1 0 1")
        );
    }

    @Override
    public String solve(String input) {
        Scanner sc = new Scanner(input);

        double a = sc.nextDouble();
        double b = sc.nextDouble();
        double c = sc.nextDouble();
        double d = sc.nextDouble();
        double e = sc.nextDouble();
        double f = sc.nextDouble();

        double y = (f - c * (d / a)) / (e - b * (d / a));
        double x = (c - b * y) / a;

        return x + " " + y;
    }

    @Override
    public CheckResult checkSolved(String reply, String clue) {
        try {
            double[] actual = Arrays.stream(reply.split(" ")).mapToDouble(Double::parseDouble).toArray();
            double[] expected = Arrays.stream(clue.split(" ")).mapToDouble(Double::parseDouble).toArray();
            if (actual.length != expected.length) {
                return CheckResult.FALSE;
            }
            for (int i = 0; i < actual.length; i++) {
                if (abs(actual[i] - expected[i]) > 0.001) {
                    return CheckResult.FALSE;
                }
            }
            return CheckResult.TRUE;
        }
        catch (NumberFormatException ex) {
            return new CheckResult(false, "Can't parse numbers - check if you don't print any additional symbols except a space between the numbers");
        }
    }
}
