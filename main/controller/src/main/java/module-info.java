open module com.blb.controller {
    requires spring.beans;
    requires spring.web;
    requires com.blb.service;
    requires com.blb.dto;
    requires com.blb.entity;
    requires org.apache.logging.log4j;

    exports com.blb.controller;
}