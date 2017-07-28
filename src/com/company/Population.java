package com.company;

import java.util.Arrays;
import java.util.Random;

public class Population {

    private static final int TOURNAMENT_SIZE = 3;

    private static final Random rand = new Random(System.currentTimeMillis());

    private float elitism;
    private float mutation;
    private float crossover;
    private Chromosome[] popArr;


    public Population(int size, float crossoverRatio, float elitismRatio,
                      float mutationRatio) {

        this.crossover = crossoverRatio;//0.8
        this.elitism = elitismRatio;//0.1
        this.mutation = mutationRatio;//0.03

        // initial
        this.popArr = new Chromosome[size];
        for (int i = 0; i < size; i++) {
            this.popArr[i] = Chromosome.generateRandom();
        }

        Arrays.sort(this.popArr);
    }

    /**
     * Method used to evolve the population.
     */
    public void evolve() {
        Chromosome[] buffer = new Chromosome[popArr.length];

        // Copy over a portion of the population unchanged, based on
        // the elitism ratio.
        int idx = Math.round(popArr.length * elitism);//取10啪的菁英
        System.arraycopy(popArr, 0, buffer, 0, idx);

        while (idx < buffer.length) {

            if (rand.nextFloat() <= crossover) {//是否交配

                Chromosome[] parents = selectParents();
                Chromosome[] children = parents[0].mate(parents[1]);

                if (rand.nextFloat() <= mutation) {
                    buffer[idx++] = children[0].mutate();
                } else {
                    buffer[idx++] = children[0];
                }

                if (idx < buffer.length) {
                    if (rand.nextFloat() <= mutation) {
                        buffer[idx] = children[1].mutate();
                    } else {
                        buffer[idx] = children[1];
                    }
                }
            } else {
                if (rand.nextFloat() <= mutation) {
                    buffer[idx] = popArr[idx].mutate();
                } else {
                    buffer[idx] = popArr[idx];
                }
            }

            ++idx;
        }

        Arrays.sort(buffer);

        // Reset
        popArr = buffer;
    }


    public Chromosome[] getPopulation() {
        Chromosome[] arr = new Chromosome[popArr.length];
        System.arraycopy(popArr, 0, arr, 0, popArr.length);

        return arr;
    }

    public Chromosome getBest() {

        return popArr[0];
    }


    public float getElitism() {
        return elitism;
    }

    public float getCrossover() {
        return crossover;
    }


    public float getMutation() {
        return mutation;
    }

    private Chromosome[] selectParents() {
        Chromosome[] parents = new Chromosome[2];

        for (int i = 0; i < 2; i++) {
            parents[i] = popArr[rand.nextInt(popArr.length)];
            for (int j = 0; j < TOURNAMENT_SIZE; j++) {
                int idx = rand.nextInt(popArr.length);
                if (popArr[idx].compareTo(parents[i]) < 0) {
                    parents[i] = popArr[idx];
                }
            }
        }

        return parents;
    }
}