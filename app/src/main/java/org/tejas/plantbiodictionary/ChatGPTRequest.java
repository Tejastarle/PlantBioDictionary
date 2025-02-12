package org.tejas.plantbiodictionary;

import java.util.Collections;
import java.util.List;

public class ChatGPTRequest {
    private String model;
    private List<Message> messages;

    public ChatGPTRequest(String model, String prompt, String base64Image) {
        this.model = model;
        this.messages = Collections.singletonList(new Message("user", prompt + "\n\n" + base64Image));
    }

    static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
