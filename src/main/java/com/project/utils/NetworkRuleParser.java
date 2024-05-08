package com.project.utils;

import com.project.models.NetworkRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.project.utils.Constants.TAG_NETWORK_RULE_PARSER;

public class NetworkRuleParser {

    public static List<NetworkRule> getRules() {
        List<NetworkRule> rules = new ArrayList<>();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sudo", "iptables", "-L", "-v");
            Process process = processBuilder.start();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean isTableHeader = false;
            String chain = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                if (line.contains("Chain")) {
                    chain = line.split("\\s+")[1];
                    isTableHeader = true;
                    continue;
                }
                if (isTableHeader) {
                    isTableHeader = false;
                    continue;
                }

                String[] rule = line.strip().split("\\s+");
                NetworkRule networkRule = new NetworkRule();
                networkRule.setType(rule[3]);
                networkRule.setAction(rule[4]);
                networkRule.setNetworkInterface("input: " + rule[5] + " output: " + rule[6]);
                networkRule.setDirection("INPUT");
                if (rule[7].equals("anywhere") && rule[8].equals("anywhere")) {
                    networkRule.setIpAddress(rule[7]);
                } else if (rule[7].equals("anywhere")) {
                    networkRule.setDirection("OUTPUT");
                    networkRule.setIpAddress(rule[8]);
                } else if (rule[8].equals("anywhere")) {
                    networkRule.setDirection("INPUT");
                    networkRule.setIpAddress(rule[7]);
                } else {
                    networkRule.setIpAddress("input: " + rule[7] + " output: " + rule[8]);
                }
                networkRule.setName(rule[2]);
                if (networkRule.getName().toLowerCase().contains("input")) {
                    networkRule.setDirection("INPUT");
                } else if (networkRule.getName().toLowerCase().contains("output")) {
                    networkRule.setDirection("OUTPUT");
                } else if (networkRule.getName().toLowerCase().contains("forward")) {
                    networkRule.setDirection("FORWARD");
                } else {
                    networkRule.setDirection("--");
                }
                if (rule.length > 9) {
                    StringBuilder comment = new StringBuilder();
                    for (int i = 9; i < rule.length; i++) {
                        if (rule[i].contains(":")) {
                            String[] split = rule[i].split(":");
                            if (split.length == 2) {
                                networkRule.setPort(split[1]);
                            }
                        }
                        comment.append(rule[i]).append(" ");
                    }
                    networkRule.setComment(comment.toString());
                }
                if (!chain.toLowerCase().contains("input") && !chain.toLowerCase().contains("output") && !chain.toLowerCase().contains("forward") && !chain.toLowerCase().contains("ufw")) {
                    String application = chain.split("-")[0];
                    networkRule.setApplicationName(application);
                }
                rules.add(networkRule);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rules;
    }

}
