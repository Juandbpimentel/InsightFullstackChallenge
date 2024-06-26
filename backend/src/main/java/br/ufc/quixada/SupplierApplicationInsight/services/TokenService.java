package br.ufc.quixada.SupplierApplicationInsight.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import br.ufc.quixada.SupplierApplicationInsight.models.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT
                .create()
                .withIssuer("insight-auth-api")
                .withSubject(user.getEmail())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
        return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Error during creating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("insight-auth-api").build().verify(token).getSubject();
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Error during validating token", exception);
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
