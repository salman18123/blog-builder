package com.tree.blogs.Listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tree.blogs.models.BlogOperationsOutbox;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.List;

public class TestData {


    private void init(Message message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<BlogOperationsOutbox> list = objectMapper.readValue(message.getBody(), List.class);
    }
}
