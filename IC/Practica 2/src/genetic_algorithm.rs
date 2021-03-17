//! # Genetic Algorithm
//! Este módulo contiene las funciones necesarias para ejecutar el algoritmo genético.

use crate::tai256c::Tai256c;
use derive_builder::Builder;
use lazy_static::lazy_static;
use rand::{prelude::SliceRandom, thread_rng, Rng};
use rayon::prelude::*;
use rustc_hash::FxHashSet;

lazy_static! {
    /// Constante global que contiene una instancia del struct `Tai256c`. Tiene que ser declarada de esta
    /// forma porque Rust no admite que una constante global sea inicializada con la llamada a una función.
    static ref TAI256C: Tai256c = Tai256c::new();
}
/// Struct que contiene los parámetros necesarios para una ejecución del algoritmo genético.
#[derive(Builder)]
pub struct GeneticAlgorithm {
    /// Función de evaluación de la población. Los posibles valores son `naive`, `baldwinian`
    /// o `lamarckian`.
    evaluation_function: fn(Vec<Vec<usize>>) -> Vec<(u64, Vec<usize>)>,
    /// Función que genera la publación inicial. Los posibles valores son `random_population`,
    ///`locally_optimized_population` o `optimized_population`.
    population_generator: fn(usize) -> Vec<Vec<usize>>,
    /// Número de generaciones que va a durar la ejecución.
    generations: u32,
    /// Tamaño de la población del algorimo genético.
    population_size: usize,
    /// Numero medio de supervivientes que van a sobrevivir para reproducirse en cada generación.
    /// Debe ser fijado de acuerdo al número de hijos.
    survivors: usize,
    /// Número de hijos que va a tener cada pareja. Debe de ser fijado de acuerdo al numero de
    /// supervivientes ya que si no se tiene cuidado la población puede explotar en tamaño o extinguirse.
    n_sons: usize,
    /// Número de padres que se van a cruzar para generar un hijo.
    n_parents: usize,
    /// Probabilidad de que un hijo sufra una mutación.
    mutation_probability: f64,
    /// Máximo numero de mutaciones que puede sufrir un hijo. Si el valor es inferior a 2 el algoritmo falla.
    max_mutations: usize,
    /// Determina cuanto se van a mezclar las permutaciones a la hora de generar un hijo.
    /// Cada padre es dividido en un número de segmentos equivalente al número de padres multiplicado por  
    /// este atributo.  Luego se compone un hijo seleccionando aleatoriamente el mismo número de segmentos
    /// de cada padre. Si vale 0 el algoritmo falla.
    genetic_mix: usize,
}
impl GeneticAlgorithm {
    /// Ejecuta el algoritmo genético utilizando los parámetros del struct. Devuelve una tupla que contiene el
    /// coste de la mejor solución obtenida, la mejor solución y un vector que contiene como ha ido mejorando
    /// la solución cada 50 generaciones
    pub fn execute(&self) -> (u64, Vec<usize>, Vec<(u32, u64)>) {
        let rng = &mut thread_rng();
        let save_interval = 50;
        let mut best = (u64::MAX, vec![]);
        let mut history = Vec::with_capacity((self.generations / save_interval) as usize);
        let mut population = (self.population_generator)(self.population_size);
        for g in 0..=self.generations {
            let mut population_rank = (self.evaluation_function)(population);
            population_rank.par_sort_unstable_by(|a, b| a.0.cmp(&b.0));
            if population_rank[0].0 < best.0 {
                best = population_rank[0].clone();
            }
            if g % save_interval == 0 {
                history.push((g, best.0));
            }
            let mut selected_permutations = select_population(population_rank, self.survivors);
            selected_permutations.shuffle(rng);
            population = new_generation(
                &selected_permutations,
                self.n_parents,
                self.mutation_probability,
                self.max_mutations,
                self.n_sons,
                self.genetic_mix,
            );
        }
        (best.0, best.1, history)
    }
}

