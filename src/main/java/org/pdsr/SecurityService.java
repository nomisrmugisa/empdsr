package org.pdsr;

public interface SecurityService {
    
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}