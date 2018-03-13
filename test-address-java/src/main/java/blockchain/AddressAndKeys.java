package blockchain;

import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

/*
To generate bitcoin private key in base58 format:
bx seed | bx ec-new | bx ec-to-wif
 */

public class AddressAndKeys {

    public static ECKey getPrivateKey(String privateKey, NetworkParameters params) {
        // Decode the private key from Satoshis Base58 variant. If 51 characters long then it's from Bitcoins
        // dumpprivkey command and includes a version byte and checksum, or if 52 characters long then it has
        // compressed pub key. Otherwise assume it's a raw key.
        ECKey key;
        if (privateKey.length() == 51 || privateKey.length() == 52) {
            DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(params, privateKey);
            key = dumpedPrivateKey.getKey();
        } else {
            BigInteger privKey = Base58.decodeToBigInteger(privateKey);
            key = ECKey.fromPrivate(privKey);
        }
        return key;
    }

    public static String calculatePublicKey(ECKey ecKey, NetworkParameters params) {

        return ecKey.getPublicKeyAsHex();
    }

    public static String calculateAddress(ECKey ecKey, NetworkParameters params) {

        return ecKey.toAddress(params).toString();
    }

    public static void main(String[] args) {

        String defaultPrimaryKey = "KzHSyczvZPWBsXSZfsKEPVffwb21iEXFSjmiNFZbFXww1pVApzJz";
        String pk;
        // Assumes main network not testnet. Make it selectable.
        NetworkParameters params = MainNetParams.get();

        if (args.length < 1) {
            System.out.println("Private key is needed as first argument on command line!");
            System.out.println(String.format("Default Private key is used: %s", defaultPrimaryKey));
            pk = defaultPrimaryKey;
        }
        else {
            pk = args[0];
        }

        ECKey privateKey = getPrivateKey(pk, params);
        System.out.println("PublicKey from private key is: " + calculatePublicKey(privateKey, params));
        System.out.println("Address from private key is: " + calculateAddress(privateKey, params));
        System.exit(0);

    }
}
