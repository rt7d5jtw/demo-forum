# ------------------------------------------------------------------------------
# INFRASTRUCTURE / DATABASE SETUP
# ------------------------------------------------------------------------------
.PHONY: local database down logs clean desc show-schema

database:
	docker-compose -f mariadb/mariadb.yml up -d

local: database
	./gradlew build run

# Shuts down the database
down:
	docker-compose -f mariadb/mariadb.yml down

# Streams database logs
logs:
	docker-compose -f mariadb/mariadb.yml logs -f mariadb

# Completely wipes the database and its data volume
clean:
	docker-compose -f mariadb/mariadb.yml down -v

# ------------------------------------------------------------------------------
# DATABASE INSPECTION HELPERS
# ------------------------------------------------------------------------------
# For inspecting all tables
desc:
	docker-compose -f mariadb/mariadb.yml exec mariadb mysql -u testuser -pmeow -e "use forum; show tables;"

# For inspecting the table schemas
show-schema:
	docker-compose -f mariadb/mariadb.yml exec mariadb mysql -u testuser -pmeow -e "use forum; show columns from users; show columns from authorities; show columns from categories; show columns from threads; show columns from posts;"

# ------------------------------------------------------------------------------
# APPLICATION BUILD (GRADLE)
# ------------------------------------------------------------------------------
.PHONY: build run

# Builds the Java application using Gradle
build:
	./gradlew build

# Runs the application locally
run:
	./gradlew run
