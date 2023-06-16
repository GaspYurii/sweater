package com.example.sweater.model.util;

import com.example.sweater.model.User;

public class MessageHelper {
    public static String getAuthorName(User author) {
        return author !=null ? author.getUsername() : "<none>";
    }

    private MessageHelper() {
    }
}
