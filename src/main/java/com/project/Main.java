package com.project;

import com.project.models.NetworkRule;

import java.io.IOException;
import java.util.Arrays;

import static com.project.utils.NetworkRuleParser.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        new FirewallApp();
    }
}
