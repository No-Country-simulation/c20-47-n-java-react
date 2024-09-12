package com.FlowBanck.service;

import com.FlowBanck.entity.UserEntity;
import com.FlowBanck.exception.login.AccountLockedException;
import com.FlowBanck.exception.login.InvalidCredentialsException;
import com.FlowBanck.exception.login.TooManyFailedLoginAttemptsException;
import com.FlowBanck.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.getEmail(email)
                .orElseThrow(() ->new InvalidCredentialsException("El correo electrónico y la contraseña no coinciden. Por favor, verifica tus datos e intenta nuevamente."));


        if (userEntity.getState().equalsIgnoreCase("INACTIVO")){
            throw new AccountLockedException("Su cuenta se encuentra deshabilitada");
        }
        else if (userEntity.getState().equalsIgnoreCase("BLOQUEADA")) {
            throw new AccountLockedException("Su cuenta se encuentra bloqueada");
        }
        else if(userEntity.getState().equalsIgnoreCase("BLOQUEADA_POR_INTENTOS")){
            throw new TooManyFailedLoginAttemptsException("Su cuenta se encuentra bloqueada por repetitivos intentos fallido");
        }


        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRol().name())))
                .collect(Collectors.toSet());
        return new User(userEntity.getEmail(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
