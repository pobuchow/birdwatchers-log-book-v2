open module com.blb.service {
    requires spring.beans;
    requires spring.context;
    requires com.blb.repository;
    requires com.blb.dto;
    requires com.blb.entity;
    requires spring.security.core;
    requires java.validation;

    exports com.blb.service;
    exports com.blb.service.exception;
}