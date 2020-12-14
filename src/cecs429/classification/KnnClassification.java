package cecs429.classification;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.DocumentCorpus;
import cecs429.index.DiskPositionalIndex;
import cecs429.index.Index;
import cecs429.index.Posting;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class KnnClassification {
    String path;
    String hamiltonPath;
    String jayPath;
    String madisonPath;
    String disputedPath;
    Index hamiltonIndex;
    Index jayIndex;
    Index madisonIndex;
    Index disputedIndex;
    DocumentCorpus jayCorpus;
    DocumentCorpus madisonCorpus;
    DocumentCorpus hamiltonCorpus;
    Set<String> vocabOfAllClasses;
    Map<Integer,Map<String,Double>> hamiltonDocumentVectors = new HashMap<>();
    Map<Integer,Map<String,Double>> madisonDocumentVectors = new HashMap<>();
    Map<Integer,Map<String,Double>> jayDocumentVectors = new HashMap<>();



    public KnnClassification(String path){
        this.path = path;
        this.hamiltonPath = path + File.separator + "HAMILTON";
        this.jayPath = path + File.separator + "JAY";
        this.madisonPath = path + File.separator + "MADISON";
        this.disputedPath = path + File.separator + "DISPUTED";
        hamiltonIndex = new DiskPositionalIndex(hamiltonPath+File.separator+"index");
        jayIndex = new DiskPositionalIndex(jayPath+File.separator+"index");
        madisonIndex = new DiskPositionalIndex(madisonPath+File.separator+"index");
        disputedIndex = new DiskPositionalIndex(disputedPath+File.separator+"index");
        vocabOfAllClasses = new HashSet<>();
        //classToCentroid = new HashMap<>();
    }
    public Map<String, String> classify(){
        int k=3;
        Map<String,String> result = new HashMap<>();
        mergeVocabs();
        Map<Integer,Map<String,Double>> disputedDocumentVectors = new HashMap<>();

        // Map<disputedDocId,Map<ClassName,Map<docId,distance>>>
        Map<Integer,Map<String,Map<Integer,Double>>> distFrmDisputedDocToClassifiedDocs=new HashMap<>();

ArrayList<String> v=new ArrayList<>(vocabOfAllClasses);
Collections.sort(v);
System.out.println(v);
        for(String vocab:vocabOfAllClasses){
            List<Posting> disputedTermPostings = disputedIndex.getPostingsWithOutPositions(vocab);
            List<Posting> hamiltonTermPostings = hamiltonIndex.getPostingsWithOutPositions(vocab);
            List<Posting> madisonTermPostings = madisonIndex.getPostingsWithOutPositions(vocab);
            List<Posting> jayTermPostings = jayIndex.getPostingsWithOutPositions(vocab);

            if(disputedTermPostings!=null)
                for(Posting posting:disputedTermPostings){
                    Map<String,Double> disputedDocVector = disputedDocumentVectors.getOrDefault(posting.getDocumentId(), new HashMap<>());
                    disputedDocVector.put(vocab, posting.getWdt()/disputedIndex.getDocLength(posting.getDocumentId()));
                    disputedDocumentVectors.put(posting.getDocumentId(),disputedDocVector);
                }


            if(hamiltonTermPostings!=null)
                for(Posting posting:hamiltonTermPostings){
                    Map<String,Double> hamiltonDocVector = hamiltonDocumentVectors.getOrDefault(posting.getDocumentId(), new HashMap<>());
                    hamiltonDocVector.put(vocab, posting.getWdt()/hamiltonIndex.getDocLength(posting.getDocumentId()));
                    hamiltonDocumentVectors.put(posting.getDocumentId(),hamiltonDocVector);
                }

            if(madisonTermPostings!=null)
                for(Posting posting:madisonTermPostings){
                    Map<String,Double> madisonDocVector = madisonDocumentVectors.getOrDefault(posting.getDocumentId(), new HashMap<>());
                    madisonDocVector.put(vocab, posting.getWdt()/madisonIndex.getDocLength(posting.getDocumentId()));
                    madisonDocumentVectors.put(posting.getDocumentId(),madisonDocVector);
                }

            if(jayTermPostings!=null)
                for(Posting posting:jayTermPostings){
                    Map<String,Double> jayDocVector = jayDocumentVectors.getOrDefault(posting.getDocumentId(), new HashMap<>());
                    jayDocVector.put(vocab, posting.getWdt()/jayIndex.getDocLength(posting.getDocumentId()));
                    jayDocumentVectors.put(posting.getDocumentId(),jayDocVector);
                }

        }
        System.out.println( "Document vectors");
        System.out.println( disputedDocumentVectors.get(5));
       System.out.println( disputedDocumentVectors.get(6));
        System.out.println( disputedDocumentVectors.get(7));
        DocumentCorpus disputedCorpus = DirectoryCorpus.loadDirectory(Paths.get(disputedPath).toAbsolutePath());
        disputedCorpus.getDocuments();
        hamiltonCorpus = DirectoryCorpus.loadDirectory(Paths.get(hamiltonPath).toAbsolutePath());
        hamiltonCorpus.getDocuments();
         madisonCorpus = DirectoryCorpus.loadDirectory(Paths.get(madisonPath).toAbsolutePath());
        madisonCorpus.getDocuments();
        jayCorpus = DirectoryCorpus.loadDirectory(Paths.get(jayPath).toAbsolutePath());
        jayCorpus.getDocuments();
        for(Integer disputedDocId: disputedDocumentVectors.keySet())
        {

            Map<String,Map<Integer,Double>> distToDoc=new HashMap<>();
            distToDoc.put("hamilton",new HashMap<>());
            distToDoc.put("madison",new HashMap<>());
            distToDoc.put("jay",new HashMap<>());
            Map<String,Double> disputedDocVector=disputedDocumentVectors.get(disputedDocId);
            for(Integer hamiltonDocId: hamiltonDocumentVectors.keySet())
            {

                Double distance=Double.valueOf(0);
                for (String term : vocabOfAllClasses) {

                    distance+= Math.pow((hamiltonDocumentVectors.get(hamiltonDocId).containsKey(term)? hamiltonDocumentVectors.get(hamiltonDocId).get(term) : 0)
                              - (disputedDocVector.containsKey(term)?disputedDocVector.get(term):0),2);
                }
                distToDoc.get("hamilton").put(hamiltonDocId,Math.sqrt(distance));
            }
            for(Integer madisonDocId: madisonDocumentVectors.keySet())
            {
                Double distance=Double.valueOf(0);
                for (String term : vocabOfAllClasses) {

                    distance+= Math.pow((madisonDocumentVectors.get(madisonDocId).containsKey(term)? madisonDocumentVectors.get(madisonDocId).get(term) : 0)
                            - (disputedDocVector.containsKey(term)?disputedDocVector.get(term):0),2);
                }
                distToDoc.get("madison").put(madisonDocId,Math.sqrt(distance));
            }
            for(Integer jayDocId: jayDocumentVectors.keySet())
            {
                Double distance=Double.valueOf(0);
                for (String term : vocabOfAllClasses) {

                    distance+= Math.pow((jayDocumentVectors.get(jayDocId).containsKey(term)? jayDocumentVectors.get(jayDocId).get(term) : 0)
                            - (disputedDocVector.containsKey(term)?disputedDocVector.get(term):0),2);
                }
                distToDoc.get("jay").put(jayDocId,Math.sqrt(distance));
            }

            distFrmDisputedDocToClassifiedDocs.put(disputedDocId,distToDoc);
            result.put(disputedCorpus.getDocument(disputedDocId).getDocumentName(),knnClassifier( k,distToDoc));
        }



        return result;
    }

    private String knnClassifier(int k, Map<String,Map<Integer, Double> > distToDoc) {

        //index 0="hamilton",1="Madison",2="jay"
       int[] nearestClassCount=new int[3];
        int docId;
        System.out.println("------Before Sorting-------");
        System.out.println("hamiltonEntryList:"+distToDoc.get("hamilton"));
        System.out.println("madisonEntryList:"+distToDoc.get("madison"));
        System.out.println("jayEntryList:"+distToDoc.get("jay"));
        System.out.println("------After Sorting-------");
         List<Map.Entry<Integer,Double>> hamiltonEntryList=new LinkedList<>(distToDoc.get("hamilton").entrySet());
         Collections.sort(hamiltonEntryList, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        List<Map.Entry<Integer,Double>> madisonEntryList=new LinkedList<>(distToDoc.get("madison").entrySet());
        Collections.sort(madisonEntryList, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        List<Map.Entry<Integer,Double>> jayEntryList=new LinkedList<>(distToDoc.get("jay").entrySet());
        Collections.sort(jayEntryList, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        System.out.println("hamiltonEntryList:"+hamiltonEntryList);
        System.out.println("madisonEntryList:"+madisonEntryList);
        System.out.println("jayEntryList:"+jayEntryList);
        Double ham,mad,jay;
        StringBuilder nearestTo=new StringBuilder();
       while (nearestClassCount[0]+nearestClassCount[1]+nearestClassCount[2]<k)
       {
           ham=hamiltonEntryList.get(nearestClassCount[0]).getValue();
           mad=madisonEntryList.get(nearestClassCount[1]).getValue();
           jay=jayEntryList.get(nearestClassCount[2]).getValue();

           if(ham<jay && ham<mad) //h>m>=j || h>j>=m
           {
               nearestTo.append(hamiltonCorpus.getDocument(hamiltonEntryList.get(nearestClassCount[0]).getKey()).getDocumentName()+"("+ham+");");
               nearestClassCount[0]++;


           }
           else if((ham==jay && ham<mad)) //((jay==ham && jay>mad))

           {
               nearestTo.append(hamiltonCorpus.getDocument(hamiltonEntryList.get(nearestClassCount[0]).getKey()).getDocumentName()+"("+ham+");");
               nearestTo.append(jayCorpus.getDocument(jayEntryList.get(nearestClassCount[2]).getKey()).getDocumentName()+"("+jay+");");
               nearestClassCount[0]++;
               nearestClassCount[2]++;
           }
           else if((ham==mad && ham<jay))//((mad==ham && mad>jay))

           {
               nearestTo.append(hamiltonCorpus.getDocument(hamiltonEntryList.get(nearestClassCount[0]).getKey()).getDocumentName()+"("+ham+");");
               nearestTo.append(madisonCorpus.getDocument(madisonEntryList.get(nearestClassCount[1]).getKey()).getDocumentName()+"("+mad+");");
               nearestClassCount[0]++;
               nearestClassCount[1]++;
           }
           else if(mad<ham && mad<jay) //h>m>=j || h>j>=m
           {
               nearestTo.append(madisonCorpus.getDocument(madisonEntryList.get(nearestClassCount[1]).getKey()).getDocumentName()+"("+mad+");");
               nearestClassCount[1]++;

           }
           else if((mad==jay && mad<ham)) //((jay==mad && jay>ham))
           {
               nearestTo.append(jayCorpus.getDocument(jayEntryList.get(nearestClassCount[2]).getKey()).getDocumentName()+"("+jay+");");
               nearestTo.append(madisonCorpus.getDocument(madisonEntryList.get(nearestClassCount[1]).getKey()).getDocumentName()+"("+mad+");");
               nearestClassCount[1]++;
               nearestClassCount[2]++;
           }
           else if(jay<ham && jay<mad) //h>m>=j || h>j>=m
           {
               nearestTo.append(jayCorpus.getDocument(jayEntryList.get(nearestClassCount[2]).getKey()).getDocumentName()+"("+jay+");");
               nearestClassCount[2]++;

           }

           else
           {
               System.out.println("ham="+ham+",mad="+mad+",jay="+jay );
               //jay=mad=ham;
               nearestTo.append(hamiltonCorpus.getDocument(hamiltonEntryList.get(nearestClassCount[0]).getKey()).getDocumentName()+"("+ham+");");
               nearestTo.append(madisonCorpus.getDocument(madisonEntryList.get(nearestClassCount[1]).getKey()).getDocumentName()+"("+mad+");");
               nearestTo.append(jayCorpus.getDocument(jayEntryList.get(nearestClassCount[2]).getKey()).getDocumentName()+"("+jay+");");
               nearestClassCount[2]++;
               nearestClassCount[1]++;
               nearestClassCount[0]++;

           }


       }


        System.out.println("------------------------------------------------");
       System.out.println("nearestClassCount[0]:"+ nearestClassCount[0]+", nearestClassCount[1]:"+ nearestClassCount[1]+", nearestClassCount[2]:"+ nearestClassCount[2]);
       System.out.println("------------------------------------------------");

        if(nearestClassCount[0]>nearestClassCount[2] && nearestClassCount[0]>nearestClassCount[1]) //h>m>=j || h>j>=m
        {
            nearestTo.append(" Class: hamilton");
            return nearestTo.toString();
        }
        else if((nearestClassCount[0]==nearestClassCount[2] && nearestClassCount[0]>nearestClassCount[1])) // h=j>m || h=m>j
        {
            if(k==1) {
                System.out.println("2 equal");
                System.out.println("hamiltoncount="+nearestClassCount[0]+" madisionCount="+ nearestClassCount[1]+" jayCount="+ nearestClassCount[2]);
                nearestTo.append(" Class: hamilton / Jay");
                return nearestTo.toString();
            }
            return knnClassifier(k-1,distToDoc);
        }
        else if((nearestClassCount[0]>nearestClassCount[2] && nearestClassCount[0]==nearestClassCount[1])) // h=j>m || h=m>j
        {
            if(k==1) {
                System.out.println("2 equal");
                System.out.println("hamiltoncount="+nearestClassCount[0]+" madisionCount="+ nearestClassCount[1]+" jayCount="+ nearestClassCount[2]);
                nearestTo.append(" Class: hamilton / madison");
                return nearestTo.toString();
            }
            return knnClassifier(k-1,distToDoc);
        }
        else  if(nearestClassCount[1]>nearestClassCount[2] && nearestClassCount[1]>nearestClassCount[0]) //m>h>=j || m>j>=h
        {

            nearestTo.append(" Class: madison");
            return nearestTo.toString();
        }
        else if(nearestClassCount[1]==nearestClassCount[2] && nearestClassCount[1]>nearestClassCount[0])// m=j>h || m=h>j
        {
            if(k==1) {
                System.out.println("2 equal");
                System.out.println("hamiltoncount="+nearestClassCount[0]+" madisionCount="+ nearestClassCount[1]+" jayCount="+ nearestClassCount[2]);
                nearestTo.append(" Class: madison / Jay");
                return nearestTo.toString();
            }
            return knnClassifier(k-1,distToDoc);
        }
        else if(nearestClassCount[2]>nearestClassCount[0] && nearestClassCount[2]>nearestClassCount[1]) //j>h>=m || j>m>=h
        {
            nearestTo.append(" Class:Jay");
            return nearestTo.toString();
        }

        else //j=m=h
        {
            if(k==1) {
                System.out.println("all equal");
                System.out.println("hamiltoncount="+nearestClassCount[0]+" madisionCount="+ nearestClassCount[1]+" jayCount="+ nearestClassCount[2]);
                nearestTo.append(" Class: hamilton / jay / madison");
                return nearestTo.toString();
            }
            return  knnClassifier(k-1,distToDoc);
        }
    }




    void mergeVocabs(){
        vocabOfAllClasses.addAll(disputedIndex.getVocabulary());
        vocabOfAllClasses.addAll(hamiltonIndex.getVocabulary());
        vocabOfAllClasses.addAll(madisonIndex.getVocabulary());
        vocabOfAllClasses.addAll(jayIndex.getVocabulary());
    }


}
