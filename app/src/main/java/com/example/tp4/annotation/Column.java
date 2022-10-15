package com.example.tp4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifica el nombre del campo de la base de datos
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * Nombre del campo
     */
    String value();

    /**
     * Tipo de campo
     * @return
     */
    Class<?> type() default String.class;

    /**
     * La longitud del campo
     * @return
     */
    int length() default 0;

}
