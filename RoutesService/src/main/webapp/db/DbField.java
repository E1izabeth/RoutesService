package main.webapp.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value= ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface DbField {
    String dbType() default "";
    boolean isDbGenerated() default false;
    boolean isReadOnly() default false;
}
