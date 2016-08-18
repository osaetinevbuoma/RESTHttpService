# RESTHttpService #

The RESTHttpService provides methods in Java for making HTTP or HTTPS requests to remote servers or APIs. Responses from requests are returned as JSON objects.

### How do I get set up? ###

You can download the source code and using a gradle, build the source code and generate a jar file. Place the jar file in your project's library folder, import the jar file using your IDE or a build tool like gradle.

#### From source code ####
* Download source code zip folder
* Unzip source folder
* Build the project with gradle and generate jar file
```
#!

cd [source_folder]
gradle build
gradle jar
```
* In your JVM project, create a folder (libs) and place generated jar file
* Import jar file into your project dependencies, build script or IDE

#### Jar file ####
* Download jar file into your project's library folder
* Import into your project classpath via IDE or build tool

### Usage ###
Follow the instructions to make http requests to remote servers.

```
#!java

import com.modnsolutions.JsonHttpService;

public class MyClass {
    public static void main(String[] args) {
        String address = "http://example.com/";

        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put("Content-Type", "application/json");
        headerParams.put("Accept", "application/json");

        JSONObject bodyParams = new JSONObject();
        bodyParams.put("body", "Some content");

        boolean ssl = false;

        // HTTP POST request
        JSONObject postResponse = com.modnsolutions.JsonHttpService.post(address + "/post", headerParams, bodyParams.toString(), ssl);
        System.out.println(postResponse.toString());

        // HTTP PUT request
        JSONObject putResponse = com.modnsolutions.JsonHttpService.put(address + "/put", headerParams, bodyParams.toString(), ssl);
        System.out.println(putResponse.toString());

        // HTTP GET request
        JSONObject getResponse = com.modnsolutions.JsonHttpService.get(address + "/get/1", headerParams, ssl);
        System.out.println(getResponse.toString());

        // HTTP DELETE request
        JSONObject deleteResponse = com.modnsolutions.JsonHttpService.delete(address + "/delete/1", headerParams, ssl);
        System.out.println(deleteResponse.toString());
    }
}
```

**NOTE: To make https request, *ssl* must be set to true and address url string must be *https* instead of *http*.**

### Who do I talk to? ###

* Contact osaetinevbuoma@gmail.com for any issues, suggestions or complaints.