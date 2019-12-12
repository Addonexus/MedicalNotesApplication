package nsa.group4.medical.service;

import nsa.group4.medical.domains.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    void setWardIdForUser(Long wardId, Long id);
}
