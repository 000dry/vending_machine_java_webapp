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
    public void canGetWallet__walletStartsEmpty(){
        assertEquals(0, user.getWallet().size());
    }

    @Test
    public void canAddCoinsToWallet(){
        user.addCoinToWallet(Coin.DOLLAR);
        user.addCoinToWallet(Coin.QUARTER);
        assertEquals(2, user.getWallet().size());
    }

    @Test
    public void canRemoveCoinsFromWallet__walletHasLessCoinsOnceRemoved(){
        user.addCoinToWallet(Coin.DOLLAR);
        user.addCoinToWallet(Coin.QUARTER);
        user.removeCoinFromWallet(Coin.DOLLAR);
        assertEquals(1, user.getWallet().size());
    }

    @Test
    public void canRemoveCoinsFromWallet__removesOnlyTheCorrectCoin(){
        user.addCoinToWallet(Coin.DOLLAR);
        user.addCoinToWallet(Coin.QUARTER);
        user.removeCoinFromWallet(Coin.DOLLAR);
        assertEquals(Coin.QUARTER, user.getWallet().get(0));
    }

    @Test
    public void canRemoveCoinsFromWallet__returnsTrueIfSuccessful(){
        user.addCoinToWallet(Coin.DOLLAR);
        user.addCoinToWallet(Coin.QUARTER);
        assertEquals(true, user.removeCoinFromWallet(Coin.DOLLAR));
    }

    @Test
    public void canRemoveCoinsFromWallet__returnsFalseIfWalletIsEmpty(){
        assertEquals(false, user.removeCoinFromWallet(Coin.DOLLAR));
    }
}
