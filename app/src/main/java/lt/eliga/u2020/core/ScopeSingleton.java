package lt.eliga.u2020.core;

import javax.inject.Scope;

@Scope
public @interface ScopeSingleton {
   Class<?> value();
}
