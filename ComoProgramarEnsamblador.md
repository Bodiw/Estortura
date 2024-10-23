# Manual de programación en ensamblador

La práctica de programación en ensamblador de Estructura de Computadores consiste en la creación de un compresor de texto.

El emulador utilizado es el basado en el lenguaje del Motorola MC88110

Asique para ayudar un poquito, vamos a ver cómo programar en ensamblador.

---

## De C a Ensamblador

Con unos simples conceptos básicos, la programación en C es bastante ligera y amigable.
Sin embargo, se mire como se mire, la programación en Ensamblador es bastante tortuosa.
Es por ello, que el objetivo de cualquier implementación en ensamblador consiste en una implementación en C, junto a una traducción a ensamblador.
Con unos pocos cambios de mentalidad, ensamblador puede ser ameno.

---

### Macros

Una macro es una expansión de código en la fase de pre-procesado. Cada invocación a una macro en el código, será expandida en su completitud a todo el código en el binario final.
Además, las macros aceptan argumentos, que serán sustituidos de manera literal por su contenido. Considera esto como un "copia y pega" del texto en el argumento al interior de la macro.

```
; Declaración
PUSH: MACRO (registro)
subu r30, r30, 4
st registro, r30, 0
ENDMACRO
; Invocación
PUSH(r10)
```

---

### Bucles

