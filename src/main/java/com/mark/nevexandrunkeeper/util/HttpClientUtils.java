package com.mark.nevexandrunkeeper.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.nevexandrunkeeper.exception.APIException;
import com.mark.nevexandrunkeeper.exception.RunKeeperException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NeVeX on 7/13/2016.
 */
public class HttpClientUtils {

    private final static ObjectMapper OM = new ObjectMapper();

    public static <T> T execute(String uri, Map<String, String> headers, String method, Class<T> returnType) throws RunKeeperException {
        return execute(uri, headers, method, null, returnType);
    }

    public static <T> T execute(String uri, Map<String, String> headers, String method, Object body, Class<T> returnType) throws RunKeeperException {
        if ( headers == null ) {
            headers = new HashMap<>();
        }
        try {
            URL url = new URL(uri);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            conn.setConnectTimeout(55000);
            conn.setDoOutput(true);
            conn.setRequestMethod(method);

            if ( body != null ) {
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(OM.writeValueAsString(body));
                writer.close();
            }

            int respCode = conn.getResponseCode();
            if (respCode == HttpURLConnection.HTTP_OK || respCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder jsonSb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonSb.append(line);
                }
                reader.close();

                String jsonString = jsonSb.toString();

                if ( returnType != null) {
                    if (returnType == String.class) {
                        return (T) jsonString;
                    } else {
                        return OM.readValue(jsonString, returnType);
                    }
                }
                return null;
            }
            throw new RunKeeperException("Received a non OK status from the API. \nURI: ["+url+"]. \nHeaders: ["+headers+"]. \nMethod: ["+method+"]. \nReturnType: ["+returnType+"]." +
                    "\nResponse Code: ["+respCode+"]. \nResponse Message: ["+conn.getResponseMessage()+"].");
        } catch (RunKeeperException rke ) {
            throw rke;
        }
        catch (Exception e ) {
            throw new APIException("There was a general problem contacting the API. URI: ["+uri+"]. \nHeaders: ["+headers+"]. \nMethod: ["+method+"]. \nReturnType: ["+returnType+"].", e);
        }
    }
}
