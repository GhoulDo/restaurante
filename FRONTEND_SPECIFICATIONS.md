# 🍽️ SISTEMA DE GESTIÓN DE RESTAURANTE - ESPECIFICACIONES FRONTEND

## 📋 DESCRIPCIÓN DEL SISTEMA

El **Sistema de Gestión de Restaurante** es una aplicación web completa que permite administrar todas las operaciones de un restaurante, desde la gestión de mesas y pedidos hasta el control de inventario y facturación. El sistema está diseñado para ser usado por diferentes tipos de usuarios (meseros, cocineros, cajeros y administradores) con interfaces específicas para cada rol.

## 🎯 OBJETIVO PRINCIPAL

Crear una plataforma digital que automatice y optimice las operaciones diarias de un restaurante, mejorando la eficiencia del servicio, reduciendo errores y proporcionando información en tiempo real para la toma de decisiones.

## 👥 USUARIOS DEL SISTEMA

### 🔴 **ADMINISTRADOR**
- Acceso completo al sistema
- Gestión de empleados, categorías, productos e inventario
- Reportes y análisis de ventas
- Configuración del sistema

### 🟡 **CAJERO**
- Gestión de clientes y facturas
- Procesamiento de pagos
- Reportes de ventas del día
- Gestión de mesas (liberación)

### 🟢 **MESERO**
- Gestión de mesas y pedidos
- Toma de órdenes de clientes
- Seguimiento de estado de pedidos
- Comunicación con cocina

### 🔵 **COCINERO**
- Visualización de pedidos pendientes
- Actualización de estado de preparación
- Gestión de productos disponibles
- Control de tiempos de preparación

## 📱 PÁGINAS Y FUNCIONALIDADES DEL FRONTEND

### 🏠 **1. PÁGINA DE INICIO / LOGIN**
```
Ruta: /login
Usuarios: Todos
```
**Funcionalidades:**
- Formulario de autenticación por rol
- Selección de empleado (simulando login)
- Redirección según el rol del usuario
- Información básica del restaurante

**Elementos UI:**
- Logo del restaurante
- Dropdown de selección de empleado
- Botón de acceso
- Links a información del sistema

---

### 🏢 **2. DASHBOARD PRINCIPAL**
```
Ruta: /dashboard
Usuarios: Todos (contenido dinámico según rol)
```
**Funcionalidades:**
- Resumen de actividad del día
- Métricas principales según rol
- Navegación rápida a funciones principales
- Notificaciones importantes

**Widgets dinámicos por rol:**
- **Admin:** Ventas totales, pedidos del día, productos con stock bajo
- **Cajero:** Facturas pendientes, ventas del día, mesas por liberar
- **Mesero:** Mesas asignadas, pedidos pendientes, nuevas órdenes
- **Cocinero:** Pedidos en preparación, tiempos promedio, productos agotados

---

### 🪑 **3. GESTIÓN DE MESAS**
```
Ruta: /mesas
Usuarios: Meseros, Cajeros, Administradores
```
**Funcionalidades:**
- Vista de plano del restaurante con estado de mesas
- Cambio de estado de mesas (Libre/Ocupada/Reservada)
- Asignación de mesas a meseros
- Historial de ocupación por mesa

**Elementos UI:**
- Grid de mesas con código de colores:
  - 🟢 Verde: LIBRE
  - 🔴 Rojo: OCUPADA
  - 🟡 Amarillo: RESERVADA
- Modal para cambiar estado de mesa
- Filtros por estado
- Botón "Agregar nueva mesa"

**Acciones:**
- Crear nueva mesa
- Editar número de mesa
- Cambiar estado
- Ver historial de la mesa
- Eliminar mesa (solo admin)

---

### 🛒 **4. GESTIÓN DE PEDIDOS**
```
Ruta: /pedidos
Usuarios: Meseros, Cocineros, Administradores
```
**Funcionalidades:**
- Lista de pedidos con filtros por estado
- Creación de nuevos pedidos
- Edición de pedidos existentes
- Seguimiento de tiempo de preparación

