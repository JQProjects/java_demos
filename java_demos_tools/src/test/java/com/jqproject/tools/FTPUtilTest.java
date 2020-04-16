package com.jqproject.tools;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FTPUtilTest {

    @Test
    void connectTest(){

        FTPClient ftpClient = FTPUtil.connectionFTPServer();
        System.out.println(ftpClient.isConnected());
    }

    @Test
    void myFirstTest() {
        assertEquals(2, 1+1);
    }

}