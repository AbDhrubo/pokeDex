package org.unknown.pokedex.models;

public class Pokemon {
    private int number;
    private String name;
    private String type1;
    private String type2;
    private int total;
    private int hp;
    private int attack;
    private int defence;
    private int speed;

    private int generation;
    private Boolean legendary;
    private String imgSrc;

    private float height;
    private float weight;

    private int evolution;
    private String desc;

    private String classification;

    public String getImgSrc() {
        return imgSrc;
    }

//    public Pokemon(int number, String name, String type1, String type2, int total, int hp, int attack, int defence, int speed, int generation, Boolean legendary) {
//        this.number = number;
//        this.name = name;
//        this.type1 = type1;
//        this.type2 = type2;
//        this.total = total;
//        this.hp = hp;
//        this.attack = attack;
//        this.defence = defence;
//        this.speed = speed;
//        this.generation = generation;
//        this.legendary = legendary;
//        this.imgSrc = "img/images/" + String.format("%03d", this.number) + ".png";
//    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        this.imgSrc = "img/images/" + String.format("%03d", this.number) + ".png";
//        System.out.println(this.imgSrc);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public Boolean getLegendary() {
        return legendary;
    }

    public void setLegendary(Boolean legendary) {
        this.legendary = legendary;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getEvolution() {
        return evolution;
    }

    public void setEvolution(int evolution) {
        this.evolution = evolution;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}