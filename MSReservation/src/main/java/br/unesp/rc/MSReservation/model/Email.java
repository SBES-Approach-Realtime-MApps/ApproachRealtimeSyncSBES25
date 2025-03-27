package br.unesp.rc.MSReservation.model;

public class Email implements Notification {
    private String providerName;

    public String getProviderName() {
        return providerName;
    }    

    public void setProviderName(String providerName) {    
        this.providerName = providerName;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("Enviando email: " + message);
    }
}
