package porqueras.ioc.proyectom13appmovil.utilidades;

import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import porqueras.ioc.proyectom13appmovil.R;

/**
 * Clase para la gestión de certificados y cifrado comunicaciones SSL/TLS
 *
 * @author Esteban Porqueras Araque
 */
public class CertificateUtils {

    // Gestión de certificados y cifrado comunicaciones SSL/TLS
    public static void inicializarManejoDeCertidicados(Resources resources) {
        //Obtenemos el certificado dentro de la carpeta raw
        InputStream inputStream = resources.openRawResource(R.raw.demo);
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(inputStream);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Verifica que el certificado del servidor es válido y se corresponde con la clave pública
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        for (X509Certificate cert : chain) {
                            cert.checkValidity();
                            try {
                                cert.verify(cert.getPublicKey());
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (NoSuchProviderException e) {
                                e.printStackTrace();
                            } catch (SignatureException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        //Creamos el objeto SSlContext que usa el objeto de TrustManager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        //Almacenamos los objetos dentro de ApiUtils para que Retrofit los pueda utilizar
        ApiUtils.sslContext = sslContext;
        ApiUtils.trustAllCerts = trustAllCerts;
    }

}
