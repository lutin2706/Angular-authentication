package be.formation.backend.filter;

import be.formation.backend.model.dto.UserFormDTO;
import be.formation.backend.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

// Filtre pour l'autentification des utilisateurs
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final String secret;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String secret) {
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
        this.secret = secret;
    }

    @Override
    // On vérifie si l'utilisateur peut se connecter et l'identifie
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserFormDTO userFormDTO = new ObjectMapper().readValue(request.getInputStream(), UserFormDTO.class); // récupération du body de la requête et extraction des infos dans le UserFormDTO
            return authenticationManager                                        // On demande à l'authenticationManager d'authentifier le User
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            userFormDTO.username,
                            userFormDTO.password,
                            Collections.emptyList()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    // Que se passe-t-il en cas de succès de l'authentification
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()                                           // Construction du Token
                .setSubject(((User)authResult.getPrincipal()).getUsername())
                .claim("authorities",authResult.getAuthorities()            // Ajout de toutes les infos qu'on veut (clé/valeur)
                        .stream()
                        .map(a -> a.getAuthority())
                        .collect(Collectors.toList()))
                .claim("infoSupplementaire", "super info")
                .setExpiration(new Date(System.currentTimeMillis()+864000000))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
    }
}
