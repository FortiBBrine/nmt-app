FROM node:alpine
WORKDIR /app
COPY . .
RUN npm i
RUN npm i -g serve
RUN npm run build
ENTRYPOINT serve -s dist -l 3000
