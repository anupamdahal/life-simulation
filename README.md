# life-simulation

A competitive take on mathematician's John Conway’s Game of Life.
It will include a number of different cellular automatons like
- prey and predator
- plant and obstacles.

Furthermore, the simulation will have a focus on competition, with user configurations being scored based on a number of criteria such as the time length of the simulation

## Deploy

First the copy .env files. 

```
cp .env.example .env && cp .env.dev.example .env.dev
```

Start docker.

```
sudo systemctl start docker
```

Deploy

```
make deploy
```