/// Genera una población de tamaño `size` de permutaciones aleatorias.
pub fn random_population(size: usize) -> Vec<Vec<usize>> {
    let rng = &mut thread_rng();
    (0..size)
        .into_iter()
        .map(|_| {
            let mut v: Vec<_> = (0..=255).collect();
            v.shuffle(rng);
            v
        })
        .collect()
}

/// Genera una población de tamaño `size` de permutaciones optimizadas con la función [`greedy()`].
pub fn locally_optimized_population(size: usize) -> Vec<Vec<usize>> {
    random_population(size)
        .into_par_iter()
        .map(|p| greedy(p).1)
        .collect()
}
/// Genera una población de tamaño `size` en la que cada individuo es el mejor de una ejecución
/// de la variante lamarckaiana del algoritmo genético.
pub fn optimized_population(size: usize) -> Vec<Vec<usize>> {
    let genetic_algorithm = GeneticAlgorithmBuilder::default()
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
        .build()
        .unwrap();

    (0..size).map(|_| genetic_algorithm.execute().1).collect()
}
/// Algoritmo greedy de optimización local basado en 2-opt, recibe una permutación y devuelve una
/// solución mejorada junto a su coste. No siempre encuentra una solucion mejor, en esos casos
/// devolverá la permutación original.
fn greedy(mut permutation: Vec<usize>) -> (u64, Vec<usize>) {
    let mut permutation_cost = unsafe { TAI256C.cost(&permutation) } / 2;
    while {
        let last_cost = permutation_cost;
        for i in 0..permutation.len() {
            for j in i + 1..permutation.len() {
                let new_cost =
                    unsafe { TAI256C.optimized_cost(&permutation, permutation_cost, i, j) };
                if permutation_cost > new_cost {
                    permutation.swap(i, j);
                    permutation_cost = new_cost;
                }
            }
        }
        last_cost != permutation_cost
    } {}
    (permutation_cost * 2, permutation)
}

/// Función de evaluación del algoritmo genético estándar. Utiliza un iterador paralelo para calcular
/// el coste de cada permutación.
pub fn naive(population: Vec<Vec<usize>>) -> Vec<(u64, Vec<usize>)> {
    population
        .into_par_iter()
        .map(|v| unsafe { (TAI256C.cost(&v), v) })
        .collect()
}

/// Función de evaluación de la variante baldwiniana del algoritmo genético. Utiliza un iterador paralelo para
/// crear una nueva población en la que el coste de cada individuo se obtiene llamando a la función [`greedy()`]
/// pero la permutación se mantiene intacta.
pub fn baldwinian(population: Vec<Vec<usize>>) -> Vec<(u64, Vec<usize>)> {
    population
        .into_par_iter()
        .map(|v| (greedy(v.clone()).0, v))
        .collect()
}

/// Función de evaluación de la variante baldwiniana del algoritmo genético. Utiliza un iterador paralelo para
/// crear una nueva población en la que cada individuo se obtiene llamando a la función [`greedy()`] sobre un
/// individuo de la población recibida.
pub fn lamarckian(population: Vec<Vec<usize>>) -> Vec<(u64, Vec<usize>)> {
    population.into_par_iter().map(greedy).collect()
}

/// Selecciona que individuos van a sobrevivir para reproducirse. Recibe un vector ordenado de parejas
/// (coste, permutación) así como el número medio de individuos que van a sobrevivir. Devuelve un vector
/// con las permutaciones supervivientes.
fn select_population(population_rank: Vec<(u64, Vec<usize>)>, survivors: usize) -> Vec<Vec<usize>> {
    let probability = |x, s| 1.0 / (1.0 + (x as f64 - s as f64).exp());
    let rng = &mut thread_rng();
    population_rank
        .into_iter()
        .enumerate()
        .filter(|&(i, _)| rng.gen_bool(probability(i, survivors)))
        .map(|(_, (_, permutation))| permutation)
        .collect()
}

