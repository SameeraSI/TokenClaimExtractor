# WSO2 Custom Mediator - Token Claim Extractor

This project provides a custom WSO2 Synapse mediator that extracts a specific claim (`client_id`) from JWT token claims and sets it as a message context property for downstream processing.

---

## Features

✔ Extracts the `client_id` claim from JWT token claims available in the message context  
✔ Stores the extracted claim as a new message context property `EXTRACTED_CLAIM_PROPERTY`  
✔ Includes structured and consistent logging with a custom prefix for easy filtering  
✔ Provides warning logs if expected claims are missing or incorrectly formatted  

---

## Project Structure

```

src/main/java/org/example/wso2/CustomMediator.java

````

---

## How It Works

- The mediator expects a message context property named `jwt_token_claims`, which should be a `Map<String, Object>` containing JWT claims.
- It searches for the `client_id` claim.
- If found and is a valid string, it sets the claim value to a new message context property called `EXTRACTED_CLAIM_PROPERTY`.
- Logs the process using a consistent prefix `[TokenClaimExtractor]` for easy debugging.

---

## Example Synapse Usage
Create a [custom policy](https://apim.docs.wso2.com/en/latest/manage-apis/design/api-policies/create-policy/) with the following Synapse configurations and apply it to the required resources in the API.

```xml
<!-- Call the custom mediator -->
<class name="org.example.wso2.CustomMediator" />
<!-- Log the value of the property -->
<log level="custom">
 <property name="Extracted Claim Value: " expression="$ctx:EXTRACTED_CLAIM_PROPERTY"/>
</log>
````

---

## Prerequisites

* Java 8 or higher
* Maven
* WSO2 API Manager that supports custom mediators

---

## Build Instructions

1. Clone the repository.
2. Run the following command to build the project:

```bash
mvn clean package
```

3. The generated JAR file will be located under the `target` directory.

---

## Deployment

1. Copy the compiled JAR to your WSO2 server's library:

```bash
# Example for API Manager
<API-M_HOME>/repository/components/lib
```

2. Update your Synapse configuration to include the mediator as shown in the usage example.

---

## Logging Configuration

To enable detailed logs for this mediator, add the following to your `log4j2.properties` file:

```properties
logger.custom-mediator-log.name = org.example.wso2
logger.custom-mediator-log.level = DEBUG
```
```properties
loggers = custom-mediator-log,.......
```
---

## Customization

You can modify the following constants in `CustomMediator.java` if needed:

| Constant Name         | Purpose                                                  |
| --------------------- | -------------------------------------------------------- |
| `JWT_CLAIMS_PROPERTY` | Message context property containing JWT claims           |
| `REQUIRED_CLAIM`      | The specific claim to extract (`client_id` by default)   |
| `EXTRACTED_PROPERTY`  | The name of the property to set with the extracted claim |
| `LOG_PREFIX`          | Custom prefix for all log messages                       |

---
