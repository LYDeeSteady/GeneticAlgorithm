package com.company;
public class HelloGA {


    public static void main(String[] args) {

        final int populationSize = 2048;
        final int maxGenerations = 16384;
        final float crossoverRatio = 0.8f;
        final float elitismRatio = 0.1f;
        final float mutationRatio = 0.03f;
        long startTime = System.currentTimeMillis();

        // 初始 Population
        Population pop = new Population(populationSize, crossoverRatio,
                elitismRatio, mutationRatio);


        int i = 0;
        Chromosome best = pop.getPopulation()[0];

        while ((i++ <= maxGenerations) && (best.getFitness() != 0)) {
            System.out.println("Generation " + i + ": " + best.getGene());
            pop.evolve();
            best = pop.getPopulation()[0];
        }

        // Get the end time for the simulation.
        long endTime = System.currentTimeMillis();

        // Print out information to the console.
        System.out.println("Generation " + i + ": " + best.getGene());
        System.out.println("Total execution time: " + (endTime - startTime) +
                "ms");
    }
}