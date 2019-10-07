package lorteam.mobilelor;

/**
 * Created by Broookah on 12/8/2017.
 */

public class ReqLOR {

    private String comment;
    private String emailIssuer;
    private String puropse;
    private String CV;
    private String transcript;
    private String status;
    private String reason;
    private String nameReq;
    private String emailRequester;

    public ReqLOR(){
        comment = "";
        emailIssuer = "";
        puropse = "";
        CV ="";
        transcript = "";
        status = "";
        reason = "";
        nameReq="";
        emailIssuer="";
    }
    public ReqLOR (String req, String email, String p, String c, String t, String s, String r, String n, String e){
        comment = req;
        emailIssuer = email;
        puropse = p;
        CV = c;
        transcript = t;
        status = s;
        reason = r;
        nameReq=n;
        emailRequester=e;

    }

    public String getcomment(){
        return comment;
    }

    public String getEmailIssuer(){
        return emailIssuer;
    }

    public String getPuropse(){
        return puropse;
    }

    public String getCV(){
        return CV;
    }

    public String getTranscript(){
        return transcript;
    }

    public String getStatus(){
        return status;
    }

    public String getReason(){
        return reason;
    }
    public String getNameReq(){
        return nameReq;
    }

    public void setStatus(String s){
        status = s;
    }

    public void setReason(String r){
        reason = r;
    }
    public void setcomment(String b){comment=b;}
    public void setEmailIssuer(String emiss){emailIssuer=emiss;}
    public void setPuropse(String pu){puropse=pu;}
    public void setNameReq(String n){nameReq=n;}
    public void setEmailRequester(String e){emailRequester=e;}
}
