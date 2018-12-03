# Using EnOS API Common SDK for Java

This article instructs how to prepare your development environment to use the *EnOS API Common SDK for Java*.

- [Installing Java JDK SE](https://github.com/EnvisionIot/enos-api-common-sdk-java#installjava)
- [Installing Maven](https://github.com/EnvisionIot/enos-api-common-sdk-java#installmaven)
- [Obtaining EnOS API Common SDK for Java](https://github.com/EnvisionIot/enos-api-common-sdk-java#installiot)
  - [Include dependency in Maven project](https://github.com/EnvisionIot/enos-api-common-sdk-java#installiotmaven)
  - [Building from source](https://github.com/EnvisionIot/enos-api-common-sdk-java#installiotsource)
- [Feature list](https://github.com/EnvisionIot/enos-api-common-sdk-java#featurelist)
- [Sample code](https://github.com/EnvisionIot/enos-api-common-sdk-java#samplecode)

<a name="installjava"></a>
## Installing Java JDK SE

To use the EnOS API Common SDK for Java, you will need **Java SE 8**.

<a name="installmaven"></a>
## Installing Maven

For EnOS API Common SDK for Java, we recommend you to use **Maven 3**.

<a name="installiot"></a>
## Obtaining EnOS API Common SDK for Java

You can obtain the EnOS API Common SDK through the following methods:

- Include the project as a dependency in your Maven project
- Download the source code by cloning the Github repository and build on your machine

<a name="installiotmaven"></a>
### Get EnOS API Common SDK for Java from Maven (as a dependency)

*This is the recommended method of including the EnOS API Common SDK in your project.*

- Navigate to [http://search.maven.org](http://search.maven.org/), search for **com.envisioniot** and take note of the latest version number (or the version number of whichever version of the common sdk you want to use).
- In your main pom.xml file, add the EnOS API Common SDK as a dependency as follows:

```
<dependency>
    <groupId>com.envisioniot</groupId>
    <artifactId>enos-api-common</artifactId>
    <version>2.1.0</version>
    <!--You might need to change the version number as you need.-->
</dependency>
```

<a name="installiotsource"></a>
### Build EnOS API Common SDK for Java from source code

- Get a copy of the **EnOS API Common SDK for Java** from GitHub repository:

```
	git clone https://github.com/EnvisionIot/enos-api-common-sdk-java.git
```

- When you have obtained a copy of the source, you can build the common SDK for Java.

<a name="featurelist"></a>
## Key features

The EnOS API Common SDK supports the following functions:

- Providing the basic components of EnOS API SDK
- Providing the specification of EnOS API status codes

<a name="samplecode"></a>
## Sample code

The following sample code is for building API SDK with the basic components of EnOS API SDK.

```
package com.envisioniot.enos.enosapi.api.request.sampleservice;

import com.envisioniot.enos.enosapi.common.response.EnOSResponse;
import java.lang.String;
import com.envisioniot.enos.enosapi.api.resource.sampleservice.SampleResource;
import com.envisioniot.enos.enosapi.common.annotation.*;
import com.envisioniot.enos.enosapi.common.enumeration.*;
import com.envisioniot.enos.enosapi.common.request.*;
import com.envisioniot.enos.enosapi.common.exception.*;

import java.util.*;

public class SampleRequest extends EnOSRequest<EnOSResponse<SampleResource>> {
    private static final String API_METHOD = "/sampleService/sampleResources";
    private static final String REQUEST_METHOD = "POST";
    
    private String orgId;
    @EnOSRequestBody
    private SampleResource sampleResource;

    public CreateProductRequest(String orgId, SampleResource sampleResource) {
        this.orgId = orgId;
        this.sampleResource = sampleResource;
    }

    @Override
    public String getApiMethodName() {
        return API_METHOD;
    }

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void check() throws EnOSRuleException {
        // super.check();
    }
}
```
**Display EnOS API status codes**

```
//throw the ServiceException that includes EnOS API Status
if(!checkSignature(accessKey, request, response)) {
    throw new ServiceException(EnOSErrorCode.APP_SIGN_FAIL);
}
```

**Get EnOS API status codes and messages**

```
//invoking enos api by java sdk
SampleRequest request = new SampleRequest(orgId, sampleResource);
EnOSResponse<SampleResource> response = client.execute(request);
System.out.println(response.getStatus()); //status code
System.out.println(response.getMsg()); //status message
```