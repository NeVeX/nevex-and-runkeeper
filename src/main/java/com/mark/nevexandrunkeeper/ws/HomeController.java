package com.mark.nevexandrunkeeper.ws;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mark Cunningham on 6/27/2017.
 */
@Controller
@RequestMapping("/")
class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String getHomeView() {
        return ControllerConstants.LANDING_PAGE;
    }
}
