package com.example.easychatgpt;

public class OpenAIMsgBean {
    public String role;
    public String content;

    public OpenAIMsgBean(String role, String content) {
        this.role = role;
        this.content = content;
    }

    @Override
    public String toString() {
        return "OpenAIMessageBean{" +
                "role=" + role +
                ", content=" + content +
                '}';
    }
}

