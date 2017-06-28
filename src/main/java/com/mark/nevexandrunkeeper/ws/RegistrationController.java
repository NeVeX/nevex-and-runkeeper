package com.mark.nevexandrunkeeper.ws;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.runkeeper.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Mark Cunningham on 6/27/2017.
 */
@Controller
class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private final RegistrationService registrationService;
    private final String runKeeperRegisterUrl;

    @Autowired
    RegistrationController(ApplicationProperties applicationProperties,
                           RegistrationService registrationService) {
        this.runKeeperRegisterUrl = applicationProperties.getOauth().getRegisterUrl();
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        LOGGER.info("Signing up a new user using the RunKeeper redirect [{}]", runKeeperRegisterUrl);
        // We just need to redirect
        return "redirect:"+ runKeeperRegisterUrl;
    }

    @RequestMapping(value = "unregister", method = RequestMethod.GET)
    public String unregister(@RequestParam("user_id") Integer userId) {
        if ( userId == null ) {
            LOGGER.warn("No User Id provided so cannot unregister");
            return ControllerConstants.REDIRECT_TO_ERROR_PAGE;
        }
        if (registrationService.unregister(userId)) {
            return ControllerConstants.REDIRECT_TO_HOME_PAGE; // Back to the home page
        }
        return ControllerConstants.REDIRECT_TO_ERROR_PAGE;
    }

}
