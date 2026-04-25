//package com.example.authservice.security;
//
//
//import com.example.authservice.config.JwtConfig;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtTokenProvider {
//    private final JwtConfig jwtConfig;
//    private SecretKey secretKey;
//
//
//    @PostConstruct
//    public void init(){
//        byte[] keyBytes = Base64.getDecoder().decode(jwtConfig.getSecret());
//        this.secretKey = keys.hmacShadKeyFor(keyBytes);
//    }
//
//    // Extract username (email in your case) from token
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    // Extract expiration date
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    // Extract custom claim
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//
//    }
//    public boolean isAccessToken(String token) {
//        return "access".equals(extractClaim(token, c -> c.get("type", String.class)));
//    }
//
//    // Extract all claims
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    //===========================Token Generation======================
//
//    public String generateAccessToken(UserDetails userDetails){
//        Map<String,Object> claims = new HashMap<>();
//
//        claims.put("type","access");
//
//        if(userDetails.getAuthorities()!= null && !userDetails.getAuthorities().isEmpty()) {
//
//            String role = userDetails.getAuthorities().iterator().next().getAuthority();
//            claims.put("role",role);
//        }
//
//        if(UserDetails instanceof CustomUserDetails customUser){
//            claims.put("name",customUser.getName());
//        }
//
//        return createToken(claims,UserDetails.getemail(),);
//
//    }
//
//
//    // Refresh token (7 days)
//    public String generateRefreshToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("type", "refresh");
//        return createToken(claims, userDetails.getUsername(), refreshExpiry);
//    }
//
//    // Common token generator method
//    private String createToken(Map<String, Object> claims, String subject, long expiry) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject) // Subject = username/email
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiry))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String getTokenType(String token) {
//        return extractAllClaims(token).get("type", String.class);
//    }
//
//
//    private Key getSignKey() {
//        byte[] keyBytes = secretKey.getBytes();
//        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
//    }
//
//    // Validate token against UserDetails
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//    public String getTokenType(String token) {
//        return extractAllClaims(token).get("type", String.class);
//    }
//
//
//}
