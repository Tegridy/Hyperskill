import org.hyperskill.hstest.v5.stage.BaseStageTest;
import org.hyperskill.hstest.v5.testcase.CheckResult;
import org.hyperskill.hstest.v5.testcase.TestCase;
import phonebook.Main;

import java.util.List;

public class PhoneBookTest extends BaseStageTest {
    public PhoneBookTest() throws Exception {
        super(Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return List.of(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        reply = reply.toLowerCase();
        return new CheckResult(
            reply.contains("start searching")
                && reply.contains("found")
                && reply.contains("min.")
                && reply.contains("sec.")
                && reply.contains("ms.")
                && reply.contains("sorting time")
                && reply.contains("searching time")
                && reply.contains("linear search")
                && reply.contains("bubble sort")
                && reply.contains("jump search"));
    }
}
