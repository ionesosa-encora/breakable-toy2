# Etapa 1: Construcción
FROM node:18 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de configuración de npm
COPY package.json package-lock.json ./

# Instala las dependencias
RUN npm install

# Copia el resto de los archivos de la aplicación
COPY . .

# Construye la aplicación
RUN npm run build

# Etapa 2: Ejecución
FROM nginx:alpine

# Copia el archivo nginx.conf desde la carpeta actual al contenedor
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copia los archivos construidos desde la etapa de build al contenedor de Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Expone el puerto 80 para que la aplicación esté accesible
EXPOSE 80

# Comando por defecto para ejecutar Nginx
CMD ["nginx", "-g", "daemon off;"]
