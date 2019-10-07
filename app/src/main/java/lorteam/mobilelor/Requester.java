package lorteam.mobilelor;

/**
 * Created by Broookah on 12/8/2017.
 */

public class Requester {
    private String email , password, FName,LName,address,phone;
    public Requester(){
        email="";
        password="";
        FName="";
        LName="";
        address="";
        phone="";
    }
    public  Requester(String email,String password, String FName,String LName,String address,String phone){
        this.email=email;
        this.password=password;
        this.FName=FName;
        this.LName=LName;
        this.address=address;
        this.phone=phone;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setFName(String FName){
        this.FName=FName;
    }

    public void setLName(String LName){
        this.LName=LName;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getEmail(){
        return  email;
    }

    public String getPassword(){
        return  password;
    }

    public String getFName(){
        return  FName;
    }
    public String getLName(){
        return  LName;
    }
    public String getAddress(){
        return  address;
    }
    public String getPhone(){
        return  phone;
    }
}
