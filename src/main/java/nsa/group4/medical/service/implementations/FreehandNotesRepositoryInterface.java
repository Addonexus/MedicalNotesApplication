package nsa.group4.medical.service.implementations;

import nsa.group4.medical.service.events.FreehandNotesAdded;

public interface FreehandNotesRepositoryInterface {
    public void save(FreehandNotesAdded freehandNotesAdded);
}
