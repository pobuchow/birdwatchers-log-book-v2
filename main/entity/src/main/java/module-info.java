open module com.blb.entity {
    requires java.persistence;
    requires java.validation;
    requires java.xml.bind;
    requires net.bytebuddy;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.classmate;

    exports com.blb.entity;
    exports com.blb.entity.exception;
}