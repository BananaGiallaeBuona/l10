package it.unibo.mvc;

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final  class ConfigurationLoader{

    ConfigurationLoader(){
        //something need to be there for the build
    }

    /**
     * Loads the configuration from the specified resource file.
     *
     * @param resourceName
     * the name of the resource file to load
     *
     * * @return a {@link Configuration} object containing the loaded settings
     *
     * * @throws IOException
     * if an I/O error occurs while reading the file
     *
     * * @throws IllegalStateException
     * if the configuration file contains invalid data
     */
    public static Configuration load(final String resourceName) throws IOException{
        final Configuration.Builder builder = new Configuration.Builder();
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream(resourceName);

        if (inputStream == null) {
            throw new IOException("Resource not found: " + "config.yml");
        }

        try(BufferedReader bReader = new BufferedReader((new InputStreamReader(inputStream)))){
            String line = bReader.readLine();
            //here we do a cycle where we scan every line of the yml
            while (line != null){
                final StringTokenizer tokenizer = new StringTokenizer(line, ": ");
                final String key = tokenizer.nextToken();
                // Qui avviene il collegamento con le parole nel tuo file yml
                selector(builder, tokenizer, key);
                line = bReader.readLine();
            }

        }
        return builder.build();
    }

    private static void selector(final Configuration.Builder builder, final StringTokenizer tokenizer, final String key){
        if (!tokenizer.hasMoreTokens()) {
            return;
        }
        final String value = tokenizer.nextToken();
        if ("minimum".equals(key)) {
            final int intValue = Integer.parseInt(value);
            builder.setMin(intValue);
        } else if ("maximum".equals(key)) {
            final int intValue = Integer.parseInt(value);
            builder.setMax(intValue);
        } else if ("attempts".equals(key)) {
            final int intValue = Integer.parseInt(value);
            builder.setAttempts(intValue);
        }
    }
}