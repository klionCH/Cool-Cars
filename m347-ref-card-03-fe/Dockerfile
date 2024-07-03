FROM node:18.17.0-bullseye-slim
WORKDIR /app
COPY . .
RUN npm ci
RUN npm run build
EXPOSE 3000
ENV BACKEND_URL=${BACKEND_URL}
CMD ["npm", "start"]
