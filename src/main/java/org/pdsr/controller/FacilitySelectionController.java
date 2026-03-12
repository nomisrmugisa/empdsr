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
import java.util.Collections;
import java.util.List;
import java.io.IOException;

import org.pdsr.master.repo.UserTableRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.slave.model.SyncTable;
import org.pdsr.CONSTANTS;
import java.util.Optional;

@Controller
public class FacilitySelectionController {

    @Autowired
    private DHIS2AuthService dhis2AuthService;

    @Autowired
    private UserTableRepository userRepo;

    @Autowired
    private SyncTableRepository syncRepo;

    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/select-facility")
    public String showFacilitySelection(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String password = (auth.getCredentials() != null) ? auth.getCredentials().toString() : null;

        List<OrganisationUnit> facilities = Collections.emptyList();
        
        // Try to get live units from DHIS2 if online
        if (password != null) {
            facilities = dhis2AuthService.getUserFacilities(username, password);
        }
        
        // Fallback to local cache if DHIS2 is unreachable or credentials not available (RememberMe)
        if (facilities.isEmpty()) {
            facilities = getCachedFacilities(username);
        }
        
        if (facilities.isEmpty()) {
            model.addAttribute("error", "No facilities found. Please login while online once to sync your account.");
            return "login";
        }

        if (facilities.size() == 1) {
            OrganisationUnit selected = facilities.get(0);
            session.setAttribute("selectedFacility", selected);
            updateGlobalSync(selected);
            return "redirect:/";
        }

        model.addAttribute("facilities", facilities);
        return "facility-selection";
    }

    @PostMapping("/select-facility")
    public String selectFacility(@RequestParam("facilityId") String facilityId, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String password = (auth.getCredentials() != null) ? auth.getCredentials().toString() : null;

        List<OrganisationUnit> facilities = Collections.emptyList();
        if (password != null) {
            facilities = dhis2AuthService.getUserFacilities(username, password);
        }
        
        if (facilities.isEmpty()) {
            facilities = getCachedFacilities(username);
        }

        OrganisationUnit selected = facilities.stream()
                .filter(f -> f.getId().equals(facilityId))
                .findFirst()
                .orElse(null);

        if (selected != null) {
            session.setAttribute("selectedFacility", selected);
            updateGlobalSync(selected);
            return "redirect:/";
        }

        return "redirect:/select-facility?error";
    }

    private void updateGlobalSync(OrganisationUnit selected) {
        Optional<SyncTable> syncOpt = syncRepo.findById(CONSTANTS.LICENSE_ID);
        if (syncOpt.isPresent()) {
            SyncTable sync = syncOpt.get();
            sync.setSyncName(selected.getName());
            sync.setSyncCode(selected.getId()); // Use DHIS2 ID as code
            syncRepo.save(sync);
        }
    }

    private List<OrganisationUnit> getCachedFacilities(String username) {
        return userRepo.findById(username).map(user -> {
            String json = user.getUnitsJson();
            if (json != null && !json.isEmpty()) {
                try {
                    return mapper.readValue(json, new TypeReference<List<OrganisationUnit>>() {});
                } catch (IOException e) {
                    return Collections.<OrganisationUnit>emptyList();
                }
            }
            return Collections.<OrganisationUnit>emptyList();
        }).orElse(Collections.emptyList());
    }
}
