package minicla03.coinquylife.DATALAYER.remote.AuthAPI;

public class AuthResult
{
    private AuthStatus statusAuth;
    private String token;

    public AuthResult(AuthStatus statusAuth, String token) {
        this.statusAuth = statusAuth;
        this.token = token;
    }

    public AuthStatus getStatusAuth() {
        return statusAuth;
    }

    public void setStatusAuth(AuthStatus statusAuth) {
        this.statusAuth = statusAuth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}