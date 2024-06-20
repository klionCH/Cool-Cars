FROM node:18.17.0-bullseye-slim
WORKDIR /app
COPY . .
RUN npm ci
RUN npm run build
EXPOSE 3000
ENTRYPOINT ["npm", "start"]
