package com.mark.nevexandrunkeeper.ws;

import com.mark.nevexandrunkeeper.exception.APIException;
import com.mark.nevexandrunkeeper.model.entity.AdminCommentJobRunEntity;
import com.mark.nevexandrunkeeper.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

/**
 * Created by NeVeX on 7/5/2016.
 */
@Controller
@RequestMapping("/admin/schedule/job")
public class AdminJobsController {

    private static final Logger LOGGER = Logger.getLogger(AdminJobsController.class.getName());

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "comments", method = RequestMethod.GET)
    public @ResponseBody String runCommentsJob() {
        AdminCommentJobRunEntity ae = adminService.runCommentsJob();
        if ( ae != null ) {
            LOGGER.info(ae.toString());
            return "Job completed. [" + ae.toString() + "]";
        } else {
            String msg = "Job did not complete correctly.";
            LOGGER.warning(msg);
            return msg;
        }
    }


}
