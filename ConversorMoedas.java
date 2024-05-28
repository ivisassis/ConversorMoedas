import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class ConversorMoeda {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/9fcc5160e43434204bfdf246/latest/USD";

    public static void main(String[] args) {
        try {
            // Obter taxas de câmbio da API
            JsonObject taxaCambioJson = obterTaxaCambio(API_URL);

            // Exibir opções de conversão
            exibirOpcoesConversao(taxaCambioJson);

            // Ler entrada do usuário
            Scanner scanner = new Scanner(System.in);
            System.out.print("Selecione a moeda de origem (código): ");
            String moedaOrigem = scanner.nextLine();
            System.out.print("Selecione a moeda de destino (código): ");
            String moedaDestino = scanner.nextLine();
            System.out.print("Digite o valor a ser convertido: ");
            double valor = scanner.nextDouble();

            // Calcular e exibir valor convertido
            double taxaOrigem = taxaCambioJson.getAsJsonObject("conversion_rates").get(moedaOrigem).getAsDouble();
            double taxaDestino = taxaCambioJson.getAsJsonObject("conversion_rates").get(moedaDestino).getAsDouble();
            double valorConvertido = (valor / taxaOrigem) * taxaDestino;
            System.out.println("Valor convertido: " + valorConvertido + " " + moedaDestino);
        } catch (IOException e) {
            System.out.println("Erro ao obter taxas de câmbio da API: " + e.getMessage());
        }
    }

    private static JsonObject obterTaxaCambio(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        return new Gson().fromJson(reader, JsonObject.class);
    }

    private static void exibirOpcoesConversao(JsonObject taxaCambioJson) {
        JsonObject conversionRates = taxaCambioJson.getAsJsonObject("conversion_rates");
        System.out.println("Opções de conversão disponíveis:");
        for (String moeda : conversionRates.keySet()) {
            System.out.println(moeda + " - " + conversionRates.get(moeda).getAsDouble());
        }
    }
}
