

import org.junit.After;
import org.junit.Before;


public class Phase1UnitTests {
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"clientSide.tests.ModelUnitTests",
				"clientSide.tests.ProxyServerUnitTests",
				"clientSide.tests.ServerPollerUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}
