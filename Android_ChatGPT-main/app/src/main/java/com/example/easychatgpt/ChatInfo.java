package com.example.easychatgpt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatInfo implements Serializable {

    /**
     * model : gpt-3.5-turbo
     * messages : [{"role":"user","content":"美国硅谷银行破产对中国有啥影响？"}]
     * temperature : 0.7
     */

    public String model = "gpt-3.5-turbo";
    public float temperature = 0.7f;
    public List<MessagesBean> messages = new ArrayList<>();

    public static class MessagesBean implements Serializable {
        /**
         * role : user
         * content : 美国硅谷银行破产对中国有啥影响？
         */

        public String role = "user";
        public String content;

        public MessagesBean(String content) {
            this.content = content;
        }
    }
}
