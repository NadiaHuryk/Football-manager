FROM node:16-alpine AS build
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install -g @angular/cli
RUN npm install
COPY . .
RUN npm run build
CMD ng serve --host 0.0.0.0
