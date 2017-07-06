package com.mark.nevexandrunkeeper.ws;

import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.runkeeper.RegistrationService;
import com.mark.nevexandrunkeeper.runkeeper.exception.RunKeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by NeVeX on 7/5/2016.
 */
@Controller
@RequestMapping("/oauthcallback")
class OAuthCallbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private final RegistrationService registrationService;

    @Autowired
    OAuthCallbackController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public RedirectView registerNewUser(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        LOGGER.info("Received callback for oauth with code [{}]", code);

        if (StringUtils.isEmpty(code)) {
            return new RedirectView(ControllerConstants.ERROR_PAGE);
        }

        String accessToken;
        try {
            accessToken = registrationService.registerNewUser(code);
            LOGGER.info("Successfully registered a new user using oAuthCode [{}]", code);
        } catch (RunKeeperException ex) {
            LOGGER.error("Could not register new user for oauthCode [{}]", code, ex);
            return new RedirectView(ControllerConstants.ERROR_PAGE);
        }

        User user;
        try {
            // Now we have access to this person's account, let's get and save the information
            user = registrationService.createNewUserAccount(accessToken);
        } catch (RunKeeperException ex) {
            LOGGER.error("Could not create an account for access token [{}]", accessToken, ex);
            return new RedirectView(ControllerConstants.ERROR_PAGE);
        }

        // If we get here, then the process was fine - the user is in our system now!
        redirectAttributes.addFlashAttribute("name", user.getName());
        return new RedirectView(ControllerConstants.WELCOME_PAGE);
    }

}
