package lt.eliga.u2020.data.api;

import javax.inject.Inject;

import lt.eliga.u2020.ApplicationScope;
import retrofit.RequestInterceptor;

@ApplicationScope
public final class ApiHeaders implements RequestInterceptor {
    private static final String AUTHORIZATION_PREFIX = "Client-ID";

    private final String authorizationValue;

    @Inject
    public ApiHeaders(@ClientId String clientId) {
        authorizationValue = AUTHORIZATION_PREFIX + " " + clientId;
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Authorization", authorizationValue);
    }
}
