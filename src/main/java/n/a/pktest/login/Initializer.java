package n.a.pktest.login;

import net.anotheria.anoprise.metafactory.Extension;
import net.anotheria.anoprise.metafactory.MetaFactory;
import net.anotheria.portalkit.services.account.Account;
import net.anotheria.portalkit.services.account.AccountService;
import net.anotheria.portalkit.services.account.AccountServiceException;
import net.anotheria.portalkit.services.account.AccountServiceFactory;
import net.anotheria.portalkit.services.authentication.AuthenticationService;
import net.anotheria.portalkit.services.authentication.AuthenticationServiceFactory;
import net.anotheria.portalkit.services.common.persistence.JDBCPickerConflictResolver;

public class Initializer {
    public static void initialize() throws Exception{
        MetaFactory.addOnTheFlyConflictResolver(new JDBCPickerConflictResolver());

        MetaFactory.addFactoryClass(AccountService.class, Extension.LOCAL, AccountServiceFactory.class);
        MetaFactory.addAlias(AccountService.class, Extension.LOCAL);

        MetaFactory.addFactoryClass(AuthenticationService.class, Extension.LOCAL, AuthenticationServiceFactory.class);
        MetaFactory.addAlias(AuthenticationService.class, Extension.LOCAL);

        //create users
        createContent();
    }

    public static void createContent() throws Exception{
        AccountService accountService = MetaFactory.get(AccountService.class);
        AuthenticationService authenticationService = MetaFactory.get(AuthenticationService.class);
        Account account1 = new Account();
        account1.setEmail("leon@anotheria.net");
        account1.setName("leon");

        Account created = null;
        try{
            created = accountService.createAccount(account1);
            System.out.println("Created account");
            authenticationService.setPassword(created.getId(), "qweqwe");
        }catch(AccountServiceException e){

        }





    }
}
