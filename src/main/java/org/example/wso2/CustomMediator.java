package org.example.wso2;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import java.util.Map;

public class CustomMediator extends AbstractMediator {
    private static final String JWT_CLAIMS_PROPERTY = "jwt_token_claims";
    private static final String REQUIRED_CLAIM = "client_id";
    private static final String EXTRACTED_PROPERTY = "EXTRACTED_CLAIM_PROPERTY";
    private static final String LOG_PREFIX = "[TokenClaimExtractor] ";

    @Override
    public boolean mediate(MessageContext context) {
        Object jwtClaimsObj = context.getProperty(JWT_CLAIMS_PROPERTY);
        if (jwtClaimsObj instanceof Map<?, ?>) {
            @SuppressWarnings("unchecked")
            Map<String, Object> jwtClaims = (Map<String, Object>) jwtClaimsObj;
            if (jwtClaims.containsKey(REQUIRED_CLAIM)) {
                Object requiredClaimValue = jwtClaims.get(REQUIRED_CLAIM);
                if (requiredClaimValue instanceof String) {
                    String extractedClaimValue = (String) requiredClaimValue;
                    context.setProperty(EXTRACTED_PROPERTY, extractedClaimValue);
                    if (log.isDebugEnabled()) {
                        log.debug(LOG_PREFIX + "Extracted '" + REQUIRED_CLAIM + "' claim: " + extractedClaimValue);
                    }
                } else {
                    log.warn(LOG_PREFIX + "'" + REQUIRED_CLAIM + "' claim is not a String.");
                }
            } else {
                log.warn(LOG_PREFIX + "'" + REQUIRED_CLAIM + "' claim not found in token claims.");
            }
        } else {
            log.warn(LOG_PREFIX + "Token claims missing or invalid in message context.");
        }
        return true;
    }
}
