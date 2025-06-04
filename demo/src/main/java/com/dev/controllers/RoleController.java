package com.dev.controllers;

import com.dev.dto.request.RoleRequest;
import com.dev.dto.response.RoleResponse;
import com.dev.dto.response.SuccessResponse;
import com.dev.helpers.Constants;
import com.dev.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public SuccessResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return new SuccessResponse<>(MessageFormat.format(Constants.API_RESPONSE_MESSAGES.SUCCESS,
                "Login"), roleService.create(request), HttpStatus.CREATED);
    }
}
