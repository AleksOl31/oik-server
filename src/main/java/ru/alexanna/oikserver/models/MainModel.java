package ru.alexanna.oikserver.models;

import ru.alexanna.oikserver.services.ReceptionService;


public class MainModel {
    protected final ReceptionService receptionService = new ReceptionService();

    public void startAllReceivers() {
        receptionService.startAllReceivers();
    }

    public void stopAllReceivers() {
        receptionService.stopAllReceivers();
    }
}
