package com.rentalservice.security;

import com.rentalservice.model.entity.User;
import com.rentalservice.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SENHA_PADRAO = "321";

    private final UserService usuarioService;

    @Override // autenticacao pelo GOOGLE
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        User usuario = usuarioService.getByEmail(email);

        if(usuario == null){
            usuario = cadastrarUsuarioNaBase(email);
        }

        authentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private User cadastrarUsuarioNaBase(String email) {
        User usuario;
        usuario = new User();
        usuario.setEmail(email);

        usuario.setName(obterLoginApartirEmail(email));

        usuario.setPassword(SENHA_PADRAO);
        usuario.setRoles(List.of("OPERADOR"));

        usuarioService.saveUser(usuario);
        return usuario;
    }

    private String obterLoginApartirEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
