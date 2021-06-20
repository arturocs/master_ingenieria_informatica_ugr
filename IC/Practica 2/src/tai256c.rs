//! # Tai256c
//! Este módulo contiene la funcion de parseo del archivo tai256c.dat, así como las funciones de cálculo de coste.

/// Struct que contiene la segunda matriz del archivo `tai256c.dat`. Solo contiene la segunda ya que la
/// primera no es necesaria para calcular el coste de una solución. Sus métodos permiten calcular el
/// coste de una permutación
pub struct Tai256c(Vec<Vec<i32>>);
impl Tai256c {
    /// Parsea el archivo `tai256.dat` y devuelve un struct `Tai256c`.
    pub fn new() -> Self {
        let lines_until_second_matrix = 257;
        let file_contents = include_str!("../tai256c.dat");
        let parse_line = |s: &str| s.split_whitespace().map(|n| n.parse().unwrap()).collect();
        let matrix = file_contents
            .split('\n')
            .filter(|&s| !s.is_empty())
            .skip(lines_until_second_matrix)
            .map(parse_line)
            .collect();
        Self(matrix)
    }

    /// Dimensiones de la submatriz de unos de la primera matriz.
    const ONES: usize = 92;

    /// Calcula el coste de una solución.
    /// # Safety
    /// `permutation` debe una permutación de los números del 0 al 255.
    pub unsafe fn cost(&self, permutation: &[usize]) -> u64 {
        let get_element = |i: usize, j: usize| {
            *(self.0)
                .get_unchecked(*permutation.get_unchecked(i))
                .get_unchecked(*permutation.get_unchecked(j)) as u64
        };
        2 * (0..Self::ONES)
            .flat_map(|i| (i + 1..Self::ONES).map(move |j| get_element(i, j)))
            .sum::<u64>()
    }

    /// Calcula el coste de una solución a partir de la solución anterior y de los elementos que
    /// se han intercambiado en iteración del algoritmo greedy.
    /// # Safety
    /// `sol` debe una permutación de los números del 0 al 255. `pos_a` y `pos_b` deben ser inferiores a 256.
    pub unsafe fn optimized_cost(
        &self,
        sol: &[usize],
        cost: u64,
        pos_a: usize,
        pos_b: usize,
    ) -> u64 {
        let get_sol = |i| *sol.get_unchecked(i);
        let get_tai = |i: usize, j: usize| *self.0.get_unchecked(i).get_unchecked(j);
        let k = (pos_b < Self::ONES && pos_a >= Self::ONES)
            || (pos_b >= Self::ONES && pos_a < Self::ONES);

        let cost_difference: i64 = (0..sol.len())
            .filter(|&i| i != pos_a && i != pos_b)
            .map(|i| {
                let change_flow = -((i < Self::ONES && k) as i32);
                let difference =
                    get_tai(get_sol(i), get_sol(pos_a)) - get_tai(get_sol(i), get_sol(pos_b));
                (difference * change_flow) as i64
            })
            .sum();
        (cost as i64 + cost_difference) as u64
    }
}
