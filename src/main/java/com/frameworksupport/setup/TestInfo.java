package com.frameworksupport.setup;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestInfo {
  String testRailId() default "none";
  
  String author() default "none";
  
  String zapsecurity() default "none";
}