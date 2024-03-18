package ru.alexanna.oikserver.models;

import ru.alexanna.oikserver.services.ReceptionService;


public class MainModel {
    protected final ReceptionService receptionService = new ReceptionService();

    public void startAllReceivers() {
       /* Receiver receiver = new EquipmentOperationReceiver();
        receiver.setPortParams("COM2", 9600, false);
        receiver.setAddresses(List.of(9, 10, 11, 12, 13, 14, 15, 16));*/

//        receptionService.addReceiver(receiver);
        receptionService.startAllReceivers();
    }

    public void stopAllReceivers() {
        receptionService.stopAllReceivers();
    }
}
