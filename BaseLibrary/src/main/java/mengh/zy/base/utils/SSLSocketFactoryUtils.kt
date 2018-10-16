package mengh.zy.base.utils

import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object SSLSocketFactoryUtils {
    /*
     * 默认信任所有的证书
     * todo 最好加上证书认证，主流App都有自己的证书
     * */
    fun createSSLSocketFactory(): SSLSocketFactory {
        lateinit var sslSocketFactory: SSLSocketFactory

        try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(createTrustAllManager()), SecureRandom())
            sslSocketFactory = sslContext.socketFactory
        } catch (e: Exception) {

        }

        return sslSocketFactory
    }


    fun createTrustAllManager(): X509TrustManager {
        lateinit var tm: X509TrustManager
        try {
            tm = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                    //do nothing，接受任意客户端证书
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    //do nothing，接受任意服务端证书
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            }
        } catch (e: Exception) {

        }

        return tm
    }
}
