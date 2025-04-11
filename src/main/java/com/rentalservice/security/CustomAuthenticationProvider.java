package com.rentalservice.security;

import com.rentalservice.model.entity.User;
import com.rentalservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService usuarioService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
     String email = authentication.getName();
     String senhaDigitada = authentication.getCredentials().toString();


        User usuarioEncontrado = usuarioService.getByEmail(email);

        if(usuarioEncontrado == null){
            throw getErroUsuarioNaoEncontrado();
        }

        String senhaCriptografada = usuarioEncontrado.getPassword();

        boolean senhamBatem = encoder.matches(senhaDigitada, senhaCriptografada);

        if(senhamBatem){
            return new CustomAuthentication(usuarioEncontrado);
        }

        throw getErroUsuarioNaoEncontrado();
    }

    private UsernameNotFoundException getErroUsuarioNaoEncontrado() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
