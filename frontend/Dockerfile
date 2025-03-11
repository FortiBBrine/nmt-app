FROM node:alpine
WORKDIR /app
COPY . .
RUN yarn global add serve
RUN yarn install
RUN yarn run build
EXPOSE 3000
ENTRYPOINT serve -s dist -l 3000
