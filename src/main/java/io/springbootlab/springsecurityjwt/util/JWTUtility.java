package io.springbootlab.springsecurityjwt.util;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtility {

    private String SECRET_KEY="secret";

    long nowMillis=System.currentTimeMillis();
    long ttMillis=System.currentTimeMillis()+1000*60*60*10;

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    //
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    //get user info from UserDetails object and create token
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userDetails.getUsername());
    }

    //create token by builder pattern
    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(ttMillis))
                .signWith(SignatureAlgorithm.ES256,SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
