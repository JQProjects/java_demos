package com.jqproject.tools;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ZKClientTest {

    private static ZKClient zkClient;

    @BeforeEach
    void setUp() {
        zkClient = new ZKClient();
    }

    @AfterEach
    void tearDown() {
    }


}