package br.unesp.rc.ResidentModel.model;


public class Contact {

    private String homePhone;
    private String businessPhone;
    private String cellPhone;
    private String email;

    public String getHomePhone() {
        return homePhone;
    }
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
    public String getBusinessPhone() {
        return businessPhone;
    }
    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }
    public String getCellPhone() {
        return cellPhone;
    }
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact [homePhone=" + homePhone + ", businessPhone=" + businessPhone + ", cellPhone=" + cellPhone + ", email=" + email + "]";
    }
}
