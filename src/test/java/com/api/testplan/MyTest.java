package com.api.testplan;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class MyTest {

    // Step 1: Define a data provider method
    @DataProvider(name = "testdata")
    public Object[][] testData() {
    	Object o="{L1_CS_REASON_CODE_ID=5000013682, L3_CS_REASON_CODE_ID=5000013685, L1_NAME=Feedback, L5_NAME=Abusive feedback, L5_CS_REASON_CODE_ID=5000013699, L3_NAME=Threatening or abusive}";
        return new Object[][]{
                {createTestData(o)},
                // Add more sets of data as needed
        };
    }

    // Step 2: Use the data provider in your test method
    @Test(dataProvider = "testdata")
    public void myTestMethod(Map<String, Object> testData) {
        // Your test logic using the provided data
        for (Map.Entry<String, Object> entry : testData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Add your actual test assertions here
    }

    // Utility method to create a Map from variable arguments
    private Map<String, Object> createTestData(Object... data) {
        Map<String, Object> testData = new HashMap<>();
        for (int i = 0; i < data.length; i += 2) {
            testData.put((String) data[i], data[i + 1]);
        }
        return testData;
    }
}