package com.fincore.app;


import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "com.fincore.app.menu",
        "com.fincore.app.user",
        "com.fincore.app.command",
        "com.fincore.app.common",
})
public class TestRunner {
}
