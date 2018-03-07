/**
 * 
 */
package mdr.ap.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Parameter / variable annotation denoting a constraint that it will never
 * carry a {@code null} value
 * 
 * @author mross
 */
@Retention(CLASS)
@Target({ FIELD, METHOD, PARAMETER, LOCAL_VARIABLE })
public @interface NotNull {

}
