package com.company;

import org.testng.annotations.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;


public class FailingTest {

    /**
     * Test passed in mvn:test, failed mvn:package
     * mvn:test  - path is ../module2/target/classes/myresource.txt
     * mvn:package - path is ../module1/target/module1-1-SNAPSHOT.jar!/myresource.txt
     * Can be fixed by adding to the pom.xml in module2
     * <build>
        <resources>
            <resource>
                <directory>../module1/src/main/resources</directory>
            </resource>
        </resources>
     * </build>
     * */
    @Test
    public void strangeFailure() throws Exception {
        String file = ClassLoader.getSystemResource("myresource.txt").getFile();
        System.out.println(file);
        assertEquals(new FileInputStream(file).read(), 49);
    }
}
