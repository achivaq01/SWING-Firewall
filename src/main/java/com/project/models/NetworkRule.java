package com.project.models;

public class NetworkRule {
    private String name;
    private String port = "--";
    private String type; //TCP OR UDP
    private String applicationName = "--";
    private String userName = "--";
    private String ipAddress;
    private String action; //network traffic allowed, forbidden or other.
    private String networkInterface; //eth0, wlan0, etc OR null
    private String direction; // either 0 IN or 1 OUT
    private String comment = "--";
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(String networkInterface) {
        this.networkInterface = networkInterface;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetworkRule{");
        sb.append("name='").append(name).append('\'');
        sb.append(", port=").append(port);
        sb.append(", type='").append(type).append('\'');
        sb.append(", applicationName='").append(applicationName).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", ipAddress='").append(ipAddress).append('\'');
        sb.append(", action='").append(action).append('\'');
        sb.append(", networkInterface='").append(networkInterface).append('\'');
        sb.append(", direction=").append(direction);
        sb.append('}');
        return sb.toString();
    }
}
