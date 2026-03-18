import java.io.InputStreamReader;
%%


%public
%class MeuLexico
%integer
%unicode
%line


%{

// A partir de 256
public static int STRING	= 257;
public static int VALUE	  = 258;

public static int IF 			= 259; 
public static int ELSE 		= 260;
public static int PUBLIC 	= 261;
public static int PRIVATE = 262;
public static int CLASS		= 263;
public static int EQUALS	= 264;
public statis int PROVA   = 374;


/**
   * Runs the scanner on input files.
   *
   * This is a standalone scanner, it will print any unmatched
   * text to System.out unchanged.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String argv[]) {
    MeuLexico scanner;
    if (argv.length == 0) {
      try {        
          // scanner = new MeuLexico( System.in );
          scanner = new MeuLexico( new InputStreamReader(System.in) );
          while ( !scanner.zzAtEOF ) 
	        System.out.println("token: "+scanner.yylex()+"\t<"+scanner.yytext()+">");
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
        
    }
    else {
      for (int i = 0; i < argv.length; i++) {
        scanner = null;
        try {
          scanner = new MeuLexico( new java.io.FileReader(argv[i]) );
          while ( !scanner.zzAtEOF ) 	
                System.out.println("token: "+scanner.yylex()+"\t<"+scanner.yytext()+">");
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv[i]+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv[i]+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
      }
    }
  }


%}

DIGIT=		[0-9]
LETTER=		[a-zA-Z]
WHITESPACE=	[ \t]
LineTerminator = \r|\n|\r\n    

%%

{LETTER}({LETTER}|{DIGIT})?         {return PROVA;}
{DIGIT}+(\.{DIGIT}+)?               {return VALUE;}

"+" |
"-" |
"*" |
"/" |
"," |
"(" |
")" | {return yytext().charAt(0);}

{WHITESPACE}+       { }
{LineTerminator}		{}
.                   {System.out.println(yyline+1 + ": caracter invalido: "+yytext());}
