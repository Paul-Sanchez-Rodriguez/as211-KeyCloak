package com.example.identityservice.handler;

import com.example.identityservice.dto.CreatedUserDto;
import com.example.identityservice.dto.LoginDto;
import com.example.identityservice.dto.TokenDto;
import com.example.identityservice.entity.User;
import com.example.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthHandler {
    private final UserService userService;

    public Mono<ServerResponse> login(ServerRequest request){
        Mono<LoginDto> dtoMono = request.bodyToMono(LoginDto.class);
        return  dtoMono
                .flatMap(userService::login)
                .flatMap(tokenDto ->
                        ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenDto.getToken()) // Agrega el encabezado personalizado aquí
                        .bodyValue(tokenDto));
    }

    public Mono<ServerResponse> create(ServerRequest request){
        Mono<CreatedUserDto> dtoMono = request.bodyToMono(CreatedUserDto.class);
        return  dtoMono
                .flatMap((dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userService.create(dto), User.class)));
    }

}
