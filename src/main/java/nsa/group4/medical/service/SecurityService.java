package nsa.group4.medical.service;

public interface SecurityService {
    String findLoggedInByUsername();

    void autoLogin(String email, String password);
}
