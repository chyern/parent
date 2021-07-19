package com.github.chyern.connect.processor;

import com.github.chyern.common.enums.ChyernErrorEnum;
import com.github.chyern.common.exception.ChyernException;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
public class DefaultConnectProcessor extends AbstractConnectProcessor {

    @Override
    protected String around() throws Exception{
        String result;
        BufferedReader readerBuffer = null;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestMethod(method);
            headers.forEach(urlConnection::addRequestProperty);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            if (body != null) {
                urlConnection.setDoOutput(true);
                try (OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream())) {
                    byte[] bodyByte = new GsonBuilder().create().toJson(body).getBytes(StandardCharsets.UTF_8);
                    outputStream.write(bodyByte, 0, bodyByte.length);
                    outputStream.flush();
                }
            }
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder resultBuffer = new StringBuilder();
                String line;
                InputStream connectionInputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(connectionInputStream, StandardCharsets.UTF_8);
                readerBuffer = new BufferedReader(inputStreamReader);
                while ((line = readerBuffer.readLine()) != null) {
                    resultBuffer.append(line);
                }
                result = resultBuffer.toString();
            } else {
                throw new ChyernException(ChyernErrorEnum.CONNECT_RESULT_ERROR);
            }
        } finally {
            if (readerBuffer != null) {
                readerBuffer.close();
            }
            urlConnection.disconnect();
        }
        return result;
    }
}
