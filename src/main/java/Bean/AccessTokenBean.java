package Bean;

import Services.AccessToken.AccessTokenService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;

@ManagedBean
@RequestScoped
public class AccessTokenBean {

    private int count;
    private ArrayList<String> accessToken = new ArrayList<String>();


    public void createAccessToken() {
        if(this.count > 0) {
            AccessTokenService accessTokenService = new AccessTokenService();
            this.accessToken = accessTokenService.getAccessTokensAndInsertThemInToDB(this.count);
        }
    }

    public ArrayList<String> getAccessToken() {
        return this.accessToken;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
