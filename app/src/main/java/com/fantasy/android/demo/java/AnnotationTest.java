package com.fantasy.android.demo.java;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AnnotationTest {

    @Target(ElementType.TYPE)
     public @interface Table {

        /**

         * 数据表名称注解，默认值为类名称

         * @return

         */

        public String tableName() default "className";

    }

    @Target(ElementType.FIELD)
    public @interface NoDBColumn {

    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Column {

        public String name() default "fieldName";

        public String setFuncName() default "setField";

        public String getFuncName() default "getField";

        public boolean defaultDBValue() default false;

    }


    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Column2 {

        public String name() default "fieldName";

        public String setFuncName() default "setField";

        public String getFuncName() default "getField";

        public boolean defaultDBValue() default false;

    }

    @Inherited
    public @interface Greeting {

        public enum FontColor{ BULE,RED,GREEN};

        String name();

        FontColor fontColor() default FontColor.GREEN;

    }

}
