package org.example.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 어노테이션이 생성될 수 있는 위치를 지정. (메소드의 파라미터로 선언된 객체에서만 사용)
@Retention(RetentionPolicy.RUNTIME) // 해당 애노테이션을 옵션값까지 유지시킨다.
public @interface LoginUser { // 이 파일을 어노테이션 클래스로 지정
}
