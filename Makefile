keycloak: 
	docker run -p 8081:8080 \
  	-e KC_BOOTSTRAP_ADMIN_USERNAME=admin \
  	-e KC_BOOTSTRAP_ADMIN_PASSWORD=admin \
  	-e KC_HTTP_CORS_ORIGINS="http://localhost:4200" \
  	-e KC_HTTP_CORS_METHODS="GET,POST,DELETE,PUT,OPTIONS" \
  	-e KC_HTTP_CORS_ALLOW_CREDENTIALS=true \
  	-e KC_HTTP_CORS_MAX_AGE=1000 \
  	-v keycloak-data:/opt/keycloak/data \
  	quay.io/keycloak/keycloak:26.1.4 start-dev

	
docker-stop:
	docker stop $(docker ps -a -q)

docker-rm:
	docker rm $(docker ps -a -q)