En cualquier idioma de programación, los bucles/iteraciones son un elemento esencial para las tareas repetitivas. Ensamblador no es excepción, asique analicemos la transformación de un clásico bucle `for` a un bucle en ensamblador.
Debido a la no-existencia de bucles en ensamblador, la única manera que tenemos de trabajar en este escenario, es con la _keyword_ de C `goto`, que permite saltar a otro segmento de código de nuestro programa. Analicemos el funcionamiento de un bucle `for`, su estado intermedio, y por último su traducción a C, mediante un ejemplo de un [Cifrador César](https://es.wikipedia.org/wiki/Cifrado_C%C3%A9sar), en el cuál un texto en claro pasa a ser cifrado mediante una operación `c = (m + k) mod(a)`, donde c es el carácter(valor en ascii) cifrado final, m es el carácter original, a es el numero de caracteres en el alfabeto y k es la llave o "contraseña" de nuestro cifrado.

```c
char *text = "mensajesupersecreto\0";
char *ciphertext;
int key = 3;

for(int i = 0; i<20; i++){
    ciphertext[i] = ((text[i] - 'a' + key) % 26) + 'a';
}
```

En este escenario, estamos iterando sobre los 20 caracteres de text (el `null terminator \0` no cuenta lógicamente como parte de la cadena). Convertimos el valor ascii del texto a un numero de 0-26 para representar el valor en el abecedario, realizamos el encriptado, y volvemos a convertir a ascii.
Sin embargo, este es un problema mejor formulado con un bucle while, y notación de punteros en lugar de arrays. El siguiente segmento de código tiene la misma funcionalidad:

```c
char *text = "mensajesupersecreto\0";
char *ciphertext;
int i = 0, key = 3;

while((text + i)* != '\0'){
    (ciphertext + i)* = (((text + i)* - 'a' + key) % 26) + 'a';
    i++;
}
```

Ahora tenemos algo más similar a lo que ocurre en realidad,y no tenemos que contar las letras del texto.

Accedemos a la dirección de las cadenas con un offset i para extraer o guardar valores de la operación de encriptado.

Hasta ahora, el código es literalmente el mismo, solo que escrito de otro modo.
Pero sigue sin ser suficiente, asique vamos a eliminar el uso de `for` y `while` mediante `goto`. Aunque antes de proceder a ello, descompongamos el funcionamiento de estos:

```c
for(<declaración de variable local>; <condición para seguir iterando>; <sentencia al final de cada iteración>){
    <código>
}
```

```c
<declaración de variable local>
while(<condición para seguir iterando>){
    <código>
    <sentencia al final de cada iteración>
}
```

Se nota ya un patrón de como funcionan los bucles, no? Pues procedamos a reescribirlos con `goto` y `tags`:

```c
<declaración de variable local>
goto condicion;
bucle:
    <código>
    <sentencia al final de cada iteración>
condicion:
if (<condición para seguir iterando>)
    goto bucle;
```

Veamos ahora el funcionamiento de este segmento:

1. Declaramos la variable local
2. Saltamos a la condición del bucle, ignorando el código de por medio
   1. Si la condición es verdadera, saltamos de nuevo hacia el inicio del código de dentro del bucle y ejecutamos la primera iteración
   2. Al terminar el código, ejecutamos nuestra sentencia de final de iteración (por lo general es i++)
   3. Analizamos de nuevo la condición del bucle para ver si iteramos de nuevo
3. Si la condición es falsa, se ignora el goto y el código continua sin haber ejecutado ninguna iteración.

Ahora, explicado el esquema anterior, procedamos a transformar nuestro cifrado césar:

```c
char *text = "mensajesupersecreto\0";
char *ciphertext;
int i = 0, key = 3;

goto condicion;
bucle:
    (ciphertext + i)* = (((text + i)* - 'a' + key) % 26) + 'a';
    i++;
condicion:
    if((text + i)* != '\0')
        goto bucle;
```

Enhorabuena! Acabas de entender como funciona un bucle a bajo nivel.
Aunque aún tenemos que bajar un poco más para poder decir que este código es idéntico a ensamblador. La línea que contiene la operación de cifrado tiene demasiadas operaciones, asique vamos a tener que descomponer el bucle un poco más, de tal modo que cada línea contenga una única operación.

```c
char *text = "mensajesupersecreto\0";
char *ciphertext;
int i = 0;
int key = 3;
int aux;

goto condicion;
bucle:
    // Referencia:
    // (ciphertext + i)* = (((text + i)* - 'a' + key) % 26) + 'a';
    aux = aux - 'a';
    aux = aux + key;
    MOD(aux, 26); // Realizamos la operacion modulo con una macro que no vamos a implementar aqui, que en ensamblador no hay instrucción "modulo"
    aux = aux + 'a';
    (ciphertext + i)* = aux; // Recuerdas lo de que con arrays, punteros y memoria se hace por separado? Pues eso
    i = i + 1;
condicion:
    // OJO: Esta es la primera comparación, ademas asignamos a aux_a
    aux = (text + i)*; // Guardamos en aux el carácter actual del texto sin cifrar
    // Importante notar que accesos y escrituras a memoria/arrays se hacen por separado
    if(aux != '\0') // Que no se cumple la condición? ignora el goto
        goto bucle; // Se cumple? A iterar
```

Y ya está, tenemos el bucle traducido. Hay margen de optimización, modulo estará implementado en una macro por separado, pero está listo para el paso final

Antes de proceder, pongamos unas condiciones para el set de instrucciones:

1. Solo contamos con 27 variables (registros), r2...28
2. Las comparaciones requieren de un registro auxiliar para el resultado
3. Los ascii necesitan un valor literal (el numerito, vamos)

Asique con esto montado, elijamos unos registros cualesquiera que usar y su finalidad, así como el set de instrucciones

| Registro | Contenido                       |
| -------- | ------------------------------- |
| r5       | Puntero a Texto sin cifrar      |
| r6       | Puntero a Texto cifrado         |
| r7       | i                               |
| r10      | key                             |
| r15      | aux                             |
| r28      | Registo temporal de comparación |

Y el set de instrucciones:

| Instrucción | Sintaxis                    | Operación                                                            |
| ----------- | --------------------------- | -------------------------------------------------------------------- |
| addu        | addu rD,r1,(r2 \| N)        | Suma sin signo                                                       |
| subu        | subu rD,r1,(r2 \| N)        | Resta sin signo                                                      |
| st          | st rD, r1, r2               | Store(guardar en Memoria principal/Array) contenido de rD en r1 + r2 |
| ld          | ld rD, r1, r2               | Load(sacar de Memoria principal/Array) contenido de r1 + r2 en rD    |
| cmp         | cmp rD, r1, (r2 \| N)       | Comparación en rD de r1 y r2                                         |
| bb1         | bb1 op, rD, (tag \| N \ r1) | branch bit 1 (salta si el bit op está a 1)                           |
| or          | or rD, r1, r2               | Operación OR bit a bit                                               |
| br          | br (tag \| r1)              | Salta r1\*4 instrucciones                                            |

Listo! Vamos allá!

```ens
; Macros disponibles: MOD, LEA, PUSH, POP

; Declara la cadena original en memoria
text: "mensajesupersecreto\0"

; Declaración(inicialización) de variables
LEA(r5, text); La direccion de una tag se guarda con LEA
or r6, r0, 0x100; Guardamos en 0x100 la cadena cifrada
or r7, r0, 0; Inicializamos i a 0
or r10, r0, 3; Inicializamos la llave a 3
or r15, r0, 0; Inicializamos aux a 0

br condicion

bucle:
    subu r15, r15, 97; Convertimos aux a un numero de 0-26
    addu r15, r15, r10; aux = aux + key
    MOD(r15, 26); aux = aux % 26
    addu r15, r15, 97; Convertimos aux de vuelta a ascii
    st r15, r6, r7; (ciphertext + i)* = aux
    addu r7, r7, 1; i++

condicion:
    ld r15, r5, r7; Guardamos en aux el carácter actual del texto sin cifrar
    cmp r28, r15, 0; Comparamos aux con '\0'
    bb1 ne, r28, bucle; Si aux != '\0', salta a bucle

```

Eso es todo! Ya tenemos el bucle traducido, sabemos qué instrucciones usar para replicar las operaciones que hubiesemos usado en C, y con la mentalidad de este problema, podemos atacar cualquier reto en ensamblador

Por ultimo, antes de proceder, veamos algunas instrucciones del MC88110, y cómo se usan

---

## Instruction Set

### Instrucciones Lógicas

Considera cada operando de la operación como un arrays de bits, y el array resultado es almacenado en rD

| Instrucción | Sintaxis            | Operación               |
| ----------- | ------------------- | ----------------------- |
| and         | and rD,r1,(r2 \| N) | Operación AND bit a bit |
| or          | or rD,r1,(r2 \| N)  | Operación OR bit a bit  |
| xor         | xor rD,r1,(r2 \| N) | Operación XOR bit a bit |

Un ejemplo es el siguiente: `or r3, r2(con valor 25), 10`, que realizaría una operación `r3 = 27 = b00011001 | b00001010`

### Instrucciones Aritméticas

| Instrucción | Sintaxis             | Operación                |
| ----------- | -------------------- | ------------------------ |
| addu        | addu rD,r1,(r2 \| N) | Suma sin signo           |
| subu        | subu rD,r1,(r2 \| N) | Resta sin signo          |
| mulu        | mulu rD,r1,(r2 \| N) | Multiplicación sin signo |
| divu        | divu rD,r1,(r2 \| N) | División sin signo       |
| sub         | sub rD,r1,(r2 \| N)  | Resta con signo          |
| cmp         | cmp rD,r1,(r2 \| N)  | Comparación              |

### Instrucciones de Campo de Bit

Al igual que con las instrucciones lógicas, consideraremos los registros y sus contenidos como arrays de bits, que facilitan el entendimiento de la funcionalidad de las instrucciones.
La sintaxis `W5<05>` se refiere a una sección de tamaño _WIDTH_ especificado por 5 bits(0-32), con un desplazamiento _OFFSET_ de 5 bits, osea, un caso particular `2<7>` implica seleccionar 2 bits a partir del bit 7.

Cada W5<05> puede ser reemplazado por un registro, cuyo contenido será interpretado, extrayendo el valor O5 de los 5 bits menos significativos, y W5 de los bits 10-5

```ens
; W5<O5> = 2<7>
;                                                |---|
;   1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1|1 1|1 1 1 1 1 1 1
;                                                |---|
;   Little Endian 31-> 30-> 29->...-> 2-> 1-> 0
```

| Instrucción | Sintaxis                              | Operación                                                                                   |
| ----------- | ------------------------------------- | ------------------------------------------------------------------------------------------- |
| clr         | clr rD, r1, (WIDTH5\<OFFSET5> \| r2)  | Guarda en rD r1 con el campo de bits a 0                                                    |
| set         | set rD, r1, (WIDTH5\<OFFSET5> \| r2)  | Guarda en rD r1 con el campo de bits a 1                                                    |
| ext         | ext rD, r1, (WIDTH5\<OFFSET5> \| r2)  | Guarda en los bits menos significativos de rD el campo de bits de r1 con extensión de signo |
| extu        | extu rD, r1, (WIDTH5\<OFFSET5> \| r2) | Guarda en los bits menos significativos de rD el campo de bits de r1 sin extensión de signo |
| mak         | mak rD, r1, (WIDTH5\<OFFSET5> \| r2)  | Guarda los OFFSET bits menos significativos de r1 con desplazamiento WIDTH en rD            |
| rot         | rot rD, r1, (\<OFFSET5> \| r2)        | Guarda en rD los bits de r1 rotados a la derecha OFFSET veces                               |

### Instrucciones de Control de flujo

| Instrucción | Sintaxis                     | Operación                                                                                                                               |
| ----------- | ---------------------------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| br          | br (r1 \| tag \| N)          | Salta a la tag o dirección de PC + (r1 \| N) \* 4 instrucciones                                                                         |
| bb1         | bb1 op, r1, (tag \| N \| r1) | Salta si el bit op está a 1 en r1 a la tag o dirección de PC + (r1 \| N) \* 4 instrucciones                                             |
| bb0         | bb0 op, r1, (tag \| N \| r1) | Salta si el bit op está a 0 en r1 a la tag o dirección de PC + (r1 \| N) \* 4 instrucciones                                             |
| bsr         | bsr (r1 \| tag \| N)         | Salta a la tag o dirección de PC + (r1 \| N) \* 4 instrucciones y además guarda el PC actual + 4 en r1 (osea, la siguiente instrucción) |

### Instrucciones de Carga y Almacenamiento

| Instrucción | Sintaxis         | Operación                                                                              |
| ----------- | ---------------- | -------------------------------------------------------------------------------------- |
| ld          | ld rD, r1, r2    | Guarda en rD el contenido de la dirección r1 + r2                                      |
| st          | st rD, r1, r2    | Guarda en la dirección r1 + r2 el contenido de rD                                      |
| ld.bu       | ld.bu rD, r1, r2 | Guarda en rD el contenido (byte menos significativo) de la dirección r1 + r2 sin signo |
| st.b        | st.b rD, r1, r2  | Guarda en la dirección r1 + r2 el byte menos significativo de rD                       |

---
