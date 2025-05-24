# AliFood

## Descripción

**AliFood** es una aplicación de escritorio JavaFX diseñada especialmente para **jóvenes que están comenzando su camino
hacia la independencia y la vida en solitario**. Su objetivo principal es simplificar la gestión de la despensa,
ayudándote a llevar un control de tus alimentos, evitar el desperdicio y descubrir cómo cocinar deliciosas comidas sin
complicaciones.


## Tecnologías Utilizadas

* **Java:** El lenguaje de programación principal.
* **JavaFX:** El framework de interfaz de usuario para construir la aplicación de escritorio. Este framework proporciona un conjunto de herramientas y API para crear interfaces gráficas ricas y modernas, incluyendo:
    * Controles de interfaz de usuario (botones, etiquetas, listas, etc.).
    * Soporte para diseño declarativo mediante archivos FXML.
    * Mecanismos para la gestión de la escena gráfica y ventanas.
    * Capacidades de diseño y disposición de nodos en la interfaz.
    * Manejo de eventos de usuario.
    * Funcionalidades para la gestión de propiedades y la vinculación de datos.
    * API para trabajar con colecciones dinámicas.
    * Soporte para imágenes, pintura y texto.
    * Animaciones y efectos visuales.
* **Librerías JavaFX adicionales:**
    * **BootstrapFX:** Para aplicar estilos similares a Bootstrap a la interfaz de usuario de JavaFX.
    * **ControlsFX:** Proporciona controles de interfaz de usuario de alta calidad que complementan los controles básicos de JavaFX.
    * **FormsFX:** Facilita la creación de formularios en JavaFX.
    * **FXGL:** Una librería (motor) para crear juegos 2D en JavaFX (si decides añadir elementos de juego o animaciones más complejas).
    * **Ikonli:** Permite utilizar packs de iconos en aplicaciones Java.
    * **TilesFX:** Una librería para crear dashboards con JavaFX.
    * **ValidatorFX:** Para la validación de formularios en JavaFX.
* **MySQL (o la base de datos de tu elección):** Para el almacenamiento de la información de los usuarios y la despensa. Se utilizará el conector JDBC correspondiente (ej., `mysql-connector-j`).
* **jBCrypt (u otra librería de encriptación):** Para la encriptación segura de las contraseñas de los usuarios.
* **Librería JSON (Jackson, Gson u otra):** Para la manipulación de datos JSON al interactuar con la API de inteligencia artificial.
* **API de Inteligencia Artificial (especificar cuál se utilizará):** Para la obtención de sugerencias de recetas.

  
## Funcionalidades Principales

* **Gestión de la Despensa:** Ingresa, visualiza y elimina alimentos, con seguimiento de su frescura.
* **Alertas de Caducidad:** Recibe notificaciones sobre alimentos próximos a vencer y visualiza la eliminación
  automática de productos caducados.
* **Lista de Compras Inteligente:** Gestiona tu lista de compras con sincronización automática a la despensa y
  designación de alimentos básicos.
* **Sugerencias de Recetas con IA:** Descubre recetas creativas basadas en los ingredientes disponibles, priorizando
  aquellos a punto de caducar.
* **Recetas Diarias:** Obtén ideas de recetas para desayuno, almuerzo y cena utilizando los alimentos de tu despensa.
* **Autenticación Segura:** Inicio de sesión y registro de usuarios con validación y encriptación de contraseñas.

## Estructura del Proyecto

AliFood/

├── src/

│ ├── main/

│ │ ├── java/

│ │ │ └── com/sebasth/

│ │ │ ├── models/

│ │ │ │ ├── Alimento.java

│ │ │ │ └── Usuario.java

│ │ │ ├── views/

│ │ │ │ ├── LoginView.java

│ │ │ │ ├── RegistroView.java

│ │ │ │ ├── DespensaView.java

│ │ │ │ ├── ListaComprasView.java

│ │ │ │ ├── RecetasView.java

│ │ │ │ └── AlertaCaducidadDialog.java

│ │ │ ├── controllers/

│ │ │ │ ├── LoginController.java

│ │ │ │ ├── DespensaController.java

│ │ │ │ ├── ListaComprasController.java

│ │ │ │ ├── RecetasController.java

│ │ │ │ ├── UsuarioController.java

│ │ │ │ └── AlertaCaducidadController.java

