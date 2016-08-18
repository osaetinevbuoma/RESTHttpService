package com.modnsolutions;

import org.grails.web.json.JSONException;
import org.grails.web.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Osaetin Evbuoma (osaetinevbuoma@gmail.com).
 *
 * Make JSON HTTP Requests to remote servers
 */
public class JsonHttpService {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String CHARSET = "UTF-8";
    private static JSONObject jsonObject;

    /**
     * Set header http parameters
     *
     * @param url       Remote URL
     * @param headers   Header parameters
     * @param method    HTTP method
     * @return  HttpURLConnection
     * @throws IOException
     */
    private static HttpURLConnection setHeaders(URL url, Map<String, String> headers,
                                                String method) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();

        return httpURLConnection;
    }

    /**
     * Set header http parameters for secure connections
     *
     * @param url       Remote URL
     * @param headers   Header parameters
     * @param method    HTTP method
     * @return  HttpURLConnection
     * @throws IOException
     */
    private static HttpsURLConnection setSecureHeaders(URL url, Map<String, String> headers,
                                                       String method) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestMethod(method);
        httpsURLConnection.setRequestProperty("User-Agent", USER_AGENT);
        httpsURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpsURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpsURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.connect();

        return httpsURLConnection;
    }

    /**
     * Push output data stream depending on the connection type
     *
     * @param h             http url connection object
     * @param body          body content to post or push
     * @throws IOException
     */
    private static void outputDataStream(Object h, String body) throws IOException {
        if (h instanceof HttpsURLConnection) {
            DataOutputStream dataOutputStream = new DataOutputStream(((HttpsURLConnection) h)
                    .getOutputStream());
            dataOutputStream.writeBytes(body);
            dataOutputStream.flush();
            dataOutputStream.close();
        } else if (h instanceof HttpURLConnection) {
            DataOutputStream dataOutputStream = new DataOutputStream(((HttpURLConnection) h)
                    .getOutputStream());
            dataOutputStream.writeBytes(body);
            dataOutputStream.flush();
            dataOutputStream.close();
        }
    }

    /**
     * Buffer response data and parse to json format
     *
     * @param h             http url connection type
     * @return JSONObject
     * @throws IOException
     */
    private static JSONObject bufferResponse(Object h) throws IOException {
        BufferedReader bufferedReader = null;

        if (h instanceof HttpsURLConnection) {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(((HttpsURLConnection) h).getInputStream()));
        } else if (h instanceof HttpURLConnection) {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(((HttpURLConnection) h).getInputStream()));
        }

        String inputLine;
        StringBuilder stringBuffer = new StringBuilder();

        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuffer.append(inputLine);
        }

        bufferedReader.close();

        if (h instanceof HttpsURLConnection) {
            ((HttpsURLConnection) h).disconnect();
        } else if (h instanceof HttpURLConnection) {
            ((HttpURLConnection) h).disconnect();
        }

        return new JSONObject(stringBuffer.toString());
    }

    /**
     * Make a HTTP POST request to a remote server
     *
     * @param address       Server url address e.g. http://example.com/
     * @param headerParams  Header parameters
     * @param bodyParams    Body content
     * @param ssl           Send over http or https connection (note ssl set to true means address
     *                      must be https)
     * @return  jsonObject  JSON response from remote server
     */
    public static JSONObject post(String address, Map<String, String> headerParams,
                                  String bodyParams, boolean ssl) {
        try {
            URL url = new URL(address);

            if (ssl) {
                HttpsURLConnection httpsURLConnection = setSecureHeaders(url, headerParams,
                        "POST");
                outputDataStream(httpsURLConnection, bodyParams);
                if (httpsURLConnection.getResponseCode() == 200) {
                    jsonObject = bufferResponse(httpsURLConnection);
                }
            } else {
                HttpURLConnection httpURLConnection = setHeaders(url, headerParams, "POST");
                outputDataStream(httpURLConnection, bodyParams);
                if (httpURLConnection.getResponseCode() == 200) {
                    jsonObject = bufferResponse(httpURLConnection);
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
        }

        return jsonObject;
    }

    /**
     * Make a HTTP PUT request to a remote server
     *
     * @param address       Server url address e.g. http://example.com/
     * @param headerParams  Header parameters
     * @param bodyParams    Body content
     * @param ssl           Send over http or https connection (note ssl set to true means address
     *                      must be https)
     * @return  jsonObject  JSON response from remote server
     */
    public static JSONObject put(String address, Map<String, String> headerParams,
                                 String bodyParams, boolean ssl) {
        try {
            URL url = new URL(address);

            if (ssl) {
                HttpsURLConnection httpsURLConnection = setSecureHeaders(url, headerParams, "PUT");
                outputDataStream(httpsURLConnection, bodyParams);
                if (httpsURLConnection.getResponseCode() == 200) {
                    jsonObject = bufferResponse(httpsURLConnection);
                }
            } else {
                HttpURLConnection httpURLConnection = setHeaders(url, headerParams, "PUT");
                outputDataStream(httpURLConnection, bodyParams);
                if (httpURLConnection.getResponseCode() == 200) {
                    jsonObject = bufferResponse(httpURLConnection);
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
        }

        return jsonObject;
    }

    /**
     * Make a HTTP GET request to a remote server
     *
     * @param address       Server url address e.g. http://example.com/:param
     * @param headerParams  Header parameters
     * @param ssl           Send over http or https connection (note ssl set to true means address
     *                      must be https)
     * @return  jsonObject  JSON response from remote server
     */
    public static JSONObject get(String address, Map<String, String> headerParams, boolean ssl) {
        try {
            URL url = new URL(address);

            if (ssl) {
                HttpsURLConnection httpsURLConnection = setSecureHeaders(url, headerParams, "GET");
                if (httpsURLConnection.getResponseCode() == 200) {
                    jsonObject = bufferResponse(httpsURLConnection);
                }
            } else {
                HttpURLConnection httpURLConnection = setHeaders(url, headerParams, "GET");
                if (httpURLConnection.getResponseCode() == 200) {
                    jsonObject = bufferResponse(httpURLConnection);
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }

        return jsonObject;
    }

    /**
     * Make a HTTP DELETE request to a remote server
     *
     * @param address       Server url address e.g. http://example.com/:param
     * @param headerParams  Header parameters
     * @param ssl           Send over http or https connection (note ssl set to true means address
     *                      must be https)
     * @return  jsonObject  JSON response from remote server
     */
    public static JSONObject delete(String address, Map<String, String> headerParams, boolean ssl) {
        try {
            URL url = new URL(address);

            if (ssl) {
                HttpsURLConnection httpsURLConnection = setSecureHeaders(url, headerParams, "DELETE");
                if (httpsURLConnection.getResponseCode() == 200) {
                    jsonObject = bufferResponse(httpsURLConnection);
                }
            } else {
                HttpURLConnection httpURLConnection = setHeaders(url, headerParams, "DELETE");
                if (httpURLConnection.getResponseCode() == 200) {
                    jsonObject = bufferResponse(httpURLConnection);
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }

        return jsonObject;
    }
}
