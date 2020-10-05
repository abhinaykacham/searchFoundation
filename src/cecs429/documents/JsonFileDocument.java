package cecs429.documents;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFileDocument implements FileDocument{
    private int mDocumentId;
    private Path mFilePath;
    private String title;
    private String url;
    private String body;
    private String author;


    /**
     * Constructs a JsonFileDocument with the given document ID representing the file at the given
     * absolute file path.
     */
    public JsonFileDocument(int id, Path absoluteFilePath) {
        mDocumentId = id;
        mFilePath = absoluteFilePath;
        /*(try(Reader reader = Files.newBufferedReader(mFilePath)) {
            Gson gson = new Gson();
            title = gson.fromJson(reader, Park.class).getTitle();
        }catch(Exception e){
            e.printStackTrace();
        }*/


        try(Reader reader = Files.newBufferedReader(mFilePath)) {
            Gson gson = new Gson();
            //System.out.println(hasAuthor());
            if(hasAuthor()==false){
                Park park=gson.fromJson(reader,Park.class);
                title = park.getTitle();
                url= park.getUrl();
                body=park.getBody();
            }
            else{
                Article article;
                article=gson.fromJson(reader,Article.class);
                title=article.getTitle();
                url=article.getUrl();
                author=article.getAuthor();
                body=article.getBody();
                //System.out.println(body);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Boolean hasAuthor() {
        try (Reader reader = Files.newBufferedReader(mFilePath)) {
            Gson gson = new Gson();
            author = gson.fromJson(reader, Article.class).getAuthor();
            if (author != null) {
                return true;
            }
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }        return false;
    }
    public Reader getAuthor(){
        Reader stringReader=null;
        stringReader=new StringReader(author);
        return stringReader;
    }
    @Override
    public Path getFilePath() {
        return mFilePath;
    }

    @Override
    public int getId() {
        return mDocumentId;
    }

    @Override
    public Reader getContent() {
        Reader stringReader=null;
        stringReader=new StringReader(body);
        return stringReader;
    }

    @Override
    public String getTitle() {
        return title;
    }
    public static FileDocument loadJsonFileDocument(Path absolutePath, int documentId) {
        return new JsonFileDocument(documentId, absolutePath);
    }
}
