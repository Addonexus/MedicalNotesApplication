package nsa.group4.medical.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String email, String password);
}
