package com.dev.demo.services;

import com.dev.demo.dto.request.LoginRequest;
import com.nimbusds.jose.JOSEException;

public interface AuthService {

    String login(LoginRequest request) throws JOSEException;
}
