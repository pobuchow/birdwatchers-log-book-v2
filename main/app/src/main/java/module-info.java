open module com.blb.app {
    requires spring.context;
    requires spring.beans;
    requires spring.security.core;
    requires spring.security.config;
    requires com.blb.service;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.webmvc;
    requires java.sql;
    requires spring.data.jpa;
    requires com.blb.controller;

    exports com.blb.app;
}