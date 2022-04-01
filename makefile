sim:
	cd simulator && ./gradlew installDist && ./build/install/simulator/bin/simulator-server

deploy:
	docker-compose build && docker-compose up

serve:
	cd server && npm run start-dev