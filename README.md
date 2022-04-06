# life-simulation

A competitive take on mathematician's John Conway’s Game of Life.
It will include a number of different cellular automatons like
- prey and predator
- plant and obstacles.

Furthermore, the simulation will have a focus on competition, with user configurations being scored based on a number of criteria such as the time length of the simulation

## Requirements

- Docker ([Docker Installation Guide](https://docs.docker.com/get-docker/))
- Docker Compose ([Docker Compose Installation Guide](https://docs.docker.com/compose/install/))


## Deploy

First the copy the .env files

Copy .env.example as .env and .env.dev.example as .env.dev

```
cp .env.example .env && cp .env.dev.example .env.dev
```

Start docker daemon

```
sudo systemctl start docker
```

Build the images

```
docker-compose build
```

Start the containers

```
docker-compose up
```
