package com.cooking_ideas_thymeleaf.cooking_ideas_thymeleaf.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.net.URLEncoder;

class Plat {
    String id;
    String name;
    String image;
    String level;
    int preparation_time;
    int cooking_time;
    String ingr;
    String recette;
    List<String> ingredients;
    List<String> recipe;
    List<User> commentaries;
    int nbRealisation;
    int nbLike;

    //Constructors
    public Plat(){

    }
    public Plat(String idN, String nom, String img, String lvl, int prep, int cook, List<String> ingr, List<String> recip, List<User> comm, int nb, int nb1){
        this.setId(idN);
        this.setName(nom);
        this.setImage(img);
        this.setLevel(lvl);
        this.setPreparation_time(prep);
        this.setCooking_time(cook);
        this.setIngredients(ingr);
        this.setRecipe(recip);
        this.setCommentaries(comm);
        this.setNbRealisation(nb);
        this.setNbLike(nb1);
    }

    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setCooking_time(int cooking_time) {
        this.cooking_time = cooking_time;
    }

    public void setPreparation_time(int preparation_time) {
        this.preparation_time = preparation_time;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setCommentaries(List<User> commentaries) {
        this.commentaries = commentaries;
    }

    public void setRecipe(List<String> recipe) {
        this.recipe = recipe;
    }

    public void setNbRealisation(int nbRealisation) {
        this.nbRealisation = nbRealisation;
    }

    public void setNbLike(int nbLike) {
        this.nbLike = nbLike;
    }

    public void setIngr(String ingr) {
        this.ingr = ingr;
    }

    public void setRecette(String recette) {
        this.recette = recette;
    }
    //Getters


    public String getIngr() {
        return ingr;
    }

    public String getRecette() {
        return recette;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getLevel() {
        return level;
    }

    public int getPreparation_time() {
        return preparation_time;
    }

    public int getCooking_time() {
        return cooking_time;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getRecipe() {
        return recipe;
    }

    public List<User> getCommentaries() {
        return commentaries;
    }

    public int getNbRealisation() {
        return nbRealisation;
    }

    public int getNbLike() {
        return nbLike;
    }

    public void morph(){
        ArrayList<String> ing = new ArrayList<String>();
        ArrayList<String> rec = new ArrayList<String>();
        String[] ingre = getIngr().split("\\r\\n");
        String[] rece = getRecette().split("\\r\\n");
        for (String liste:ingre) {
            ing.add(liste);
        }
        for (String liste:rece){
            rec.add(liste);
        }
        this.setIngredients(ing);
        this.setRecipe(rec);
    }
    public JSONObject getPLatById(String id) throws Exception{
        JSONParser parser = new JSONParser();
        Plat ans=new Plat();
        URL oracle = new URL("https://nameless-escarpment-94857.herokuapp.com/PlatList"); // URL to Parse
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        JSONObject obj=null;
        String inputLine;
        int i=0;
        while ((inputLine = in.readLine()) != null) {
            JSONArray a = (JSONArray) parser.parse(inputLine);
            for(Object o : a){
                JSONObject json=(JSONObject)o;
                if(json.get("id").equals(id)){
                    obj=json;
                    break;
                }
            }

        }
        in.close();
        return obj;

    }
    public Plat parseJson(JSONObject obj) throws Exception{
        String id=(String)obj.get("id");
        String name=(String)obj.get("nom");
        System.out.println(name);
        String image=(String)obj.get("image");
        System.out.println(image);
        String level=(String)obj.get("niveau");
        System.out.println(level);
        int prep=Integer.parseInt((String)obj.get("preparation"));
        System.out.println(prep);
        int cuisson=Integer.parseInt((String)obj.get("cuisson"));
        System.out.println(cuisson);
        List<String> ingr=new ArrayList<String>();
        JSONArray ing = (JSONArray)obj.get("ingredient");
        System.out.println(ing);
        for (Object o: ing)
        {
            System.out.println(o);
            ingr.add((String)o);
        }
        List<String> recipe=new ArrayList<String>();
        JSONArray recip=(JSONArray)obj.get("recette");
        for (Object o: recip)
        {
            recipe.add((String)o);
            System.out.println(o);
        }
        List<User> commentaires=new ArrayList<User>();
        JSONArray comms=(JSONArray)obj.get("commentaire");
        System.out.println(comms);
        if(comms!=null){
            for(Object o: comms){
                JSONObject json=(JSONObject)o;
                System.out.println(json);
                String nId = (String)json.get("id");
                String nUname =  (String)json.get("identifiant");
                String com = (String)json.get("commentaire");
                System.out.println(nId);
                System.out.println(nUname);
                System.out.println(com);
                User usr=new User(nId, nUname, com);
                commentaires.add(usr);
                System.out.println("OK");
            }
        }
        System.out.println("Nivoaka");
        int nbLike=Integer.valueOf(((Long)obj.get("nombrejaime")).intValue());
        System.out.println(nbLike);
        int nbReal=Integer.valueOf(((Long)obj.get("nombrerealisation")).intValue());
        System.out.println(nbReal);

        Plat ans=new Plat(id, name, image, level, prep, cuisson, ingr, recipe, commentaires, nbReal, nbLike);
        //JSONArray json=(JSONArray)o;;
        //String status = (String) statusObj.get("status");

        return ans;
    }
    public List<Plat> getListePlat(String url, int limit) throws Exception{
        List<Plat> ans = new ArrayList<>();
        JSONParser parser = new JSONParser();
        URL oracle = new URL(url); // URL to Parse
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        int i=0;
        while ((inputLine = in.readLine()) != null) {
            JSONArray a = (JSONArray) parser.parse(inputLine);
            int j=0;
            for(Object o : a){
                JSONObject json=(JSONObject)o;
                Plat temp = this.parseJson(json);
                ans.add(temp);
                if(limit>0){
                    if(j==limit){
                        break;
                    }
                    j++;
                }

            }

        }
        in.close();
        return ans;
    }
    public String format(String a)throws Exception{
        a= com.sun.deploy.net.URLEncoder.encode(a, "UTF-8");
        return a;
    }
    public List<Plat> getAllPlat() throws Exception{
        List<Plat> ans = this.getListePlat("https://nameless-escarpment-94857.herokuapp.com/PlatList", 0);
        return ans;
    }
    public List<Plat> getMostLiked(int limit) throws Exception{
        List<Plat> ans = this.getListePlat("https://nameless-escarpment-94857.herokuapp.com/PlatLike", limit);
        return ans;
    }
    public List<Plat> getMostRealised(int limit) throws Exception{
        List<Plat> ans = this.getListePlat("https://nameless-escarpment-94857.herokuapp.com/PlatRealisation", limit);
        return ans;
    }
    public Boolean insert(){
        try{
            JSONParser parser = new JSONParser();
            String nom = this.getName();
            nom=format(nom);
            String image= "platInserer.jpg";
            String niveau= this.getLevel();
            String preparation = Integer.toString(this.getPreparation_time());
            String cuisson = Integer.toString(this.getCooking_time());
            List<String> ingredient = this.getIngredients();
            List<String> recette = this.getRecipe();
            String i="";
            for (String ingr:ingredient) {
                i=i.concat(ingr);
                if(ingredient.indexOf(ingr)!=ingredient.size()-1){
                    i=i.concat(",");
                }
            }
            i=format(i);
            String r="";
            for (String ingr:recette) {
                r=r.concat(ingr);
                if(recette.indexOf(ingr)!=recette.size()-1){
                    r=r.concat(",");
                }
            }
            r=format(r);
            URL oracle = new URL("https://nameless-escarpment-94857.herokuapp.com/InsertPlat?nom="+nom+"&image="+image+"&niveau="+niveau+"&preparation="+preparation+"&cuisson="+cuisson+"&ingredient="+i+"&recette="+r);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            JSONObject statusObj=null;
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                statusObj = (JSONObject) parser.parse(inputLine);
            }

            in.close();


            //JSONArray json=(JSONArray)o;;
            System.out.println(statusObj);
            //String status = (String) statusObj.get("status");
            String message = (String) statusObj.get("status");
            System.out.println(message);
            if(Objects.equals(message, "OK")){
                return true;
            }
            else
                return false;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
