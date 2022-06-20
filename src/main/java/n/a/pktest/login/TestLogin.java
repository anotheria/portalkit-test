package n.a.pktest.login;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.MetaFactory;
import net.anotheria.portalkit.apis.common.authentication.AccountNotFoundException;
import net.anotheria.portalkit.apis.common.authentication.IllegalCredentialsForLoginException;
import net.anotheria.portalkit.apis.common.authentication.PortalKitAuthenticationAPI;
import net.anotheria.portalkit.apis.common.authentication.PortalKitAuthenticationAPIFactory;
import net.anotheria.portalkit.services.account.Account;
import net.anotheria.portalkit.services.account.AccountService;
import net.anotheria.portalkit.services.common.AccountId;

public class TestLogin {
    public static void main(String[] a) throws Exception{
        Initializer.initialize();
        APIFinder.addAPIFactory(PortalKitAuthenticationAPI.class, new PortalKitAuthenticationAPIFactory());
        //Do we have the user?

        String email = "leon@anotheria.net";
        System.out.println("Do we have the user? "+email);

        AccountService accountService = MetaFactory.get(AccountService.class);
        AccountId accountId = accountService.getAccountIdByEmail(email);
        System.out.println("AccountId : "+accountId);
        Account acc = accountService.getAccount(accountId);
        System.out.println("Account : "+acc);

        PortalKitAuthenticationAPI authenticationAPI = APIFinder.findAPI(PortalKitAuthenticationAPI.class);
        //Try login with wrong user name
        try {
            System.out.print("Login with wrong user name ");
            authenticationAPI.manualLogin("notleon@anotheria.net", "xxxxxxx");
            System.out.println("FAILED (positive result)");
        }catch(AccountNotFoundException e){
            System.out.println("OK");
        }catch(Exception any){
            System.out.println("ERROR");
        }

        //Try login with wrong user name
        try {
            System.out.print("Login with wrong password ");
            authenticationAPI.manualLogin("leon@anotheria.net", "xxxxxxx");
            System.out.println("FAILED (positive result)");
        }catch(IllegalCredentialsForLoginException e){
            System.out.println("OK");
        }catch(Exception any){
            System.out.println("ERROR");
        }

        //Try login with correct user name and password
        try {
            System.out.print("Login with correct password ");
            Account account = authenticationAPI.manualLogin("leon@anotheria.net", "qweqwe");
            System.out.println("OK");
            System.out.println("Account: "+account);
        }catch(Exception any){
            System.out.println("ERROR");
        }


        System.exit(0);

    }
}
