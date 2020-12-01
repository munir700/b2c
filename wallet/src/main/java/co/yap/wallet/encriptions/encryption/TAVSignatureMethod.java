package co.yap.wallet.encriptions.encryption;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * RSA-SHA1 signature method. The RSA-SHA1 signature method uses the RSASSA-PKCS1-v1_5 signature algorithm as defined in RFC3447
 * section 8.2 (more simply known as PKCS#1), using SHA-1 as the hash function for EMSA-PKCS1-v1_5.
 *
 * <p>
 * The OAuth 1.0 Protocol <a href="https://tools.ietf.org/html/rfc5849">RFC 5849</a> is obsoleted by the OAuth 2.0 Authorization Framework <a href="https://tools.ietf.org/html/rfc6749">RFC 6749</a>.
 *
 * @author Irfan Arshad
 */

public class TAVSignatureMethod {

    /**
     * The name of this RSA-SHA1 signature method ("RSA-SHA256").
     */
    public static final String SIGNATURE_NAME = "RSA-SHA256";
    // Signing Algorithm
    private static final String SIGNING_ALGORITHM = "SHA256withRSA";

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    /**
     * Construct a RSA-SHA1 signature method with the given RSA-SHA1 public/private key pair.
     *
     * @param privateKey The private key.
     * @param publicKey  The public key.
     */
    public TAVSignatureMethod(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /**
     * Construct a RSA-SHA1 signature method with the given RSA-SHA1 private key.  This constructor is to be
     * used by the consumer (who has access to its own private key).
     *
     * @param key The key.
     */
    public TAVSignatureMethod(PrivateKey key) {
        this(key, null);
    }

    /**
     * Construct a RSA-SHA1 signature method with the given RSA-SHA1 public key.  This constructor is to be
     * used by the provider (who has access to the public key of the consumer).
     *
     * @param key The key.
     */
    public TAVSignatureMethod(PublicKey key) {
        this(null, key);
    }

    /**
     * The name of this RSA-SHA1 signature method ("RSA-SHA1").
     *
     * @return The name of this RSA-SHA1 signature method.
     */
    public String getName() {
        return SIGNATURE_NAME;
    }

    /**
     * The Signature Base String is signed using the Consumer’s RSA private key per RFC3447 section 8.2.1, where K is the Consumer’s RSA private key,
     * M the Signature Base String, and S is the result signature octet string:
     * <p>
     * {@code S = RSASSA-PKCS1-V1_5-SIGN (K, M) }
     * <p>
     * oauth_signature is set to S, first base64-encoded per RFC2045 section 6.8, then URL-encoded per Parameter Encoding.
     *
     * @param signatureBaseString The signature base string.
     * @return The signature.
     * @throws UnsupportedOperationException If there is no private key.
     */
    public String sign(String signatureBaseString) {
        if (privateKey == null) {
            throw new UnsupportedOperationException("Cannot sign the base string: no private key supplied.");
        }

        try {
            Signature signer = Signature.getInstance(SIGNING_ALGORITHM);
            signer.initSign(privateKey);
            signer.update(signatureBaseString.getBytes(StandardCharsets.UTF_8));
            byte[] signatureBytes = signer.sign();
            signatureBytes = Base64.encode(signatureBytes,Base64.DEFAULT);
            return new String(signatureBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Verify the signature of the given signature base string. The signature is verified by generating a new request signature octet string, and comparing it
     * to the signature provided by the Consumer, first URL-decoded per Parameter Encoding, then base64-decoded per RFC2045 section 6.8. The signature is
     * generated using the request parameters as provided by the Consumer, and the Consumer Secret and Token Secret as stored by the Service Provider.
     *
     * @param signatureBaseString The signature base string.
     * @param signature           The signature.
     * @throws InvalidSignatureException     If the signature is invalid for the specified base string.
     * @throws UnsupportedOperationException If there is no public key.
     */
    public void verify(String signatureBaseString, String signature) throws InvalidSignatureException {
        if (publicKey == null) {
            throw new UnsupportedOperationException("A public key must be provided to verify signatures.");
        }
        try {
            byte[] signatureBytes = Base64.decode(signature.getBytes(StandardCharsets.UTF_8),Base64.DEFAULT);
            Signature verifier = Signature.getInstance(SIGNING_ALGORITHM);
            verifier.initVerify(publicKey);
            verifier.update(signatureBaseString.getBytes(StandardCharsets.UTF_8));
            if (!verifier.verify(signatureBytes)) {
                throw new InvalidSignatureException("Invalid signature for signature method " + getName());
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * The private key.
     *
     * @return The private key.
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * The private key.
     *
     * @return The private key.
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }
}