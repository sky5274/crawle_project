package com.sky.auth.config.provider;

public abstract class AbstractPreparableIntegrationAuthenticator implements IntegrationAuthenticator {

    @Override
    public void prepare(IntegrationAuthentication entity) {
        
    }
    
    @Override
    public void complete(IntegrationAuthentication entity) {

    }
}
