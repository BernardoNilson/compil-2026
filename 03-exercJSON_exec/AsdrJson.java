import java.io.*;

public class AsdrJson {

  private static final int BASE_TOKEN_NUM = 301;
  
  public static final int STRING        = 301;
  public static final int NUM 	       = 302;
  public static final int JSON          = 303;
  public static final int OBJECT        = 304;
  public static final int MEMBERS       = 305;
  public static final int MEMBERS_RESTO = 306;
  public static final int ARRAY         = 307;
  public static final int ELEMENTS      = 308;
  public static final int R             = 309;
  public static final int VALUE         = 310;

    public static final String tokenList[] = 
      {"STRING",
		 "NUM",
       "JSON",
       "OBJECT",
       "MEMBERS",
       "MEMBERS_RESTO",
       "ARRAY",
       "ELEMENTS",
       "R",
       "VALUE"
       };
                                      
  /* referencia ao objeto Scanner gerado pelo JFLEX */
  private Yylex lexer;

  public ParserVal yylval;

  private static int laToken;
  private boolean debug;

  
  /* construtor da classe */
  public AsdrJson (Reader r) {
      lexer = new Yylex(r, this);
  }

  /***** Gramática original 
 
   JSON --> ARRAY
         | OBJECT

   OBJECT: "{" MEMBERS "}"
   
   MEMBERS: STRING ":" VALUE
      |   STRING ":" VALUE "," MEMBERS
   
   ARRAY: "[" ELEMENTS "]"
   
   ELEMENTS: ELEMENTS "," VALUE
      | VALUE
   
   VALUE: STRING
      | NUMBER
      | OBJECT
      | ARRAY
***/  

/***** Gramática AJUSTADA 
 
   JSON --> ARRAY
         | OBJECT

   OBJECT: "{" MEMBERS "}"
   
   MEMBERS: STRING ":" VALUE MEMBERS_RESTO

   MEMBERS_RESTO: "," MEMBERS
             | vazio
   
   ARRAY: "[" ELEMENTS "]"

   ELEMENTS: VALUE R
   
   R: ELEMENTS "," R
      | vazio
   
   VALUE: STRING
      | NUMBER
      | OBJECT
      | ARRAY
***/  

   private void Json() {
      if (laToken == ARRAY) {
         if (debug) System.out.println("JSON --> ARRAY");
         ARRAY();
      }
      else if (laToken == OBJECT) {
         if (debug) System.out.println("JSON --> OBJECT");
         OBJECT();
      }
      else 
        yyerror("esperado 'ARRAY' ou 'OBJECT'");
   }

   private void OBJECT() {
      if (laToken == '{') {
         if (debug) System.out.println("OBJECT --> { MEMBERS }");
         verifica('{'); 
         MEMBERS();
         verifica('}');
      }
      else 
        yyerror("esperado '{'");
   }

   private void ARRAY() {
      if (laToken == '[') {
         if (debug) System.out.println("ARRAY --> [ ELEMENTS ]");
         verifica('[');
         ELEMENTS();
         verifica(']');
      }
      else 
        yyerror("esperado '['");
   }

   private void MEMBERS() {
      if (laToken == STRING) {
         if (debug) System.out.println("MEMBERS --> STRING : VALUE MEMBERS_RESTO");
         verifica(STRING);
         verifica(':');
         VALUE();
         MEMBERS_RESTO();
      }
      else 
        yyerror("esperado 'STRING'");
   }

   private void ELEMENTS() {
      if (laToken == STRING) {
         if (debug) System.out.println("ELEMENTS --> VALUE R");
         VALUE();
         R();
      }
      else 
        yyerror("esperado 'STRING' ou 'NUMBER' ou 'OBJECT' ou 'ARRAY'");
   }

   private void VALUE() {
      // TODO
   }


  private void verifica(int expected) {
      if (laToken == expected)
         laToken = this.yylex();
      else {
         String expStr, laStr;       

		expStr = ((expected < BASE_TOKEN_NUM )
                ? ""+(char)expected
			     : tokenList[expected-BASE_TOKEN_NUM]);
         
		laStr = ((laToken < BASE_TOKEN_NUM )
                ? (char)laToken+""
                : tokenList[laToken-BASE_TOKEN_NUM]);

          yyerror( "esperado token : " + expStr +
                   " na entrada: " + laStr);
     }
   }

   /* metodo de acesso ao Scanner gerado pelo JFLEX */
   private int yylex() {
       int retVal = -1;
       try {
           yylval = new ParserVal(0); //zera o valor do token
           retVal = lexer.yylex(); //le a entrada do arquivo e retorna um token
       } catch (IOException e) {
           System.err.println("IO Error:" + e);
          }
       return retVal; //retorna o token para o Parser 
   }

  /* metodo de manipulacao de erros de sintaxe */
  public void yyerror (String error) {
     System.err.println("Erro: " + error);
     System.err.println("Entrada rejeitada");
     System.out.println("\n\nFalhou!!!");
     System.exit(1);
     
  }

  public void setDebug(boolean trace) {
      debug = true;
  }


  /**
   * Runs the scanner on input files.
   *
   * This main method is the debugging routine for the scanner.
   * It prints debugging information about each returned token to
   * System.out until the end of file is reached, or an error occured.
   *
   * @param args   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String[] args) {
     AsdrJson parser = null;
     try {
         if (args.length == 0)
            parser = new AsdrJson(new InputStreamReader(System.in));
         else 
            parser = new  AsdrJson( new java.io.FileReader(args[0]));

          parser.setDebug(false);


          laToken = parser.yylex();          

          parser.Json();
     
          if (laToken== Yylex.YYEOF)
             System.out.println("\n\nSucesso!");
          else     
             System.out.println("\n\nFalhou - esperado EOF.");               

        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+args[0]+"\"");
        }
//        catch (java.io.IOException e) {
//          System.out.println("IO error scanning file \""+args[0]+"\"");
//          System.out.println(e);
//        }
//        catch (Exception e) {
//          System.out.println("Unexpected exception:");
//          e.printStackTrace();
//      }
    
  }
  
}

