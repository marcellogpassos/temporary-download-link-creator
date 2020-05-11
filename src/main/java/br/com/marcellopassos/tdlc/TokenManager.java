package br.com.marcellopassos.tdlc;

import br.com.marcellopassos.tdlc.exceptions.TokenManagerException;
import br.com.marcellopassos.tdlc.helpers.DefaultCryptographyHelper;
import br.com.marcellopassos.tdlc.helpers.DefaultEncodingHelper;
import br.com.marcellopassos.tdlc.helpers.ICryptographyHelper;
import br.com.marcellopassos.tdlc.helpers.IEncodingHelper;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TokenManager<R extends Serializable, U extends Serializable> implements ITokenManager<R, U> {

    private final String cryptographyKey;

    private final Long expiration;

    private final ICryptographyHelper cryptographyHelper;

    private final IEncodingHelper encodingHelper;

    public TokenManager(String cryptographyKey, Long expiration) {
        this(cryptographyKey, expiration, new DefaultCryptographyHelper(), new DefaultEncodingHelper());
    }

    public TokenManager(String cryptographyKey, Long expiration, ICryptographyHelper cryptographyHelper) {
        this(cryptographyKey, expiration, cryptographyHelper, new DefaultEncodingHelper());
    }

    public TokenManager(String cryptographyKey, Long expiration, IEncodingHelper encodingHelper) {
        this(cryptographyKey, expiration, new DefaultCryptographyHelper(), encodingHelper);
    }

    public TokenManager(String cryptographyKey, Long expiration, ICryptographyHelper cryptographyHelper,
                        IEncodingHelper encodingHelper) {
        this.cryptographyKey = cryptographyKey;
        this.expiration = expiration;
        this.cryptographyHelper = cryptographyHelper;
        this.encodingHelper = encodingHelper;
    }

    @Override
    public String generate(Class resourceClass, R resourceId) {
        return this.generate(new TokenPayload(resourceClass, resourceId));
    }

    @Override
    public String generate(Class resourceClass, R resourceId, U userId) {
        return this.generate(new TokenPayload(resourceClass, resourceId, userId));
    }

    @Override
    public String generate(Identifiable<R> resource) {
        return this.generate(new TokenPayload<>(resource));
    }

    @Override
    public String generate(Identifiable<R> resource, Identifiable<U> user) {
        return this.generate(new TokenPayload(resource, user));
    }

    @Override
    public String generate(TokenPayload<R, U> tokenPayload) {
        byte[] tokenPayloadBytes = SerializationUtils.serialize(tokenPayload);
        byte[] encryptedToken = this.cryptographyHelper.encrypt(tokenPayloadBytes, this.cryptographyKey.getBytes());
        return this.encodingHelper.encode(encryptedToken);
    }

    @Override
    public TokenPayload<R, U> evaluate(String token) {
        TokenPayload<R, U> tokenPayload = this.getTokenPayload(token);
        this.validateCreatedAt(tokenPayload);
        return tokenPayload;
    }

    @Override
    public TokenPayload<R, U> evaluate(String token, U requestUserId) {
        TokenPayload<R, U> tokenPayload = this.getTokenPayload(token);
        this.validateCreatedAt(tokenPayload);
        this.validateRequestUser(tokenPayload, requestUserId);
        return tokenPayload;
    }

    @Override
    public TokenPayload<R, U> evaluate(String token, Identifiable<U> user) {
        return this.evaluate(token, user.getId());
    }

    private TokenPayload<R, U> getTokenPayload(String token) {
        try {
            byte[] encryptedToken = this.encodingHelper.decode(token);
            byte[] tokenPayloadBytes = this.cryptographyHelper.decrypt(encryptedToken, this.cryptographyKey.getBytes());
            return SerializationUtils.deserialize(tokenPayloadBytes);
        } catch (Exception ex) {
            throw new TokenManagerException("Falha ao tentar obter o conteúdo do token.", ex);
        }
    }

    private void validateCreatedAt(TokenPayload<R, U> tokenPayload) {
        if (LocalDateTime.now().isAfter(tokenPayload.getCreatedAt().plus(this.expiration, ChronoUnit.MILLIS))) {
            throw new TokenManagerException("Token expirado!");
        }
    }

    private void validateRequestUser(TokenPayload<R, U> tokenPayload, U requestUserId) {
        if (!tokenPayload.isPermissionGranted(requestUserId)) {
            throw new TokenManagerException("Acesso negado!");
        }
    }

}
