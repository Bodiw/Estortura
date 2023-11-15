package me.bodiw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnsambladorMapper {

    static class MacroInfo {
        int instructionCount;

        public MacroInfo(int instructionCount) {
            this.instructionCount = instructionCount;
        }
    }

    private static Map<String, MacroInfo> macros = new HashMap<>();

    public static String lineaEnEjecucion(String archivoEnsamblador, int direccionMemoria){

        try {
            // Lee el archivo y carga las líneas de código fuente
            String[] lineasCodigo = leerCodigoFuente(archivoEnsamblador);
            // Calcula la línea correspondiente a la dirección de memoria
            int indiceLinea = encontrarIndiceLinea(lineasCodigo, direccionMemoria);

            // Muestra las líneas de código fuente circundantes
            return mostrarLineasCircundantes(lineasCodigo, indiceLinea, direccionMemoria);

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return "Error: No se pudo obtener la linea";
    }

    public static void main(String[] args) {
        System.out.println(lineaEnEjecucion("cvd.ens",2000));
    }

    private static String[] leerCodigoFuente(String archivo) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea;
        StringBuilder codigo = new StringBuilder();

        while ((linea = lector.readLine()) != null) {
            codigo.append(linea).append('\n');
        }

        lector.close();

        return codigo.toString().split("\n");
    }

    private static int encontrarIndiceLinea(String[] lineasCodigo, int direccionMemoria) {
        int indiceLinea = 0;
        int direccionActual = 0;

        int coolDownMacro = 0;

        for (String linea : lineasCodigo) {
            if (!(linea.trim().startsWith(";") || linea.trim().equals("")) && direccionActual >= direccionMemoria) {
                    break;
            }
            // System.out.println(Integer.toString(indiceLinea)+" "+linea);
            if (linea.trim().startsWith(";")) {
                // System.out.println("Salto -> "+ linea);
            } else if (linea.contains("MACRO") && linea.contains("(")) {
                String[] partes = linea.split("\\s+|\\(|,|\\)");
                String nombreMacro = partes[0].substring(0, partes[0].length() - 1);

                // Contar las líneas de la macro
                int lineasMacro = contarLineasMacro(lineasCodigo, indiceLinea + 1);

                // Almacenar información sobre la macro
                // System.out.println(nombreMacro+"-"+lineasMacro);
                macros.put(nombreMacro, new MacroInfo(lineasMacro));
                coolDownMacro = lineasMacro + 1;
            } else if (coolDownMacro==0 && linea.contains("(")) {
                int indiceDosPuntos = linea.indexOf(":");
                int indiceParentesis = linea.indexOf("(");

                // Determina el índice final para extraer el nombre de la macro
                int indiceInicio = indiceDosPuntos != -1 ? indiceDosPuntos + 1 : 0;

                // Extrae el nombre de la macro y lo trima
                String nombreFuncion = linea.substring(indiceInicio, indiceParentesis).trim();

                if (macros.containsKey(nombreFuncion)) {
                    direccionActual += macros.get(nombreFuncion).instructionCount * 4;
                    // System.out.println("partes[1]:"+nombreFuncion+"-"+macros.get(nombreFuncion).instructionCount);
                } else {
                    // System.out.println("partes[1]:"+nombreFuncion);
                    direccionActual += 4;
                }
            } else if (linea.contains("data")) {
                // Expresión regular para buscar el patrón "data" seguido de un número o texto
                // entre comillas
                // System.out.println("data");
                Pattern pattern = Pattern.compile("data\\s+((\\d+)|\"(\\\\.|[^\\\\\"])*\")");
                Matcher matcher = pattern.matcher(linea);

                if (matcher.find()) {
                    String parametro = matcher.group(1);

                    if (parametro.matches("\\d+")) {
                        // Es un número
                        // System.out.println("numero ->" + parametro);
                        direccionActual += 4;
                    } else {
                        // Es un texto, contar la longitud tratando \c como un solo caracter
                        int longitud = parametro.length();
                        parametro = parametro.replaceAll("\\\\.", " ");
                        int tamano = longitud - (parametro.length() - parametro.replace(" ", "").length());
                        direccionActual += aproximarMultiploDeCuatro(tamano);
                        // System.out.println("dato ->" + parametro + "-" + tamano);
                    }
                } else {
                    // No se encontró el patrón en la entrada
                    System.out.println("Entrada no válida");
                    break;
                }
            } else if (linea.trim().startsWith("org")) {
                String[] partes = linea.trim().split("\\s+");
                if (partes.length > 1) {
                    // Eliminar caracteres no válidos antes de convertir la cadena a número
                    String direccionOrg = partes[1].replaceAll("[^a-fA-F0-9]", "");
                    // System.out.println("->"+direccionOrg+"\n");
                    direccionActual = Integer.parseInt(direccionOrg);
                }
                indiceLinea++;
                continue;
            } else {
                if (!linea.trim().equals("")) {
                    if (coolDownMacro == 0)
                        direccionActual += 4;
                    else{
                        //System.err.println(linea);
                        coolDownMacro--;
                    }
                } // Avanza 4 bytes por cada instrucción
            }

            indiceLinea++;
        }
        //System.out.println("Ultima direccion -> " + direccionActual);
        //System.out.println("Direccion objetivo -> " + direccionMemoria);
        return indiceLinea;
    }

    private static String mostrarLineasCircundantes(String[] lineasCodigo, int indiceLinea, int direccionMemoria) {
        //System.out.println("Direccion memoria: " + direccionMemoria);
        //System.out.println("Indice linea: " + (indiceLinea + 1));
        int lineasAnteriores = 3;
        int lineasPosteriores = 3;
        String salida = "";

        for (int i = Math.max(0, indiceLinea - lineasAnteriores); i <= Math.min(lineasCodigo.length - 1,
                indiceLinea + lineasPosteriores); i++) {
            String numeroLineaCodigo = "" + (i + 1);
            String flecha = (i == indiceLinea) ? "  <---" : "";
            salida += (numeroLineaCodigo + " | " + lineasCodigo[i] + flecha) + "\n";
        }
        return salida;
    }

    public static int aproximarMultiploDeCuatro(int numero) {
        // Dividir el número por 4 y redondear hacia arriba
        int cociente = (int) Math.ceil((double) numero / 4);

        // Multiplicar el cociente por 4 para obtener el múltiplo de 4 más cercano
        int numeroAproximado = cociente * 4;

        return numeroAproximado;
    }

    private static int contarLineasMacro(String[] lineasCodigo, int indiceLinea) {
        int lineas = 0;

        while (indiceLinea < lineasCodigo.length) {
            String linea = lineasCodigo[indiceLinea].trim();
            // System.out.println(linea);
            if (linea.isEmpty() || linea.startsWith(";")) {
                // Ignorar líneas vacías o comentarios
            } else if (linea.contains("ENDMACRO")) {
                // Fin de la macro
                break;
            } else {
                // Contar líneas de la macro
                lineas++;
            }

            indiceLinea++;
        }

        return lineas;
    }
}
