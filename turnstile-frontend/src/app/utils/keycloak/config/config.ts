import { AppConfig } from 'main';
import {
  createInterceptorCondition,
  IncludeBearerTokenCondition,
  INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
  withAutoRefreshToken,
  AutoRefreshTokenService,
  UserActivityService,
  ProvideKeycloakOptions
} from 'keycloak-angular';
import { KeycloakOnLoad, KeycloakPkceMethod } from 'keycloak-js';

export function initializeKeycloak(config: AppConfig): ProvideKeycloakOptions {
  let apiRegex = new RegExp(String.raw`^${config.apiBaseUrl}(\/.*)?$`, 'i');
  const tokenCondition = createInterceptorCondition<IncludeBearerTokenCondition>({
    urlPattern: apiRegex,
  });
  return {
      config: {
        url: config.url,
        realm: config.realm,
        clientId: config.client,
      },
      initOptions: {
        onLoad: 'login-required' as KeycloakOnLoad,
        checkLoginIframe: false,
        enableLogging: true,
        pkceMethod: 'S256' as KeycloakPkceMethod,
      },
      features: [
        withAutoRefreshToken({
          onInactivityTimeout: 'logout',
          sessionTimeout: 60000
        })
      ],
      providers: [
        AutoRefreshTokenService,
        UserActivityService,
        {
          provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
          useValue: [tokenCondition]
        }
      ]
    }
}