**Sub-páginas:**
- `/pedidos/nuevo` - Crear pedido
- `/pedidos/:id` - Detalle del pedido
- `/pedidos/:id/editar` - Editar pedido

**Elementos UI:**
- Tabla de pedidos con código de colores por estado
- Botón "Nuevo Pedido"
- Filtros: Por estado, por mesa, por mesero, por fecha
- Cards de pedidos para vista de cocinero
- Timer de tiempo transcurrido

**Estados de pedidos:**
- 🟡 PENDIENTE
- 🔵 EN_PREPARACION
- 🟢 SERVIDO
- 🔴 CANCELADO

---

### 🍕 **5. CREAR/EDITAR PEDIDO**
```
Ruta: /pedidos/nuevo | /pedidos/:id/editar
Usuarios: Meseros, Administradores
```
**Funcionalidades:**
- Selección de mesa disponible
- Búsqueda y selección de productos
- Cálculo automático de totales
- Asignación de cliente (opcional)

**Elementos UI:**
- Selector de mesa
- Buscador de productos con filtro por categoría
- Lista de productos seleccionados
- Calculadora de totales
- Campos para datos del cliente
- Botones: Guardar, Cancelar, Agregar más productos

**Flujo:**
1. Seleccionar mesa
2. Buscar y agregar productos
3. Especificar cantidades
4. Revisar total
5. Guardar pedido

---

### 🏷️ **6. GESTIÓN DE CATEGORÍAS**
```
Ruta: /categorias
Usuarios: Administradores
```
**Funcionalidades:**
- CRUD completo de categorías
- Visualización de cantidad de productos por categoría
- Reordenamiento de categorías

**Elementos UI:**
- Tabla de categorías
- Botón "Nueva Categoría"
- Modal para crear/editar
- Contador de productos por categoría
- Acciones: Editar, Eliminar

---

### 🍽️ **7. GESTIÓN DE PRODUCTOS**
```
Ruta: /productos
Usuarios: Administradores
```
**Funcionalidades:**
- CRUD completo de productos
- Filtrado por categoría
- Gestión de precios
- Indicador de stock disponible

**Sub-páginas:**
- `/productos/nuevo` - Crear producto
- `/productos/:id` - Detalle del producto
- `/productos/:id/editar` - Editar producto

**Elementos UI:**
- Tabla/Grid de productos con imágenes
- Filtros por categoría y disponibilidad
- Botón "Nuevo Producto"
- Indicadores de stock bajo
- Precios destacados

---

### 👥 **8. GESTIÓN DE CLIENTES**
```
Ruta: /clientes
Usuarios: Cajeros, Administradores
```
**Funcionalidades:**
- CRUD de clientes
- Búsqueda por nombre y teléfono
- Historial de pedidos por cliente
- Datos de contacto

**Elementos UI:**
- Tabla de clientes con búsqueda
- Botón "Nuevo Cliente"
- Modal para crear/editar
- Vista de historial de pedidos
- Campos: Nombre, teléfono, email

---

### 👨‍💼 **9. GESTIÓN DE EMPLEADOS**
```
Ruta: /empleados
Usuarios: Administradores
```
**Funcionalidades:**
- CRUD de empleados
- Asignación de roles
- Filtrado por rol
- Estado activo/inactivo

**Elementos UI:**
- Tabla de empleados
- Filtros por rol
- Badges de roles con colores
- Modal para crear/editar
- Campos: Nombre, rol, teléfono, email

**Roles disponibles:**
- 🔴 ADMIN
- 🟡 CAJERO
- 🟢 MESERO
- 🔵 COCINERO

---

### 📦 **10. GESTIÓN DE INVENTARIO**
```
Ruta: /inventario
Usuarios: Administradores
```
**Funcionalidades:**
- Visualización de stock actual
- Actualización de cantidades
- Alertas de stock bajo
- Historial de movimientos

**Elementos UI:**
- Tabla de inventario con indicadores visuales
- Alertas de stock crítico (rojo)
- Botones de ajuste rápido (+/-)
- Modal para actualización masiva
- Gráficos de stock por producto

