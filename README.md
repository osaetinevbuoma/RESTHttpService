# RESTHttpService [![Build Status](https://travis-ci.org/osaetinevbuoma/RESTHttpService.svg?branch=master)](https://travis-ci.org/osaetinevbuoma/RESTHttpService) #

The RESTHttpService provides methods in Java for making HTTP or HTTPS requests to remote servers or APIs. Responses from requests are returned as JSON objects.

### How do I get set up? ###

Simply include the following lines of code in your `build.gradle` file.
```
repositories {
    maven {
        url  "http://dl.bintray.com/modnsolutions/plugins-libraries" 
    }
}

dependencies {
    compile "com.modnsolutions:RESTHttpService:1.1"
}
```

### Usage ###
Code signature for **post** and **put** requests:

```
// headerParameters can be set as null if extra headers are not needed
JsonHTTPService.post(String remoteUrl, Map<String, String> headerParameters, String bodyParameters, boolean sslConnection)
JsonHTTPService.put(String remoteUrl, Map<String, String> headerParameters, String bodyParameters, boolean sslConnection)
```

Code signature for **get** and **delete** requests:

```
// headerParameters can be set as null if extra headers are not needed
JsonHTTPService.get(String remoteUrl, Map<String, String> headerParameters, boolean sslConnection)
JsonHTTPService.delete(String remoteUrl, Map<String, String> headerParameters, boolean sslConnection)
```

### Example: ###

```
import com.modnsolutions.JsonHTTPService;

public class MyClass {
    public static void main(String[] args) {
        String address = "http://example.com/";

        Map<String, String> headerParams = new HashMap<String, String>();
        // Put as many header parameters as you desire
        headerParams.put("Content-Type", "application/json");
        headerParams.put("Accept", "application/json");

        JSONObject bodyParams = new JSONObject();
        // Put as many body parameters as desired for post and put requests
        bodyParams.put("body", "Some content");

        boolean ssl = false;

        // HTTP POST request
        JSONObject postResponse = JsonHTTPService.post(address + "/post", headerParams, bodyParams.toString(), ssl);
        System.out.println(postResponse.toString());

        // HTTP PUT request
        JSONObject putResponse = JsonHTTPService.put(address + "/put", headerParams, bodyParams.toString(), ssl);
        System.out.println(putResponse.toString());

        // HTTP GET request
        JSONObject getResponse = JsonHTTPService.get(address + "/get/1", headerParams, ssl);
        System.out.println(getResponse.toString());

        // HTTP DELETE request
        JSONObject deleteResponse = JsonHTTPService.delete(address + "/delete/1", headerParams, ssl);
        System.out.println(deleteResponse.toString());
    }
}
```

**NOTE: To make https request, *ssl* must be set to true and address url string must be *https* instead of *http*.**

### Who do I talk to? ###

* Contact osaetinevbuoma@gmail.com for any issues, suggestions or complaints.