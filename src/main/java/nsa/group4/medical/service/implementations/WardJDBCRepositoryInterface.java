package nsa.group4.medical.service.implementations;

import nsa.group4.medical.service.events.WardAdded;

public interface WardJDBCRepositoryInterface {
    public void saveWard(WardAdded wardAdded);
}
