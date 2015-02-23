package lt.eliga.u2020;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by eliga on 23/02/15.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {
}

