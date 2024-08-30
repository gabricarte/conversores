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
        String folder = "C:\\projetos-spring\\conversores\\conversor-improprio-lote\\src\\main\\java\\org\\example\\";

        String inputFile = folder + "input.json";   // Nome do arquivo JSON de entrada
        String outputFile = folder + "output.json"; // Nome do arquivo JSON de saída

        try {
            FileReader reader = new FileReader(inputFile, StandardCharsets.ISO_8859_1);
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject inputJson = new JSONObject(tokener);
            reader.close();

            JSONObject outputJson = new JSONObject();

            if (inputJson.has("invoiceId")) {
                outputJson.put("invoiceId", inputJson.getNumber("invoiceId"));
            } else {
                outputJson.put("invoiceId", JSONObject.NULL);
            }

            if (inputJson.has("keyAccess")) {
                outputJson.put("keyAccess", inputJson.getString("keyAccess"));
            } else {
                outputJson.put("keyAccess", JSONObject.NULL);
            }

            if (inputJson.has("branch")) {
                outputJson.put("branch", inputJson.getString("branch"));
            } else {
                outputJson.put("branch", JSONObject.NULL);
            }

            if (inputJson.has("id")) {
                outputJson.put("id", inputJson.getNumber("id"));
            }

            if (inputJson.has("emissionDate")) {
                outputJson.put("emissionDate", getStringValue(inputJson, "emissionDate"));
            }

            if (inputJson.has("regionalCode")) {
                outputJson.put("regionalCode", getNumberValue(inputJson, "regionalCode"));
            }

            if (inputJson.has("invoiceNumber")) {
                outputJson.put("invoiceNumber", getNumberValue(inputJson, "invoiceNumber"));
            }

            if (inputJson.has("type")) {
                JSONObject internalObject = inputJson.getJSONObject("type");
                JSONObject outputInternalObject = new JSONObject();

                if (internalObject.has("id")) {
                    outputInternalObject.put("id", getNumberValue(internalObject, "id"));
                }
                if (internalObject.has("description")) {
                    outputInternalObject.put("description", getStringValue(internalObject, "description"));
                }

                outputJson.put("type", outputInternalObject);
            }

            if (inputJson.has("status")) {
                JSONObject internalObject = inputJson.getJSONObject("status");
                JSONObject outputInternalObject = new JSONObject();

                if (internalObject.has("id")) {
                    outputInternalObject.put("id", getNumberValue(internalObject, "id"));
                }
                if (internalObject.has("description")) {
                    outputInternalObject.put("description", getStringValue(internalObject, "description"));
                }

                outputJson.put("status", outputInternalObject);
            }

            if (inputJson.has("operator")) {
                JSONObject internalObject = inputJson.getJSONObject("operator");

                if (internalObject.has("code")) {
                    outputJson.put("operatorCode", getNumberValue(internalObject, "code"));
                }

            }

            if (inputJson.has("items")) {
                JSONArray inputArray = inputJson.getJSONArray("items");
                JSONArray outputArray = new JSONArray();

                for (int i = 0; i < inputArray.length(); i++) {
                    JSONObject internalObject = inputArray.getJSONObject(i);
                    JSONObject outputObject = new JSONObject();
                    if (internalObject.has("id")) {
                        outputObject.put("id", getNumberValue(internalObject, "id"));
                    }

                    if (internalObject.has("product")) {
                        outputObject.put("product", getNumberValue(internalObject, "product"));
                    }
                    if (internalObject.has("quantity")) {
                        outputObject.put("quantity", getNumberValue(internalObject, "quantity"));
                    }
                    if (internalObject.has("reason")) {
                        JSONObject arrayInternalObject = internalObject.getJSONObject("reason");
                        outputObject.put("reason", arrayInternalObject);
                    }
                    if (internalObject.has("lotNumber")) {
                        outputObject.put("lotNumber", getStringValue(internalObject, "lotNumber"));
                    }
                    if (internalObject.has("flLotIllegible")) {
                        outputObject.put("flLotIllegible", internalObject.getBoolean("flLotIllegible"));
                    }
                    if (internalObject.has("dueDate")) {
                        outputObject.put("dueDate", getStringValue(internalObject, "dueDate"));
                    }
                    if (internalObject.has("policyDestination")) {
                        JSONObject arrayInternalObject = internalObject.getJSONObject("policyDestination");
                        outputObject.put("policyDestination", arrayInternalObject);
                    }
                    if (internalObject.has("cdAddress")) {
                        outputObject.put("cdAddress", getNumberValue(internalObject, "cdAddress"));
                    }
                    if (internalObject.has("balanceId")) {
                        outputObject.put("balanceId", getNumberValue(internalObject, "balanceId"));
                    }
                    if (internalObject.has("invoiceId")) {
                        outputObject.put("invoiceId", getNumberValue(internalObject, "invoiceId"));
                    }
                    if (internalObject.has("invoiceNumberItem")) {
                        outputObject.put("invoiceNumberItem", getNumberValue(internalObject, "invoiceNumberItem"));
                    }
                    if (internalObject.has("provider")) {
                        outputObject.put("provider", getNumberValue(internalObject, "provider"));
                    }
                    if (internalObject.has("producer")) {
                        outputObject.put("producer", getNumberValue(internalObject, "producer"));
                    }
                    if (internalObject.has("policyId")) {
                        outputObject.put("policyId", getNumberValue(internalObject, "policyId"));
                    }

                    if (internalObject.has("operator")) {
                        JSONObject internalOperator = inputJson.getJSONObject("operator");
                        // Adapte conforme os campos internos que deseja transformar
                        if (internalOperator.has("code")) {
                            outputObject.put("operatorCode", getNumberValue(internalOperator, "code"));
                        }
                    }

                    if (internalObject.has("valueCostRaia")) {
                        outputObject.put("valueCostRaia", getNumberValue(internalObject, "valueCostRaia"));
                    }
                    outputArray.put(outputObject);
                }

                outputJson.put("items", outputArray);
            }

            FileWriter writer = new FileWriter(outputFile, StandardCharsets.UTF_8);
            writer.write(outputJson.toString(4)); // 4 é o nível de indentação para formatar o JSON
            writer.close();

            System.out.println("Transformação concluída com sucesso!");

        } catch (IOException e) {
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