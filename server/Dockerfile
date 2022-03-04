FROM node:12

WORKDIR /life-simulation

COPY package*.json ./

RUN npm install

COPY . .

CMD [ "npm", "start" ]