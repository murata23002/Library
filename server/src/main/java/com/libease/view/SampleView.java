package com.libease.view;


public class SampleView {
    public String render() {
        return "./api works. Try <a href='./api/getMessage'>./api/getMessage</a>";
    }

    public String renderJsonString() {
        return "{ \"message\": \"The board is green!\" }";
    }
}