│ │ │ ├── database/

│ │ │ │ ├── DatabaseConnection.java

│ │ │ │ ├── AlimentoDAO.java

│ │ │ │ ├── UsuarioDAO.java

│ │ │ │ └── ListaComprasDAO.java

│ │ │ ├── api/

│ │ │ │ └── RecipeAPIConnector.java

│ │ │ ├── utils/

│ │ │ │ ├── PasswordUtils.java

│ │ │ │ └── DateUtils.java

│ │ │ └── Main.java

│ │ └── resources/

│ │ ├── com/sebasth/

│ │ │ ├── assets/

│ │ │ │ └── images/

│ │ │ ├── styles/

│ │ │ │ └── main.css

│ │ │ ├── views/

│ │ │ │ ├── LoginView.fxml

│ │ │ │ ├── RegistroView.fxml

│ │ │ │ ├── DespensaView.fxml

│ │ │ │ ├── ListaComprasView.fxml

│ │ │ │ ├── RecetasView.fxml

│ │ │ │ └── AlertaCaducidadDialog.fxml

│ │ │ └── config.properties

│ └── test/

│ └── java/

│ └── com/sebasth/

│ └── ...

├── lib/

├── .gitignore

├── README.md

└── pom.xml

###

* **`models/`**: Define las estructuras de datos (Alimento, Usuario) de forma clara y organizada.
* **`views/`**: Contiene la interfaz gráfica amigable de JavaFX, diseñada para una navegación sencilla.
* **`controllers/`**: Implementa la lógica detrás de cada función, haciendo que la aplicación sea fácil de usar.
* **`database/`**: Gestiona la información de tus alimentos y usuarios de manera segura.
* **`api/`**: Permite que AliFood te ofrezca las mejores sugerencias de recetas basadas en lo que tienes.
* **`utils/`**: Incluye herramientas prácticas para el funcionamiento de la aplicación.
* **`Main.java`**: El punto de inicio para que comiences a usar AliFood.
* **`resources/assets/`**: Guarda las imágenes y otros elementos visuales de la aplicación.
* **`resources/styles/`**: Define la apariencia moderna y fácil de leer de AliFood.
* **`resources/views/`**: Contiene los archivos que definen cómo se ve cada pantalla de la aplicación.
* **`lib/`**: Almacena las herramientas externas que AliFood necesita para funcionar.
* **`pom.xml` (o `build.gradle`)**: Ayuda a gestionar las herramientas que AliFood utiliza.

## Cómo Empezar a Usar AliFood

1. Asegúrate de tener instalado el **Java Development Kit (JDK)** en tu computadora.
2. Si utilizas **Maven** o **Gradle**, verifica que estén instalados.
3. Descarga el proyecto AliFood (si lo obtuviste de un repositorio).
4. Abre el proyecto en **IntelliJ IDEA**.
5. Descarga las herramientas necesarias (dependencias) usando **Maven** (`mvn clean install`) o **Gradle** (
   `gradle build`).
6. Ejecuta el archivo `Main.java` que se encuentra en `src/main/java/com/sebasth/`.

**¡Bienvenido a la Cocina Independiente con AliFood!**

AliFood está diseñado para hacer tu vida más fácil mientras te adaptas a vivir solo. ¡Esperamos que disfrutes de la
experiencia y descubras lo sencillo que puede ser gestionar tu despensa y cocinar deliciosas comidas!

## Contribución

Si tienes ideas para mejorar AliFood o encuentras algún problema, ¡no dudes en contribuir!

1. Haz una copia del proyecto (**fork**).
2. Crea una rama para tus ideas (`git checkout -b feature/tu-nueva-idea`).
3. Realiza los cambios que consideres importantes (`git commit -m 'Añade tu increíble idea'`).
4. Sube tus cambios a tu copia del proyecto (`git push origin feature/tu-nueva-idea`).
5. Envía una solicitud para que tus cambios se incluyan en el proyecto principal (**Pull Request**).

## Licencia

[Aquí iría la información de la licencia, si aplica]

## Dependencias

(Las dependencias específicas se listarán aquí, por ejemplo, con Maven:)

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>tu_version</version>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>tu_version_javafx</version>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>tu_version_javafx</version>
</dependency>
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>tu_version</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>tu_version_jackson</version>
</dependency>
