import Money.Coin;
import User.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    User user;

    @Before
    public void before(){
        user = new User();
    }

    @Test
    public void canGetWallet__walletStartWith15Coins(){
        assertEquals(15, user.getWallet().size());
    }

    @Test
    public void canAddCoinsToWallet(){
        user.addCoinToWallet(Coin.DOLLAR);
        user.addCoinToWallet(Coin.QUARTER);
        assertEquals(17, user.getWallet().size());
    }

    @Test
    public void canRemoveCoinsFromWallet__walletHasLessCoinsOnceRemoved(){
        user.addCoinToWallet(Coin.DOLLAR);
        user.addCoinToWallet(Coin.QUARTER);
        user.removeCoinFromWallet(Coin.DOLLAR);
        assertEquals(16, user.getWallet().size());
    }


    @Test
    public void canRemoveCoinsFromWallet__returnsTrueIfSuccessful(){
        user.addCoinToWallet(Coin.DOLLAR);
        user.addCoinToWallet(Coin.QUARTER);
        assertEquals(true, user.removeCoinFromWallet(Coin.DOLLAR));
    }

    @Test
    public void canRemoveCoinsFromWallet__returnsFalseIfWalletIsEmpty(){
        for(int i = 0; i < user.getWallet().size();)
        user.getWallet().remove(0);
        assertEquals(false, user.removeCoinFromWallet(Coin.DOLLAR));
    }
}
