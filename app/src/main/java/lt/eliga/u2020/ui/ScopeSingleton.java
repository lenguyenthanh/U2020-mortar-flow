package lt.eliga.u2020.ui;

import javax.inject.Scope;

@Scope
public @interface ScopeSingleton {
   Class<?> value();
}
