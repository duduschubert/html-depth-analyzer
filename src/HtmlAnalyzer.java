import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Stack;

public class HtmlAnalyzer {

	public static void main(String[] args) {
		
		// Exige que somente 1 argumento/link seja usado no terminal
        if (args.length != 1) { 
        	// Se for diferente de 1 o programa para com erro de conexão.
            System.out.println("URL connection error"); 
            return;
        }

     // urlString será a variável que irá guardar o link para verificação posterior
        String urlString = args[0]; 

        try { 
        	// Cria um objeto URL a partir da String recebida como argumento.
            @SuppressWarnings("deprecation")
			URL url = new URL (urlString);
            
            // O método openConnection() retorna uma URLConnection genérica,
            // por isso fazemos o cast para HttpURLConnection,
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Abrimos a conexão
            
            // Define que queremos BUSCAR os dados (requisição GET)
            connection.setRequestMethod("GET");

         // Cria um leitor para ler o conteúdo que o servidor enviou
            BufferedReader reader = new BufferedReader(

                    // Converte os bytes recebidos da conexão em texto (caracteres)
                    new InputStreamReader(

                            // Pega os dados (HTML) vindos do servidor
                            connection.getInputStream()

                    )
            );
           
            // Atribução de tipagem e criação de variáveis de profundidade atual, máxima e texto mais profundo
            String line; 
            int currentDepth = 0;
            int maxDepth = 0;
            String deepestText = "";

            Stack<String> stack = new Stack<>();

            while ((line = reader.readLine()) != null) { // O leitor lê a linha e será TRUE se for DIFERENTE de nulo

                line = line.trim(); // Remove todos os espaços vazios

                if (line.isEmpty()) { /* SE estiver vazio, o programa continuará,
                					ignorando linhas em branco conforme especificado */
                    continue;
                }

                if (line.startsWith("</")) { // Checa se começa com FECHAMENTO, se for TRUE...

                    String tagName = line.substring(2, line.length() - 1); // Verifica qual é a TAG

                    if (stack.isEmpty() || !stack.peek().equals(tagName)) { 
                    	// Verifica se a pilha está vazia OU o topo da pilha NÃO é igual ao nome da tag
                        System.out.println("malformed HTML"); 
                        // O erro acontece caso UMA afirmação for verdadeira
                        return; // Se qualquer uma das condições for verdadeira → HTML malformado → programa para
                    }

                    stack.pop(); // Aqui ele limpa a stack para que seja analisada a próxima tag
                    currentDepth--; // Subtrai os níveis de profundidade apontando que foram abertos e fechados corretamente

                } else if (line.startsWith("<")) { // Checa se é uma ABERTURA, se for TRUE..

                    String tagName = line.substring(1, line.length() - 1); // Verifica qual é a TAG
                    stack.push(tagName); // Insere ela na pilha
                    currentDepth++; // Aumenta a contagem de profundidade

                } else { // Caso ambas as afirmações acima sejam falsas é por que não há tag e sim, TEXTO

                    if (currentDepth > maxDepth) { // Verifica se a profundidade atual é maior que profundidade máxima
                        maxDepth = currentDepth; // Atribui a profundidade máxima caso a atual seja a maior
                        deepestText = line; // Atribui o texto mais profundo à variável line
                    }
                }
            }

            reader.close(); // Fecha o leitor
          
         // Verifica se ainda existem tags abertas ou se a profundidade não voltou ao zero
            if (!stack.isEmpty() || currentDepth != 0) { 
            // Se alguma tag ficou sem fechamento ou estrutura inconsistente	
                System.out.println("malformed HTML"); 
             // Encerra o programa
                return;
            }
            
         // Se passou por todas as verificações e está tudo correto
            System.out.println(deepestText);

        } catch (Exception e) { // Caso ocorram erros de conexão (URL inválida, sem internet ou timeout)
            System.out.println("URL connection error"); // Print conforme especificado
        }
    }
}