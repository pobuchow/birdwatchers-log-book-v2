module com.blb.controller {
    requires spring.beans;
    requires spring.web;
    requires com.blb.service;
    requires com.blb.dto;

    exports com.blb.controller;
}