**Indicadores:**
- 🔴 Stock crítico (< 5)
- 🟡 Stock bajo (5-15)
- 🟢 Stock normal (> 15)

---

### 🧾 **11. GESTIÓN DE FACTURAS**
```
Ruta: /facturas
Usuarios: Cajeros, Administradores
```
**Funcionalidades:**
- Lista de facturas generadas
- Creación de facturas desde pedidos
- Filtrado por fecha y empleado
- Impresión/descarga de facturas

**Sub-páginas:**
- `/facturas/nueva` - Crear factura
- `/facturas/:id` - Ver factura
- `/facturas/:id/imprimir` - Imprimir factura

**Elementos UI:**
- Tabla de facturas con filtros
- Botón "Nueva Factura"
- Selector de pedidos servidos
- Preview de factura
- Botones de impresión/descarga

---

### 📊 **12. REPORTES Y ESTADÍSTICAS**
```
Ruta: /reportes
Usuarios: Administradores, Cajeros
```
**Funcionalidades:**
- Reportes de ventas por período
- Productos más vendidos
- Rendimiento por empleado
- Análisis de ocupación de mesas

**Elementos UI:**
- Filtros de fecha
- Gráficos interactivos (Chart.js/D3.js)
- Tablas de datos
- Botones de exportación
- Métricas destacadas

**Tipos de reportes:**
- Ventas diarias/mensuales
- Productos top
- Empleados más productivos
- Ocupación promedio de mesas
- Ingresos por categoría

---

### ⚙️ **13. CONFIGURACIÓN DEL SISTEMA**
```
Ruta: /configuracion
Usuarios: Administradores
```
**Funcionalidades:**
- Configuración general del restaurante
- Gestión de horarios de operación
- Configuración de impresoras
- Respaldos de datos

**Elementos UI:**
- Pestañas de configuración
- Formularios de ajustes
- Botones de respaldo
- Configuración de notificaciones

---

## 🎨 DISEÑO Y UX/UI

### **Paleta de colores sugerida:**
- **Primario:** #2563eb (Azul)
- **Secundario:** #10b981 (Verde)
- **Peligro:** #ef4444 (Rojo)
- **Advertencia:** #f59e0b (Amarillo)
- **Neutro:** #6b7280 (Gris)

### **Tipografía:**
- **Principal:** Inter, Roboto o system fonts
- **Tamaños:** 12px, 14px, 16px, 18px, 24px, 32px

### **Componentes UI principales:**
- **Navbar:** Navegación superior con logo y perfil
- **Sidebar:** Menú lateral colapsable
- **Cards:** Para widgets de dashboard
- **Tables:** Para listados de datos
- **Modals:** Para formularios de creación/edición
- **Alerts:** Para notificaciones y feedback
- **Buttons:** Consistentes en tamaños y estados
- **Forms:** Con validación en tiempo real

### **Responsividad:**
- **Desktop:** 1200px+ (Diseño principal)
- **Tablet:** 768px-1199px (Adaptado)
- **Mobile:** 320px-767px (Menú hamburguesa, cards apiladas)

---

## 🛠️ TECNOLOGÍAS RECOMENDADAS

### **Frontend Framework:**
- **React.js** con TypeScript
- **Next.js** para SSR/SSG
- **Vue.js** con Nuxt.js
- **Angular** para aplicaciones empresariales

### **UI Libraries:**
- **Tailwind CSS** + **Headless UI**
- **Material-UI (MUI)**
- **Ant Design**
- **Chakra UI**

### **State Management:**
- **Redux Toolkit** (React)
- **Zustand** (React - más simple)
- **Pinia** (Vue.js)
- **NgRx** (Angular)

### **Gráficos y visualización:**
- **Chart.js**
- **Recharts**
- **D3.js**
- **ApexCharts**

### **Utilidades:**
- **Axios** para peticiones HTTP
- **React Query/TanStack Query** para cache
- **Date-fns** para manejo de fechas
- **React Hook Form** para formularios
- **React Router** para navegación

---

## 🔄 FLUJOS DE TRABAJO PRINCIPALES

### **1. Flujo de Toma de Pedido:**
```
Mesero → Selecciona Mesa → Busca Productos → Agrega al Pedido → Confirma → Envía a Cocina
```

