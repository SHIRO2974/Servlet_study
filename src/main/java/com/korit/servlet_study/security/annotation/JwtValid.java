package com.korit.servlet_study.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//  ElementType: 어느 타입에 어노테이션을 달지 정하는 타입
@Target({ElementType.METHOD, ElementType.TYPE}) // class 및 method 에도 달 수 있는 annotation
@Retention(RetentionPolicy.RUNTIME) // 런타임 시점에 어노테이션 사용
public @interface JwtValid {


}
