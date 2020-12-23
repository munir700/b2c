package co.yap.wallet.encriptions.encryption;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * A POJO for storing the TAV Signature configuration.
 */
public class TAVSignatureConfig {
    public StringBuilder includedFieldsInOrder = new StringBuilder();
    public StringBuilder concatenatedData = new StringBuilder();
    public String dataValidUntilTimestamp;
    PrivateKey privateKey;
    PublicKey publicKey;

    protected TAVSignatureConfig() {

    }
}
