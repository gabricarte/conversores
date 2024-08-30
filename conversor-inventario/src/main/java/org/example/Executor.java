package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Executor {
    public static void main(String[] args) {

        String folder = "C:\\projetos-spring\\conversor-inventario\\src\\main\\java\\org\\example\\";

        String inputFile = folder + "input.json";   // Nome do arquivo JSON de entrada
        String outputFile = folder +  "output.json"; // Nome do arquivo JSON de saída

        try{
            FileReader reader = new FileReader(inputFile, StandardCharsets.ISO_8859_1);
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject inputJson = new JSONObject(tokener);
            reader.close();

            JSONObject outputJson = new JSONObject();


            if (inputJson.has("id")) {
                outputJson.put("id", inputJson.getNumber("id"));
            } else {
                outputJson.put("id", JSONObject.NULL);
            }

            if (inputJson.has("idLegacy")) {
                outputJson.put("idImproprioInven", inputJson.getNumber("idLegacy"));
            } else {
                outputJson.put("idImproprioInven", JSONObject.NULL);
            }

            if (inputJson.has("regionalCode")) {
                outputJson.put("cdRegional", inputJson.getNumber("regionalCode"));
            } else {
                outputJson.put("cdRegional", JSONObject.NULL);
            }

            if (inputJson.has("cdLastProduct")) {
                outputJson.put("cdUltimProdu", inputJson.getNumber("cdLastProduct"));
            } else {
                outputJson.put("cdUltimProdu", JSONObject.NULL);
            }

            if (inputJson.has("dtOpened")) {
                outputJson.put("dtAberturaInven", inputJson.getString("dtOpened"));
            } else {
                outputJson.put("dtAberturaInven", JSONObject.NULL);
            }

            if (inputJson.has("dtClosed")) {
                outputJson.put("dtEncerramInven", inputJson.getString("dtClosed"));
            } else {
                outputJson.put("dtEncerramInven", JSONObject.NULL);
            }

            if (inputJson.has("inventoryGeral")) {
                outputJson.put("flInventarioGeral", inputJson.getNumber("inventoryGeral"));
            } else {
                outputJson.put("flInventarioGeral", JSONObject.NULL);
            }

            if (inputJson.has("status")) {
                JSONObject internalObject = inputJson.getJSONObject("status");
                JSONObject outputInternalObject = new JSONObject();

                if (internalObject.has("id")) {
                    outputInternalObject.put("id", getNumberValue(internalObject,"id"));
                }
                if (internalObject.has("description")) {
                    outputInternalObject.put("dsStatusInven", getStringValue(internalObject,"description"));
                }

                outputJson.put("tbImproprioInvenStatus", outputInternalObject);
            } else {
                outputJson.put("status", JSONObject.NULL);
            }

            // loop para os produtos
            if (inputJson.has("inventoryProducts")) {
                JSONArray inputArray = inputJson.getJSONArray("inventoryProducts");
                JSONArray outputArray = new JSONArray();

                for (int i = 0; i < inputArray.length(); i++) {
                    JSONObject internalObject = inputArray.getJSONObject(i);
                    JSONObject outputObject = new JSONObject();

                    if (internalObject.has("idProduct")) {
                        outputObject.put("id", getNumberValue(internalObject,"idProduct"));
                    }

                    if (internalObject.has("finished")) {
                        outputObject.put("flFinalizado", getNumberValue(internalObject,"finished"));
                    }

                    if (internalObject.has("finalCount")) {
                        outputObject.put("nrContagemFinal", getNumberValue(internalObject,"finalCount"));
                    }

                    if (internalObject.has("finalCount")) {
                        outputObject.put("nrContagemFinal", getNumberValue(internalObject,"finalCount"));
                    }

                    if (internalObject.has("qtdAdjust")) {
                        outputObject.put("qtAjusteContabil", getNumberValue(internalObject,"qtdAdjust"));
                    }

                    if (internalObject.has("qtdImproperAdjust")) {
                        outputObject.put("qtAjusteImproprio", getNumberValue(internalObject,"qtdImproperAdjust"));
                    }

                    if (internalObject.has("qtdStarted")) {
                        outputObject.put("qtContabilInicio", getNumberValue(internalObject,"qtdStarted"));
                    }

                    if (internalObject.has("qtdFinal")) {
                        outputObject.put("qtContagemFinal", getNumberValue(internalObject,"qtdFinal"));
                    }

                    if (internalObject.has("qtImproperFinal")) {
                        outputObject.put("qtImproprioFinal", getNumberValue(internalObject,"qtImproperFinal"));
                    }

                    if (internalObject.has("qtImproperStarted")) {
                        outputObject.put("qtImproprioInicio", getNumberValue(internalObject,"qtImproperStarted"));
                    }

                    if (internalObject.has("qtPrenote")) {
                        outputObject.put("qtPrenota", getNumberValue(internalObject,"qtPrenote"));
                    }
                    outputArray.put(outputObject);
                }
                outputJson.put("tbImproprioInvenProdutos", outputArray);

                FileWriter writer = new FileWriter(outputFile, StandardCharsets.UTF_8);
                writer.write(outputJson.toString(4)); // 4 é o nível de indentação para formatar o JSON
                writer.close();

                System.out.println("Transformação concluída com sucesso!");
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }
    private static Object getStringValue(JSONObject object, String key) {
        try {
            return object.getString(key);
        } catch (Exception e) {
            return JSONObject.NULL;
        }
    }

    private static Object getNumberValue(JSONObject object, String key) {
        try {
            return object.getNumber(key);
        } catch (Exception e) {
            return JSONObject.NULL;
        }
    }
}