package com.srikar.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //Inject the required dependencies
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // get JWT token from Http request
        String token = getJwtFromToken(request);

        //Validate Token
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){

            //retrieve username from token
            String username = jwtTokenProvider.getUsernameFromJWT(token);

            //load user associated with token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //set info to Spring Security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
           filterChain.doFilter(request, response);
    }

    //Bearer <accessToken>
    private String getJwtFromToken(HttpServletRequest request){

        //Todo - bearerToken returns null ; need to debug
        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        System.out.println("******Error****");
        return null;
    }
}
