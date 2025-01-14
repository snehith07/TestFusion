package org.example.context;

import java.util.HashMap;

public class TestExecutionContext {

    private final String testName;
    private final HashMap<String, Object> testExecutionState;

    public TestExecutionContext(String testName){
        SessionContext.addContext(Thread.currentThread().getId(), this);
        this.testName = testName;
        this.testExecutionState = new HashMap<>();
    }

    public String getTestName(){
        return testName;
    }

    public void addTestState(String key, Object details){
        testExecutionState.put(key, details);
    }

    public Object getTestState(String key){
        return testExecutionState.get(key);
    }

    public String getTestStateAsString(String key){
        return (String) testExecutionState.get(key);
    }

    public HashMap<String, Object> getAllTestState(){
        return testExecutionState;
    }
}
