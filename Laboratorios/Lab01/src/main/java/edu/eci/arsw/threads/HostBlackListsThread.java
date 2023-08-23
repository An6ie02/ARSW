package edu.eci.arsw.threads;

import java.util.LinkedList;

import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class HostBlackListsThread extends Thread {

    private int inicio;
    private int fin;
    private String ipaddress;
    private int ocurrencesCount;
    private int checkedListsCount;
    private HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
    LinkedList<Integer> blackListOcurrences = new LinkedList<>();
    
    public HostBlackListsThread(int inicio, int fin, String ipaddress) {
        this.inicio = inicio;
        this.fin = fin;
        this.ipaddress = ipaddress;
        this.ocurrencesCount = 0;
        this.checkedListsCount = 0;
    }

    @Override
    public void run() {
        for (int i = inicio; i < fin  && ocurrencesCount < HostBlackListsValidator.BLACK_LIST_ALARM_COUNT; i++) {
            checkedListsCount++;
            if (skds.isInBlackListServer(i, ipaddress)) {
                blackListOcurrences.add(i);
                ocurrencesCount++;
            }
        }
    }

    public int getCheckedListsCount() {
        return checkedListsCount;
    }
    
    public LinkedList<Integer> getBlackListOcurrences() {
        return blackListOcurrences;
    }

}
