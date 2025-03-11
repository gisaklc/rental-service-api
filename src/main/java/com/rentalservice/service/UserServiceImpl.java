package com.rentalservice.service;

import com.rentalservice.exception.DuplicatedTupleException;
import com.rentalservice.jwt.InvalidTokenException;
import com.rentalservice.jwt.JwtService;
import com.rentalservice.model.AccessToken;
import com.rentalservice.model.entity.User;
import com.rentalservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        var existingUser = getByEmail(user.getEmail());

        if (existingUser != null) {
            throw new DuplicatedTupleException("Usuário já existe com o email fornecido.");
        }

        encodePassword(user);
        return userRepository.save(user);
    }

    @Override
    public AccessToken authenticate(String email, String password) {

        var user = getByEmail(email);

        if (user == null) {
            throw new InvalidTokenException("Usuário inativo ou não encontrado.");
        }


        //verfica se a senha bate ccom a do banco
        boolean matches = passwordEncoder.matches(password, user.getPassword());

        if(matches){//se a senha bate gera o token
            return jwtService.generateToken(user);
        }

        return null;
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private void encodePassword(User user){
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
    }

}
