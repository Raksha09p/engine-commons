package com.walmart.enginecommons.commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class CommonUtil {

    private static String hostName;

    private CommonUtil() {

    }

    public static String getHostName() {
        if (hostName != null) {
            return hostName;
        } else {
            try {
                hostName = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                log.error("Failed to Retrieve Hostname with failure message : {}", e.getMessage());
                hostName = "unknown";
            }
            return hostName;
        }

    }
}