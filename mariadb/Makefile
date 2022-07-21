IMAGE := mariadb
TAG := 10.3
NET := perunanetti
SWARMHOST := $(SWARMHOST)
MARIADB_ID := $(shell docker ps -f "ancestor=mariadb:10.3" -q)

all: network volume local

network:
	docker network create -d overlay --attachable perunanetti \
		--opt encrypted=true

volume:
	docker volume create mariadb_data

local:
	docker-compose -f mariadb.yml up -d

logs:
	docker logs -f $(MARIADB_ID)

init:
	docker cp schema.sql $(MARIADB_ID):/
	docker exec -t $(MARIADB_ID) mysql -u testuser -pmeow -e "use forum; source schema.sql; show tables"

# For inspecting the tables
show-schema:
	docker exec -t $(MARIADB_ID) mysql -u testuser -pmeow -e "use forum; show columns from users;"
	docker exec -t $(MARIADB_ID) mysql -u testuser -pmeow -e "use forum; show columns from authorities;"
	docker exec -t $(MARIADB_ID) mysql -u testuser -pmeow -e "use forum; show columns from categories;"
	docker exec -t $(MARIADB_ID) mysql -u testuser -pmeow -e "use forum; show columns from threads;"
	docker exec -t $(MARIADB_ID) mysql -u testuser -pmeow -e "use forum; show columns from posts;"

# For inspecting the tables
desc:
	docker exec -t $(MARIADB_ID) mysql -u testuser -pmeow -e "use forum; show tables;"

clean:
	docker stop $(MARIADB_ID)
	sleep 5
	docker rm $(MARIADB_ID)

.PHONY: clean desc show-schema init logs local volume network all
