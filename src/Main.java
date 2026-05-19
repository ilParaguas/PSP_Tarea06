
import java.io.*;
import java.util.logging.*;
import java.util.regex.*;

public class Main {

    public static void main(String[] args) throws Exception {

        // Variables para gestionar los bucles de las peticiones de entrada de datos
        boolean usuarioCorrecto = false;
        boolean archivoCorrecto = false;

        // Declaración de variables para el arhivo de registro .log
        Logger logger = Logger.getLogger("Registro");
        FileHandler fh;

        // Variable para almacenar la petición del usuario
        String archivo, usuario;
        Pattern pat = null;
        Matcher mat = null;

        // Abro flujo de entrada para el usuario
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            // Antes de hacer nada, configuro el logger
            fh = new FileHandler("./Registro.log", true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            // Desactivo los logs por pantalla
            logger.setUseParentHandlers(false);

            while (!usuarioCorrecto) {
                // Solicitud de entrada del usuario del cliente
                System.out.println("Introduce nombre de usuario para login (máximo 8 caracteres, en minúscula): ");
                // Entrada de datos
                usuario = br.readLine();
                // Establecimiento de condiciones para la validación del usuario
                pat = Pattern.compile("^[a-z]{8}$");
                mat = pat.matcher(usuario);

                // Si el usuario es correcto sigue la ejecución
                if (mat.find()) {
                    usuarioCorrecto = true;
                    System.out.println("Formato de nombre de usuario correcto... Sesión iniciada...\n");
                    // Registro resultado en Registro.log
                    logger.log(Level.WARNING, "OK: Nombre de usuario cumple las reglas de validación.");

                    while (!archivoCorrecto) {
                        // Solicitud de entrada del archivo a buscar
                        System.out.println("Introduce nombre del fichero (máximo 8 caracteres, extensión de 3 caracteres): ");
                        // Entrada de datos
                        archivo = br.readLine();
                        // Establecimiento de condiciones para la validación del archivo
                        pat = Pattern.compile("^[a-zA-Z]{1,8}\\.[a-zA-Z]{3}$");
                        mat = pat.matcher(archivo);

                        // Si el archivo es correcto sigue la ejecución
                        if (mat.find()) {
                            archivoCorrecto = true;
                            System.out.println("OK:::Formato de nombre de archivo correcto.\n");

                            // Compruebo si el archivo solicitado es el que he creado para la tarea
                            if (archivo.equals("Ejemplo.txt")) {
                                // Mostrar archivo por pantalla
                                String linea;
                                BufferedReader brArchivo = new BufferedReader(new FileReader(archivo));
                                while ((linea = brArchivo.readLine()) != null) {
                                    System.out.println(linea);
                                }
                                // Registro resultado en Registro.log
                                logger.log(Level.WARNING, "OK: Credenciales correctas. Archivo Ejemplo.txt mostrado por pantalla.");
                            } else {
                                System.out.println("Error:::El archivo especificado no existe.");
                                // Registro resultado en Registro.log
                                logger.log(Level.WARNING, "Error: Credenciales correctas, pero el archivo solicitado no existe.");
                            }

                        } else {
                            // Si la entrada de datos del archivo no es correcta lo vuelve a pedir
                            System.out.println("ERROR:::Formato de nombre de archivo incorrecto. Prueba de nuevo.\n");
                            // Registro resultado en Registro.log
                            logger.log(Level.WARNING, "Error: Formato de archivo no cumple las reglas de validación.");
                        }
                    }
                } else {
                    // Si la entrada de datos del nombre de usuario no es correcta lo vuelve a pedir
                    System.out.println("Error:::Nombre de usuario incorrecto. Introdúcelo de nuevo.\n");
                    // Registro resultado en Registro.log
                    logger.log(Level.WARNING, "Error: Nombre de usuario no cumple las reglas de validación.");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
