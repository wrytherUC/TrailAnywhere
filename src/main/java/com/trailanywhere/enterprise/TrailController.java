package com.trailanywhere.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller will handle all Trail endpoints.
 */
@Controller
public class TrailController {

    /**
     * Handle the root endpoint and return the homepage.
     * @return homepage
     */
    @RequestMapping("/")
    public String index() {
        return "TrailFinder";
    }
}
