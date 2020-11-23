open module com.blb.controller {
    requires spring.beans;
    requires spring.web;
    requires com.blb.service;
    requires com.blb.dto;
    requires com.blb.entity;

    exports com.blb.controller;
}