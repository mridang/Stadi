package com.mridang.stadissa.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/*
 * This is a helper class to make network requests to fetch pages
 */
public class Network {

    /*
     * This class is a custom HTTP response intercepter that will decompress
     * the GZIPped response
     */
    class Decompressor implements HttpResponseInterceptor {

        /*
         * @see org.apache.http.HttpResponseInterceptor#process(org.apache.http.
         * HttpResponse, org.apache.http.protocol.HttpContext)
         */
        public void process(HttpResponse hreResponse, HttpContext hctContext) throws HttpException, IOException {

            HttpEntity entity = hreResponse.getEntity();

            if (entity != null) {

                Header ceheader = entity.getContentEncoding();

                if (ceheader != null) {

                    HeaderElement[] codecs = ceheader.getElements();

                    for (int i = 0; i < codecs.length; i++) {

                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {

                            hreResponse.setEntity(new HttpEntityWrapper(entity) {

                                @Override
                                public InputStream getContent() throws IOException, IllegalStateException {

                                    return new GZIPInputStream(wrappedEntity.getContent());

                                }

                                @Override
                                public long getContentLength() {

                                    return -1;

                                }

                            });

                            return;

                        }

                    }

                }

            }

        }

    }

    /*
     * This method makes a HTTP GET request and is generally used for logging
     * in.
     *
     * @param  strUrl    the URL of the page to which to GET
     * @param  lstParams a list of name-value pairs to post
     * @return String    the results of the GET request
     */
    public String doGet(String strUrl, List<NameValuePair> lstParams) throws Exception {

        Integer intTry = 0;

        while (intTry < 3) {

            intTry += 1;

            try {

                String strResponse = null;

                HttpGet htpGet = new HttpGet(strUrl);
                htpGet.addHeader("Accept-Encoding", "gzip");
                htpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20100101 Firefox/15.0.1");
                DefaultHttpClient dhcClient = new DefaultHttpClient();
                dhcClient.addResponseInterceptor(new Decompressor(), 0);
                HttpResponse resResponse = dhcClient.execute(htpGet);

                strResponse = EntityUtils.toString(resResponse.getEntity());

                return strResponse;

            } catch (Exception e) {

                //TODO Handle the 404 and 500 Exceptions

                if (intTry < 3) {

                    Log.d("helpers.Network", String.format("Attempt #%d", intTry));

                 } else {

                     e.printStackTrace();
                     throw e;

                 }

            }

        }

        return null;

    }

}