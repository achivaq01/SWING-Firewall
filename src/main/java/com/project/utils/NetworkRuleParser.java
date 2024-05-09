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
            ProcessBuilder processBuilder = new ProcessBuilder("sudo", "iptables", "-L", "-v", "-n", "--line-numbers");
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
                networkRule.setNumber(rule[0]);
                networkRule.setType(rule[4]);
                networkRule.setAction(rule[5]);
                if (rule[6].equals("*") && rule[7].equals("*")) {
                    networkRule.setNetworkInterface("*");
                } else if (rule[6].equals("*")) {
                    networkRule.setNetworkInterface(rule[7]);
                } else if (rule[7].equals("*")) {
                    networkRule.setNetworkInterface(rule[6]);
                } else {
                    networkRule.setNetworkInterface("input: " + rule[6] + " output: " + rule[7]);
                }
                networkRule.setDirection("INPUT");
                if (rule[8].equals("0.0.0.0/0") && rule[9].equals("0.0.0.0/0")) {
                    networkRule.setIpAddress(rule[8]);
                } else if (rule[8].equals("0.0.0.0/0")) {
                    networkRule.setDirection("OUTPUT");
                    networkRule.setIpAddress(rule[9]);
                } else if (rule[9].equals("0.0.0.0/0")) {
                    networkRule.setDirection("INPUT");
                    networkRule.setIpAddress(rule[8]);
                } else {
                    networkRule.setIpAddress("input: " + rule[8] + " output: " + rule[9]);
                }
                networkRule.setName(chain + " " + networkRule.getNumber());
                if (networkRule.getName().toLowerCase().contains("input")) {
                    networkRule.setDirection("INPUT");
                } else if (networkRule.getName().toLowerCase().contains("output")) {
                    networkRule.setDirection("OUTPUT");
                } else if (networkRule.getName().toLowerCase().contains("forward")) {
                    networkRule.setDirection("FORWARD");
                } else {
                    networkRule.setDirection("--");
                }
                if (rule.length > 10) {
                    StringBuilder comment = new StringBuilder();
                    for (int i = 10; i < rule.length; i++) {
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
