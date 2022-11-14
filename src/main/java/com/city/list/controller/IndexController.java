package com.city.list.controller;

import com.city.list.consts.ErrorConst;
import com.city.list.exception.ResourceNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Error controller to handle invalid resource path requests
 * */
@RestController
public class IndexController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        throw new ResourceNotFoundException(ErrorConst.RESOURCE_PATH_NOT_FOUND_ERROR_MESSAGE, null);
    }

}
