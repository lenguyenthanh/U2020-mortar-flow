package lt.eliga.u2020.data.api;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The Imgur application client ID.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ClientId {
}