/// Genera una permutación hija a partir de un array de permutaciones padre. Recibe el array de permutaciones
///  padre el parámetro `genetic_mix` cuya funcionalidad ha sido descrita en el struct [`GeneticAlgorithm`].
fn cross(parents: &[Vec<usize>], genetic_mix: usize) -> Vec<usize> {
    let permutation_size = parents[0].len();
    let mut son = Vec::with_capacity(permutation_size);
    let mut elements_in_son = FxHashSet::default();
    elements_in_son.reserve(permutation_size);
    let mut order_of_parents: Vec<_> = (0..parents.len())
        .cycle()
        .take(parents.len() * genetic_mix)
        .collect();
    order_of_parents.shuffle(&mut thread_rng());
    let partition_size = permutation_size / order_of_parents.len();
    for (n, i) in order_of_parents.into_iter().enumerate() {
        let a = n * partition_size;
        let b = if n == parents.len() * genetic_mix - 1 {
            permutation_size
        } else {
            (n + 1) * partition_size
        };
        for &j in parents[i][a..b].iter() {
            if elements_in_son.insert(j) {
                son.push(Some(j));
            } else {
                son.push(None);
            }
        }
    }

    fix_permutation(son, &elements_in_son)
}

/// Rellena los valores `None` que se generan en una permutación cuando hay elementos repetidos. Recibe una
/// permutación de Option<usize> y un `FxHashSet` con todos los elementos usize que contiene la permutación.
/// Utiliza el FxHashSet para ver que números faltan en la permutación y colocarlos aleatoriamente en las
/// posiciones de cada valor `None`.
fn fix_permutation(
    permutation: Vec<Option<usize>>,
    elements_in_son: &FxHashSet<usize>,
) -> Vec<usize> {
    let mut missing: Vec<_> = (0..permutation.len())
        .filter(|i| !elements_in_son.contains(i))
        .collect();
    missing.shuffle(&mut thread_rng());
    let mut missing = missing.into_iter();
    permutation
        .into_iter()
        .map(|i| match i {
            Some(a) => a,
            None => missing.next().unwrap(),
        })
        .collect()
}

/// Muta una permutación. Recibe la probabilidad de mutación así como el número maximo de mutaciones que
/// pueden ocurrir. Las mutaciones se realizan con el método
/// [partial_shuffle()](https://docs.rs/rand/0.8.3/rand/seq/trait.SliceRandom.html#tymethod.partial_shuffle).
fn mutate(mut permutation: Vec<usize>, probability: f64, max_mutations: usize) -> Vec<usize> {
    let rng = &mut thread_rng();
    if rng.gen_bool(probability) {
        let number_of_mutations = rng.gen_range(1..max_mutations);
        permutation.partial_shuffle(rng, number_of_mutations);
    }
    permutation
}

/// Dado un conjunto de permutaciones, las cruza y genera una nueva población. Recibe un array de permutaciones,
/// el numero de padres que se tiene en cuenta para crear un hijo, la probabilidad de mutación, el número máximo
/// de mutaciones posibles, el número de hijos que va a tener cada padre y el parámetro `genetic_mix`.
/// La selección de padres se realiza mediante una versión paralela del método
/// [windows()](https://doc.rust-lang.org/std/primitive.slice.html#method.windows).
fn new_generation(
    selected_permutations: &[Vec<usize>],
    n_parents: usize,
    mutation_probability: f64,
    max_mutations: usize,
    n_sons: usize,
    genetic_mix: usize,
) -> Vec<Vec<usize>> {
    selected_permutations
        .par_windows(n_parents)
        .flat_map(|parents| {
            (0..n_sons).into_par_iter().map(move |_| {
                let son = cross(parents, genetic_mix);
                mutate(son, mutation_probability, max_mutations)
            })
        })
        .collect()
}
