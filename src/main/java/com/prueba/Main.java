package com.prueba;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        /**
         * El archivo moviesFiles carga los detalles de las películas directamente
         * del archivo peliculas.csv y transforma estos datos en un ArrayList.
         */
        File moviesFiles = new File( "peliculas.csv");
        ArrayList<Movie> movies = new ArrayList<>();

        /**
         *
         * Aquí se crean objetivos Movie a partir de cada línea.
         */
        try(BufferedReader br = new BufferedReader(new FileReader(moviesFiles))) {
            String linea;
            while((linea = br.readLine()) != null) {
                var lineParts = linea.split(",");
                movies.add(new Movie(
                        /**
                         * Cada línea separa por "," divide a la película en id, título,
                         * año, director y género.
                         */
                        lineParts[0],
                        lineParts[1],
                        Integer.parseInt(lineParts[2]),
                        lineParts[3],
                        lineParts[4]
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Aquí creamos el StringBuilder resultado para almacenar el contenido del archivo
         * template.html
         */
        StringBuilder resultado= new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader("template.html"))) {
            String linea;
            while((linea = br.readLine()) != null) {
                resultado.append(linea).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Por último creamos la carpeta salida, y una vez hecho esto, a través de
         * BufferedWriter creamos un archvo html por cada película.
         */
        File carpeta = new File("salida");
        carpeta.mkdir();
        for (Movie m : movies) {
            try(BufferedWriter bw = new BufferedWriter((new FileWriter("salida/"+m.getTitle()+"-"+m.getId()+".html")))) {
                bw.write(
                        resultado.toString()
                                .replace("%%1%%", m.getId())
                                .replace("%%2%%", m.getTitle())
                                .replace("%%3%%", m.getYear().toString())
                                .replace("%%4%%", m.getDirector())
                                .replace("%%5%%", m.getGenre())
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
