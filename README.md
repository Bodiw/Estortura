# Estortura

Estructura va a terminar con mi Cordura

![image](https://github.com/Bodiw/Estortura/assets/58666162/01c13382-cbb3-4213-ad85-484debb25356)

## Descripcion

Estortura es una GUI en Java que sirve como "Wrapper" del Emulador MC88110
En otras palabras, envuelve el emulador de ensamblador del MC88110 y le proporciona una interfaz grafica a la salida generada por este.

## Funcionalidades

### Display de Registros , Registo de control y Memoria Principal

La GUI representa en tiempo real el valor de los registros y de la Memoria principal uno junto al otro

### Highlight de actualizaciones

Tanto en registros como en Memoria, un cambio en el valor actualiza el color de la celda a uno mas llamativo para avisar de un cambio ocurrido. Si la celda en algun momento fue modificada pero no en la ultima instruccion, se muestra un color mas apagado

### Traductor Ascii

Tanto los registros como la MP disponen de un panel Ascii adyacente que proporciona una traduccion a ASCII instantanea

### Reload

Mi parte favorita.
Un unico boton, que vuelve a compilar y ejecutar el archivo de codigo fuente, permitiendo rapidas actualizaciones de codigo.

El uso del compilador genera un archivo temporal en el directorio actual `estortura.bin`

### Bitmap

Tanto en configuracion inicial como haciendo click sobre una celda de Registros o Memoria o una Ascii, actualiza el Bitmap a seguir dicha celda y mostrar su valor en bits.

### Step

Ejecucion secuencial de 'Steps' Instrucciones al pulsar el boton. Se puede usar la rueda de raton para cambiar su valor

### Memoria

Al igual que Step, la rueda de raton cambia su valor. Memoria muestra la direccion a partir de la cual mostrar la MemoriaPrincipal. Tras cambiar el valor, cualquier actualizacion o pulsr en 'Memoria' actualizara la memoria principal

### Stdin/Stdout

La pestaña de Sdtout junto a Memoria permite ver la salida del emulador como fue diseñado en su principio.
Stdin proporciona enviar comandos a este.
No todas las acciones de la GUI proporcionan feedback a traves de Stdout, pero si todas aquellas mediante Stdin.

Stdin funciona tanto como pulsando el boton 'Stdin' para enviar el comando como la tecla Enter. La tecla Esc permite desenfocar el campo de texto

### Last/Next

Displays de ultimo comando y de siguiente instruccion del emulador

## Configuracion

El archivo `config.json` en la raiz del repositorio sirve como ejemplo de una configuracion base para la GUI.
Por defecto, la GUI busca un `config.json` en el directorio activo, pero tambien se puede sobreescribir mediante argumentos en la linea de comandos, llamandolo como `java -jar estortura.jar miConfiguracion.json`, teniendo java instalado (Esta version ha sido construida con Java 17)

Los campos del `config.json` son siguientes:
| Campo | Descripcion |
| --- | --- |
| EMULADOR| Direccion del archivo ejecutable del emulador |
| COMPILADOR| Direccion del archivo ejecutable del compilador |
| CONF| Direccion del archivo de configuracion del emulador |
| CODIGO| Codigo fuente de Ensamblador, el famoso 'CVD.ens' |
| SCALE| Escala de la GUI, por defecto es 1.1 |
| STEP_INICIO| Valor inicial del boton de Step |
| SKIP_INICIO| Cuantas instrucciones ejecutar al incio del programa|
| MEM_ADDR_INICIO| Direccion de Memoria que mostrar en GUI al iniciar el programa |
| BITMAP_TYPE| Que campo debe enseñarse en el bitmap, `REGISTER` si registro o `MEMORY` si Memoria Principal|
| BITMAP_ADDR| Direccion a coger como bitmap. Si es registro, muestra un registro 0-31 o en el caso de la MP, la palabra de la direccion|
| TAG_INICIO| Etiqueta del inicio del programa "START" en el codigo fuente ensamblador |
| TAG_BREAKPOINT| En caso de haber, punto de ruptura hasta el que ejecutar al iniciar la GUI, similar a SKIP_INICIO |

Nota: En un sistema Windows, los Directorios deben ser especificados con doble barra invertida `\\` en lugar de una sola `/`, como lo esta en e `config.json` de ejemplo.
Primero se ejecuta `TAG_BREAKPOINT` y luego `SKIP_INICIO`, por lo que es poosible poner un skip a partir de una etiqueta
El bitmap muestra en binario una celda de la memoria o de los registros, no el valor en si. Si cambia la direccion de la memoria en dicha celda, tambien lo hace el bitmap.

## Ejecucion

El proyecto esta escrito en Java 17, por tanto esta o versiones mas recientes deberian de poder ejecutarlo sin problemas.

Se puede obtener un ejecutable .jar desde releases, o clonar la repo, abrir un IDE en el proyecto y ejecutar main desde me.bodiw.App

Se requiere configurar el archivo `config.json`, dado que la GUI es un WRAPPER, no el emulador en si.

Intentar no pasarle nombres "Graciosos", dado que la GUI parsea la salida del emulador. Meter "PC = 99999" por Stdin en algun otro modo es mas que capaz de confundir a la GUI. En general evitar patrones/nombres que use el Emulador en si

En caso de problemas , se puede ejecutar desde una terminal con `java -jar estortura.jar miConfiguracion.json` para ver los mensajes de error a traves de consola.
