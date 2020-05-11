package br.com.marcellopassos.tdlc;

import java.io.Serializable;

public interface ITokenManager<R extends Serializable, U extends Serializable> {

    String generate(Class resourceClass, R resourceId);

    String generate(Class resourceClass, R resourceId, U userId);

    String generate(Identifiable<R> resource);

    String generate(Identifiable<R> resource, Identifiable<U> user);

    String generate(TokenPayload<R, U> tokenPayload);

    TokenPayload<R, U> evaluate(String token);

    TokenPayload<R, U> evaluate(String token, U requestUserId);

    TokenPayload<R, U> evaluate(String token, Identifiable<U> user);

}
