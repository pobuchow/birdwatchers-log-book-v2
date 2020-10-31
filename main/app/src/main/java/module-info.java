module com.blb.app {
    requires spring.context;
    requires spring.beans;
    requires spring.security.core;
    requires spring.security.config;
    requires com.blb.service;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.webmvc;

    exports com.blb.app;
}