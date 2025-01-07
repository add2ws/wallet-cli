import lombok.extern.slf4j.Slf4j;
import org.tron.common.utils.Utils;
import org.tron.core.exception.CancelException;
import org.tron.core.exception.CipherException;
import org.tron.keystore.StringUtils;
import org.tron.protos.Protocol;
import org.tron.walletcli.WalletApiWrapper;
import org.tron.walletserver.WalletApi;

import java.io.IOException;

@Slf4j
public class ApplicationExecutor {

    private final static WalletApiWrapper WalletApiWrapperSingleton = new WalletApiWrapper();

    static final char[] Password = "appleisyours89U".toCharArray();

    public static void register() throws CipherException, IOException {
        WalletApiWrapperSingleton.registerWallet(Password);
    }

    public static WalletApiWrapper login() throws CipherException, IOException {
        WalletApi walletApi = WalletApi.loadWalletFromKeystore();
        walletApi.setLogin();
        WalletApiWrapperSingleton.setWallet(walletApi);
        return WalletApiWrapperSingleton;
    }

    static void importWallet() throws CipherException, IOException {
        login();
        String pri = "xxxxxxxxxxx";
//        String pri = "xxxxxxxxxxxxxxxxxxxxxxxx";
        byte[] priKey = StringUtils.hexs2Bytes(pri.getBytes());
        String s = WalletApiWrapperSingleton.importWallet(Password, priKey);
        System.out.println(s);
    }

    static void getAddress() throws CipherException, IOException {
        WalletApiWrapper walletApiWrapper = login();
        String address = walletApiWrapper.getAddress();
        logger.info("结果是=>\n{}", address);
    }

    static Protocol.Account getAccount() throws CipherException, IOException {

        login();
        Protocol.Account account = WalletApiWrapperSingleton.queryAccount();

        Protocol.Transaction transaction = Protocol.Transaction.getDefaultInstance();

        logger.info("结果是===>\n{}", Utils.formatMessageString(account));
        return account;
    }

    static Protocol.Account queryAccount(String addressBase58) throws CipherException, IOException {
        byte[] bytes = WalletApi.decodeFromBase58Check(addressBase58);
        Protocol.Account account = WalletApi.queryAccount(bytes);
        logger.info("结果是===>\n{}", Utils.formatMessageString(account));
        return account;
    }

    static void getBlock() throws CipherException, IOException {
        Protocol.Block block = WalletApiWrapperSingleton.getBlock(-1);
        logger.info("结果是===>\n{}", Utils.formatMessageString(block));

    }

    static void updatePermission(String ownerBase58, String permissionJSON) throws CipherException, IOException, CancelException {
        byte[] owner = WalletApi.decodeFromBase58Check(ownerBase58);
        login();

        boolean b = WalletApiWrapperSingleton.accountPermissionUpdate(owner, permissionJSON);
        logger.info("结果是===>{}", b);

    }

    static void sendCoin(String ownerBase54, String toBase54, long amount) throws CipherException, IOException, CancelException {
        login();
        boolean b = WalletApiWrapperSingleton.sendCoin(WalletApi.decodeFromBase58Check(ownerBase54), WalletApi.decodeFromBase58Check(toBase54), amount);
        logger.info("结果是===>{}", b);

    }

    static String owner_key = "TMvMriTLkS2fjBKtyixzJUbnjro8TaZzRo";
    static String active_key = "TCrvqd9r2zvZFuNGWEQiivuCcNXZqUBmFr";
    static String tron3_key = "THQRNVstadZ7zpHMfafuoXe7Et4utHyrEn";

    public static void main(String[] args) throws CipherException, IOException, CancelException {
//        queryAccount("TCrvqd9r2zvZFuNGWEQiivuCcNXZqUBmFr");

//        getBlock();

        WalletApi.FilePath = "Wallet";

        sendCoin("TCrvqd9r2zvZFuNGWEQiivuCcNXZqUBmFr", "THQRNVstadZ7zpHMfafuoXe7Et4utHyrEn", 1);

//                register();

//        importWallet();

//        Protocol.Account account = getAccount();
//        logger.info("账户[{}]余额：{}", account.getAccountName(), account.getBalance() / 1000000.0);

//        updatePermission("TCrvqd9r2zvZFuNGWEQiivuCcNXZqUBmFr", "{\"owner_permission\":{\"keys\":[{\"address\":\"TMvMriTLkS2fjBKtyixzJUbnjro8TaZzRo\",\"weight\":1}],\"threshold\":1,\"type\":0,\"permission_name\":\"owner\"},\"active_permissions\":[{\"operations\":\"7fff1fc0033ec30f000000000000000000000000000000000000000000000000\",\"keys\":[{\"address\":\"TMvMriTLkS2fjBKtyixzJUbnjro8TaZzRo\",\"weight\":1}],\"threshold\":1,\"type\":2,\"permission_name\":\"active\"}]}");


    }
}
