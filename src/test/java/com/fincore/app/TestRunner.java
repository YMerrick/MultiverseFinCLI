package com.fincore.app;


import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "com.fincore.app.application",
        "com.fincore.app.cli",
        "com.fincore.app.data",
        "com.fincore.app.model",
})
public class TestRunner {
}
