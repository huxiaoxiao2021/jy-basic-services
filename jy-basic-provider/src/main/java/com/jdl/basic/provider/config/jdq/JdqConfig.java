package com.jdl.basic.provider.config.jdq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @description:
 * @author: lql
 * @date: 2021/1/6 21:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JdqConfig {


    private String id;

    private boolean testEnv;

    private String domain;

    private String clientId;

    private String password;

    private String topic;

    private boolean enabled = true;

    private HashMap<String, String> userDefinedProperties;


}
