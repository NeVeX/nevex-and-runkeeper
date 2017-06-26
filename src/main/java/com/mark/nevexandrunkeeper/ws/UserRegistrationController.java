package com.mark.nevexandrunkeeper.ws;

import com.mark.nevexandrunkeeper.util.APIUtil;
import com.mark.nevexandrunkeeper.model.entity.UserEntity;
import com.mark.nevexandrunkeeper.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by NeVeX on 7/5/2016.
 */
@Controller
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @Value("${oauth.runkeeper-register-url}")
    private String runKeeperOauthRegisterUrl;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @RequestMapping(value = {"/", "/signup"}, method = RequestMethod.GET)
    public String getShowResource(Model model) {
        model.addAttribute("runkeeper_oauth_register_url", runKeeperOauthRegisterUrl);
        return "signup";
    }

    @RequestMapping(value = {"/register", "/register/"}, method = RequestMethod.GET)
    public String registerNewUser( /* @RequestParam(name = "code", required = false) String code, */
                                         @RequestParam Map<String, String> allRequestParams,
                                         HttpServletRequest request,
                                         RedirectAttributes redirectAttributes) {
        /**
         *
         * So this is fucked up, the RequestParam is not able to grab individual query parameters, but
         * using the general map works. Even when injecting the httpServletRequest, has the params.
         * Something weird and fucked up is happening - could be the dispatcher/viewModel/filters....god damn
         *
         *
         */
        String code = APIUtil.getString(allRequestParams, "code");

        if (StringUtils.isEmpty(code)) {
            return "redirect:/error";
        }
//        UserRegistration ur = new UserRegistration(code, request.getRequestURL().toString());
        UserEntity ue = userRegistrationService.register(code);
        if ( ue != null ) {
//        redirectAttributes.addFlashAttribute("oauth_code", code);
            redirectAttributes.addFlashAttribute("unregister_uri", "/unregister?code=" + code);
            redirectAttributes.addFlashAttribute("user", ue);
            return "redirect:/welcome";
        }
        return "redirect:/error";
    }


    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String welcome(ModelMap model) {
        UserEntity ue = (UserEntity) model.get("user");
        if ( ue != null ) {
            model.addAttribute("name", ue.getName());
        }
        return "welcome";
    }

    @RequestMapping(value = "unregister", method = RequestMethod.GET)
    public String unregister(@RequestParam Map<String, String> allRequestParams) {
        String code = APIUtil.getString(allRequestParams, "code");
        if ( StringUtils.hasText(code)) {
            userRegistrationService.unregister(code);
        }
        return "redirect:/signup";
    }


}
