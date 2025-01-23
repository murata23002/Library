package com.libease.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LibraryCommon {
    public static String getJsoStringFromRequest(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        Stream<String> lines = reader.lines();
        String json = lines.collect(Collectors.joining("\r\n"));
        return json;
    }

    public static int getUserIdFromSession(HttpServletRequest request) {
        int userId = 0;
        HttpSession session = request.getSession();
        Optional<Integer> userIdOpt = Optional.ofNullable((Integer) session.getAttribute("userId"));
        if (userIdOpt.isPresent()) {
            userId = userIdOpt.get();
        }
        return userId;
    }
}
