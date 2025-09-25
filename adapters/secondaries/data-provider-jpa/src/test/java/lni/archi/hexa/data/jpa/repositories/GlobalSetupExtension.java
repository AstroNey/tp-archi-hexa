package lni.archi.hexa.data.jpa.repositories;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class GlobalSetupExtension implements BeforeAllCallback {

    private static boolean initialized = false;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!initialized) {
            SharedTestDatabase.init("test", "src/test/resources/data.sql", "src/test/resources/insert.sql");
            initialized = true;
        }
    }

}
