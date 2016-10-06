package com.epam.resolvers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component

public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = Logger.getLogger(GlobalHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception exception) {
        logger.error("Exception: ", exception);
        return new ModelAndView("global_error");
    }
}
