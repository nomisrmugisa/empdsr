package org.pdsr.controller;

import org.pdsr.dhis2.DHIS2AuthService;
import org.pdsr.dhis2.OrganisationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FacilitySelectionController {

    @Autowired
    private DHIS2AuthService dhis2AuthService;

    @GetMapping("/select-facility")
    public String showFacilitySelection(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String password = (String) auth.getCredentials();

        List<OrganisationUnit> facilities = dhis2AuthService.getUserFacilities(username, password);
        
        if (facilities.isEmpty()) {
            model.addAttribute("error", "No facilities assigned to your DHIS2 account.");
            return "login";
        }

        if (facilities.size() == 1) {
            session.setAttribute("selectedFacility", facilities.get(0));
            return "redirect:/dashboard";
        }

        model.addAttribute("facilities", facilities);
        return "facility-selection";
    }

    @PostMapping("/select-facility")
    public String selectFacility(@RequestParam("facilityId") String facilityId, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String password = (String) auth.getCredentials();

        List<OrganisationUnit> facilities = dhis2AuthService.getUserFacilities(username, password);
        OrganisationUnit selected = facilities.stream()
                .filter(f -> f.getId().equals(facilityId))
                .findFirst()
                .orElse(null);

        if (selected != null) {
            session.setAttribute("selectedFacility", selected);
            return "redirect:/dashboard";
        }

        return "redirect:/select-facility?error";
    }
}