### **2. Flujo de Preparación:**
```
Cocinero → Ve Pedido Pendiente → Cambia a "En Preparación" → Completa → Cambia a "Servido"
```

### **3. Flujo de Facturación:**
```
Cajero → Selecciona Pedido Servido → Genera Factura → Procesa Pago → Libera Mesa
```

### **4. Flujo de Gestión de Inventario:**
```
Admin → Revisa Stock → Actualiza Cantidades → Configura Alertas → Genera Reportes
```

---

## 📱 CONSIDERACIONES MOBILE

### **Vistas optimizadas para móvil:**
- Dashboard con cards apiladas
- Lista de mesas en formato grid 2x2
- Formularios con inputs grandes
- Navegación por tabs en lugar de sidebar
- Botones de acción flotantes

### **Funcionalidades específicas mobile:**
- Scanner QR para mesas (futuro)
- Notificaciones push
- Modo offline básico
- Gestos de swipe para acciones rápidas

---

## 🔐 CONSIDERACIONES DE SEGURIDAD

### **Autenticación:**
- Login por rol de empleado
- Sesiones con timeout automático
- Protección de rutas por rol

### **Validación:**
- Validación de formularios en frontend
- Sanitización de inputs
- Manejo seguro de errores

### **Datos sensibles:**
- No almacenar passwords en frontend
- Encriptación de datos locales
- HTTPS obligatorio en producción

---

## 🚀 FASES DE DESARROLLO SUGERIDAS

### **Fase 1: MVP (4-6 semanas)**
- Login y dashboard básico
- Gestión de mesas
- Creación de pedidos básicos
- Lista de productos

### **Fase 2: Core Features (3-4 semanas)**
- CRUD completo de productos y categorías
- Gestión de inventario
- Sistema de facturación
- Gestión de empleados

### **Fase 3: Advanced Features (2-3 semanas)**
- Reportes y estadísticas
- Configuración del sistema
- Optimizaciones mobile
- Mejoras UX/UI

### **Fase 4: Polish & Deploy (1-2 semanas)**
- Testing exhaustivo
- Optimización de performance
- Documentación
- Deployment y configuración

---

## 📋 EJEMPLO DE ESTRUCTURA DE ARCHIVOS

```
src/
├── components/           # Componentes reutilizables
│   ├── ui/              # Componentes básicos (Button, Input, etc.)
│   ├── layout/          # Layout components (Navbar, Sidebar)
│   └── forms/           # Formularios específicos
├── pages/               # Páginas principales
│   ├── dashboard/
│   ├── mesas/
│   ├── pedidos/
│   ├── productos/
│   └── reportes/
├── services/            # Servicios API
│   ├── api.js           # Configuración base
│   ├── categorias.js
│   ├── productos.js
│   └── pedidos.js
├── hooks/               # Custom hooks
├── utils/               # Utilidades
├── types/               # Tipos TypeScript
└── styles/              # Estilos globales
```

---

## 🎯 MÉTRICAS DE ÉXITO

### **Performance:**
- Tiempo de carga inicial < 3 segundos
- Tiempo de respuesta de acciones < 1 segundo
- Score de Lighthouse > 90

### **Usabilidad:**
- Facilidad de uso para personal sin experiencia técnica
- Reducción de errores en pedidos > 80%
- Tiempo de entrenamiento < 1 hora por empleado

### **Business Impact:**
- Incremento en eficiencia del servicio > 30%
- Reducción de tiempo de toma de pedidos > 50%
- Mejor control de inventario y reducción de desperdicio

---

## 📞 SOPORTE Y MANTENIMIENTO

### **Documentación requerida:**
- Manual de usuario por rol
- Guía de instalación y configuración
- API documentation
- Guía de troubleshooting

### **Monitoreo:**
- Logs de errores del frontend
- Analytics de uso
- Performance monitoring
- User feedback collection

---

¡El sistema está diseñado para ser escalable, intuitivo y eficiente, mejorando significativamente las operaciones del restaurante y la experiencia tanto del personal como de los clientes!
