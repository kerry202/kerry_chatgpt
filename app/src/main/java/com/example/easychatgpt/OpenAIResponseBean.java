package com.example.easychatgpt;

import java.util.List;

public class OpenAIResponseBean {
    public String id;
    public String object;
    public int created;
    public String model;
    public Usage usage;
    public List<Choice> choices;

    public static class Usage {
        public int prompt_tokens;
        public int completion_tokens;
        public int total_tokens;
    }

    public static class Choice {
        public OpenAIMsgBean message;
        public String finish_reason;
        public int index;
    }
}
