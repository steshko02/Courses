package com.example.coursach.security.filters;

import com.example.coursach.config.properties.JwtProperties;
import com.example.coursach.dto.error.ErrorMessageDto;
import com.example.coursach.dto.security.LoginRequestDto;
import com.example.coursach.dto.user.security.JwtResponseDto;
import com.example.coursach.exception.user.InvalidUsernameOrPasswordException;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.security.utils.JwtTokenProvider;
import com.example.coursach.service.ErrorMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/login", HttpMethod.POST.name());

    private final ErrorMessageService errorMessageService;

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtProperties jwtProperties;

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(
            ErrorMessageService errorMessageService,
            JwtTokenProvider jwtTokenProvider,
            JwtProperties jwtProperties,
            ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.errorMessageService = errorMessageService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
    }


    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        LoginRequestDto requestDto = obtainLoginRequestDto(request);

        UsernamePasswordAuthenticationToken authRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        requestDto.getEmail().trim(),
                        requestDto.getPassword()
                );

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private LoginRequestDto obtainLoginRequestDto(HttpServletRequest request) throws IOException {
        return Optional.of(objectMapper.readValue(request.getInputStream(), LoginRequestDto.class))
                .orElse(LoginRequestDto.EMPTY);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {

        AuthorizedUser userDetails = (AuthorizedUser) authResult.getPrincipal();
        String tokenWithBearerPrefix = jwtTokenProvider.createTokenWithBearerPrefix(userDetails);
        response.addHeader(
                jwtProperties.getAccessTokenKey(),
                tokenWithBearerPrefix
        );

        JwtResponseDto build = JwtResponseDto.builder().jwt(tokenWithBearerPrefix).build();
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(build);

        response.getWriter().write(json);
        response.flushBuffer();
    }


    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException {

        ErrorMessageDto dto =
                errorMessageService.getMessage(new InvalidUsernameOrPasswordException());

        String errorMessage = objectMapper.writeValueAsString(dto);

        response.setStatus(dto.getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.print(errorMessage);
    }
}
