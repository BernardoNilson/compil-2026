PUCRS – Escola Politécnica
Construção de Compiladores Exercícios Análise Léxica
1. Dada a especificação sintática abaixo que reconhece arquivos JSON (versão
simplificada), escreva uma especificação léxica JFLEX que permita realizar o
reconhecimento léxico do programa exemplo apresentado. Para cada token
reconhecido mostre o código do token e seu lexeme, além do número de linha
onde foi reconhecido. No caso de dúvidas sobre o formato dos símbolos consulte a
especificação JSON (https://www.json.org/).

JSON → ARRAY
| OBJECT
OBJECT → "{" MEMBERS "}"
MEMBERS → STRING ":" VALUE
| STRING ":" VALUE "," MEMBERS
ARRAY→ "[" ELEMENTS "]"
ELEMENTS→ ELEMENTS "," VALUE
| VALUE
VALUE→ STRING | NUMBER | OBJECT | ARRAY

Teste com a entrada abaixo:
{
 "id": 1,
 "name": "Toner para Impressora XK 4532",
 "price": 219.23,
 "tags": [ "Toner", "4532" ],
 "stock": {
 "shopping iguatemi": 3
 }
}

2. Uma importante instituição de ensino disponibiliza aos professores um sistema que
permite definir o sistema de avaliação das suas disciplinas. A imagem abaixo
demonstra a interface do sistema.
PUCRS – Escola Politécnica
Construção de Compiladores Exercícios Análise Léxica
Ao submeter o formulário é enviado ao servidor um arquivo com as seguintes
informações, nesta ordem e formato:
• Uma lista de nomes de avaliações, separadas por vírgulas. Cada nome de
avaliação é formado por uma letra seguido, opcionalmente, de uma (única) letra
ou dígito, por exemplo: P1 ou Prova1. Pelo menos um nome deve ser declarado;
• Na linha seguinte, um número inteiro indicando o tipo de substituição. Os valores
possíveis são: 0, 1 ou 2;
• Na linha seguinte a fórmula de avaliação a ser aplicada. Esta formula é uma
expressão aritmética onde podem ser utilizados apenas os operadores
aritméticos clássicos (*, /, + e -), números (inteiros ou em ponto flutuante) e
nomes de avaliações
• Na última linha aparece o nome a ser considerado como nota de substituição.
Esta linha é opcional.
Veja um exemplo de arquivo de entrada (válido):
P1, PS, ME, TF
1
(((P1+ME)+TF)/2)
PS
Escreva, e teste, um anlisador léxico que será futuramente utilizado para análise
sintática do arquivo submetido ao servidor.