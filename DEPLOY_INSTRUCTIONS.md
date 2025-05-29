# Instrucciones para Desplegar en Render

## Pasos para Desplegar

### 1. Preparar el Repositorio
```bash
# Asegúrate de que todos los cambios estén guardados
git add .
git commit -m "Preparar para despliegue en Render"
git push origin main
```

### 2. Crear Servicio en Render

1. Ve a [Render.com](https://render.com)
2. Conecta tu cuenta de GitHub
3. Haz clic en "New +" → "Web Service"
4. Conecta tu repositorio de GitHub
5. Configura el servicio:

**Configuración Básica:**
- Name: `restaurante-api`
- Environment: `Java`
- Region: `Oregon (US West)` o el más cercano
- Branch: `main`

**Build & Deploy:**
- Build Command: `./mvnw clean package -DskipTests`
- Start Command: `java -jar target/restaurante-0.0.1-SNAPSHOT.jar`

**Variables de Entorno:**
- `PORT`: (Render lo asigna automáticamente)
- `SPRING_PROFILES_ACTIVE`: `production`

### 3. Configurar Base de Datos

La base de datos PostgreSQL actualizada en Render:
- Host: `dpg-d0seuk2li9vc73cu8t9g-a.virginia-postgres.render.com`
- Puerto: `5432`
- Base de datos: `restaurante_czko`
- Usuario: `admin_restaurante`
- Contraseña: `qwrL26hB1eqUcefDZo8vilqGhWAhn0hX`

**URL Externa de Conexión:**
```
postgresql://admin_restaurante:qwrL26hB1eqUcefDZo8vilqGhWAhn0hX@dpg-d0seuk2li9vc73cu8t9g-a.virginia-postgres.render.com/restaurante_czko
```

### 4. Verificar Despliegue

Una vez desplegado, verifica que funciona:
```bash
# Verificar estado del servicio
curl https://tu-app-restaurante.onrender.com/health

# Verificar endpoint principal
curl https://tu-app-restaurante.onrender.com/

# Verificar API
curl https://tu-app-restaurante.onrender.com/api/categorias
```

### 5. URLs Importantes

- **Servicio Principal:** `https://tu-app-restaurante.onrender.com`
- **API Base:** `https://tu-app-restaurante.onrender.com/api`
- **Health Check:** `https://tu-app-restaurante.onrender.com/health`

### 6. Configuración para Frontend

Actualiza tu frontend para usar la URL de producción:

```javascript
const API_BASE_URL = 'https://tu-app-restaurante.onrender.com/api';
```

## Troubleshooting

### Si el despliegue falla:
1. Revisa los logs en Render Dashboard
2. Verifica que Java 17 esté configurado
3. Asegúrate de que todas las dependencias estén en el pom.xml

### Si hay errores de CORS:
- Verifica que CorsConfig.java esté configurado correctamente
- Asegúrate de que tu frontend use HTTPS si el backend está en HTTPS

### Si la base de datos no conecta:
- Verifica las credenciales en application.properties
- Asegúrate de que la base de datos en Render esté activa

## Comandos Útiles

```bash
# Compilar localmente antes de desplegar
./mvnw clean package -DskipTests

# Ejecutar localmente para pruebas
java -jar target/restaurante-0.0.1-SNAPSHOT.jar

# Ver logs en tiempo real (en Render Dashboard)
# Ve a tu servicio → Logs tab
```

## Notas Importantes

1. **Primera vez:** El primer despliegue puede tomar 5-10 minutos
2. **Cold Start:** Si no hay tráfico por un tiempo, el servicio puede dormir
3. **Logs:** Siempre revisa los logs en caso de problemas
4. **Updates:** Cada push a main redesplegará automáticamente
