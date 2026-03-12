package org.pdsr.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Profile("mock-dhis2")
public class MockDHIS2Controller {

    @GetMapping("/me")
    public Map<String, Object> me() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", "mock-user-id");
        user.put("username", "admin");
        user.put("displayName", "Mock Admin User");
        
        Map<String, Object> ou1 = new HashMap<>();
        ou1.put("id", "ou-1");
        ou1.put("name", "Mock Facility 1");
        ou1.put("level", 4);
        
        Map<String, Object> ou2 = new HashMap<>();
        ou2.put("id", "ou-2");
        ou2.put("name", "Mock Facility 2");
        ou2.put("level", 3);
        
        user.put("organisationUnits", Arrays.asList(ou1, ou2));
        return user;
    }
}
