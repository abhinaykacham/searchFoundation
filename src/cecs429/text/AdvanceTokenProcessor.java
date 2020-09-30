package cecs429.text;

import org.tartarus.snowball.SnowballStemmer;

import java.lang.StringBuilder;
import java.lang.String;
import java.util.*;


/**
 * A advance creates terms from tokens by removing non-alphanumeric
 * characters from the beginning and end of the token, but not the middle.
 * 
 * Remove all apostropes or quotation marks (single or double quotes) from anywhere in the string.
 * 
 */
public class AdvanceTokenProcessor implements TokenProcessor {
	@Override
	public List<String> processToken(String token) {
        List<String> terms = new ArrayList();
        StringBuilder builder = new StringBuilder(token);
        int i=0;
        while(i<builder.length()){
            if(Character.isDigit(builder.charAt(i)) || Character.isLetter(builder.charAt(i)))
                break;
            i++;
        }
        if(i!=0)
            builder.delete(0,i-1);

        int j=builder.length()-1;
        while(j>=0){
            if(Character.isDigit(builder.charAt(j)) || Character.isLetter(builder.charAt(j)))
                break;
            j--;
        }
        if(j!=builder.length()-1)
            builder.delete(j+1,builder.length());
        String term = builder.toString().replaceAll("\"|'", "");
        //TODO: Need to add stemming code
        if(term.indexOf('-')==-1){
            term = term.toLowerCase(Locale.ENGLISH);
            //terms.add(term);
            try {
                terms.add(stemProcess(term));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else{
           // terms.addAll(Arrays.asList(term.split("-")));
           String[] arrofStr=term.split("-");
           for(String a: arrofStr){
               try {
                   terms.add(stemProcess(a));
               } catch (Throwable throwable) {
                   throwable.printStackTrace();
               }
           }
            try {
                terms.add(stemProcess(term.replaceAll("-","")));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
		return terms;
    }


    String stemProcess(String token) throws Throwable{
       /* Stemmer s=new Stemmer();
        char[] n=token.toCharArray();
        s.add(n,n.length);
        s.stem();
        String u=s.toString();
        return u;
        */
        Class stemClass = Class.forName("org.tartarus.snowball.ext.englishStemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
        stemmer.setCurrent(token);
        stemmer.stem();
        return stemmer.getCurrent();

	}
}
