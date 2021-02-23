//! # Práctica 2 IC: Algortimos genéticos
//! En esta práctica se pide resolver el problema de la asignación cuadrática mediante
//! algoritmos genéticos. Este problema se puede reducir a encontrar un f que minimice la
//! siguiente sumatoria:
//!
//! ![\sum _{a,b\in P}w(a,b)\cdot d(f(a),f(b))](https://wikimedia.org/api/rest_v1/media/math/render/svg/05d30494a1a27d09c83ad564ccaf41194006d89e)
//!
//! Donde w y d son dos matrices que vienen en el archivo `tai256c.dat` de la biblioteca
//! [QAPLIB](http://www.seas.upenn.edu/qaplib/).

pub mod genetic_algorithm;
pub mod tai256c;

use genetic_algorithm::{
    baldwinian, lamarckian, naive, random_population, GeneticAlgorithmBuilder,
};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    println!("Executing naive genetic algorithm");
    let result = GeneticAlgorithmBuilder::default()
        .evaluation_function(naive)
        .population_generator(random_population)
        .generations(10000)
        .population_size(200)
        .survivors(100)
        .n_parents(2)
        .n_sons(2)
        .mutation_probability(0.3)
        .max_mutations(2)
        .genetic_mix(1)
        .build()?
        .execute();

    println!("Best result:");
    println!("Cost: {}", result.0);
    println!("Solution: {:?}\n", result.1);

    println!("Executing baldwinian genetic algorithm");
    let result = GeneticAlgorithmBuilder::default()
        .evaluation_function(baldwinian)
        .population_generator(random_population)
        .generations(100)
        .population_size(100)
        .survivors(50)
        .n_parents(2)
        .n_sons(2)
        .mutation_probability(0.3)
        .max_mutations(2)
        .genetic_mix(1)
        .build()?
        .execute();

    println!("Best result:");
    println!("Cost: {}", result.0);
    println!("Solution: {:?}\n", result.1);

    println!("Executing lamarckian genetic algorithm");
    let result = GeneticAlgorithmBuilder::default()
        .evaluation_function(lamarckian)
        .population_generator(random_population)
        .generations(100)
        .population_size(100)
        .survivors(50)
        .n_parents(2)
        .n_sons(2)
        .mutation_probability(0.3)
        .max_mutations(2)
        .genetic_mix(1)
        .build()?
        .execute();

    println!("Best result:");
    println!("Cost: {}", result.0);
    println!("Solution: {:?}\n", result.1);

    Ok(())
}
