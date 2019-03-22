
## **Configure seu projeto**
* [Etapa 1: Configurar a tokenização de pagamento](https://docs.mundipagg.com/v1/docs/google-paytm#section--etapa-1-configurar-a-tokeniza-o-de-pagamento-)
* [Etapa 2: Definir o ambiente de teste](https://docs.mundipagg.com/v1/docs/google-paytm#section--etapa-2-definir-o-ambiente-de-teste-)
* [Etapa 3: Definir as redes de cartões de pagamento compatíveis](https://docs.mundipagg.com/v1/docs/google-paytm#section--etapa-3-definir-as-redes-de-cart-es-de-pagamento-compat-veis-)
* [Etapa 4: Descrever as formas de pagamento permitidas](https://docs.mundipagg.com/v1/docs/google-paytm#section--etapa-4-descrever-as-formas-de-pagamento-permitidas-)


## **Etapa 1: Configurar a tokenização de pagamento**
A Mundipagg utiliza o tipo de tokenização de **PAYMENT_GATEWAY**, que é a implementação de comerciante mais comum da forma de pagamento com cartão na API Google Pay.
Além disso, 2 parâmetros são necessários para realizar a construção do token: 
- Nome do Gateway: **mundipagg**.
- **ID do comerciante** que irá integrar com a Mundipagg.


``` java
  public static final String PAYMENT_GATEWAY_TOKENIZATION_NAME = "mundipagg";
  public static final String PAYMENT_GATEWAY_TOKENIZATION_ID = "SEU_MERCHANT_ID";

  public static final HashMap<String, String> PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS =
      new HashMap<String, String>() {
        {
          put("gateway", Constants.PAYMENT_GATEWAY_TOKENIZATION_NAME);
          put("gatewayMerchantId", Constants.PAYMENT_GATEWAY_TOKENIZATION_ID);
        }
      };
```

## **Etapa 2: Definir o ambiente de teste**
A definição do ambiente é importante, pois definirá a estrutura do token gerado pela API Google Pay. E também influenciará no momento da descriptografia que a Mundipagg executará.
Para configurar o ambiente, basta definir a variável citada abaixo:
``` java
public static final int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;
```



## Etapa 3: Definir as redes de cartões de pagamento compatíveis 
Defina as redes de cartões que seu aplicativo aceita:
``` java
public static final List<String> SUPPORTED_NETWORKS = Arrays.asList(
  "MASTERCARD",	
  "DISCOVER",  
  "AMEX",    
  "VISA"
  "JCB");
```

## **Etapa 4: Descrever as formas de pagamento permitidas**
A API Google Pay pode retornar cartões registrados em arquivo no Google.com.br (PAN_ONLY) e/ou um token de dispositivo em um dispositivo Android autenticado com um criptograma 3-D Secure (CRYPTOGRAM_3DS).
``` java
  public static final List<String> SUPPORTED_METHODS =
      Arrays.asList(
          "PAN_ONLY",
          "CRYPTOGRAM_3DS");
  ```
**PAN_ONLY:** A API irá criptografar os detalhes do cartão de forma simples. Sem levar em consideração uma autenticação a mais no meio do processo de pagamento.
**CRYPTOGRAM_3DS:** A cada requisição de pagamento, a API irá gerar uma cartão digital para a compra atual, elevando assim o nível de segurança daquela transação.

**A Mundipagg oferece suporte para transações autenticadas via criptograma 3D-Secure. O que pode aumentar ainda mais a segurança com a qual o lojista realiza suas transações. [Saiba mais sobre transações autenticadas no Gateway Mundipagg](https://docs.mundipagg.com/v1/reference?showHidden=0922f#cart%C3%A3o-de-d%C3%A9bito-1)**

**Testar e implantar**
Ao final das configurações, o envio do token para a API do lojista já poderá ser testado.
a classe ***CheckoutTokenSender***  se encarregará de mapear os campos necessários para enviar para o gateway **Mundipagg**. 

**Desta forma, o cliente já estará apto a transacionar com GooglePay.**
<center><img src="https://files.readme.io/06ae222-b8211fb-GPay-android-mock.png" width="200" height="400" /></center>
